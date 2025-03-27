package com.green.project_quadruaple.search;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.common.model.SizeConstants;
import com.green.project_quadruaple.entity.model.SearchWord;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.review.model.SearchCategoryDto;
import com.green.project_quadruaple.search.model.*;
import com.green.project_quadruaple.search.model.filter.*;
import com.green.project_quadruaple.search.model.strf_list.GetSearchStrfListBasicRes;
import com.green.project_quadruaple.search.model.strf_list.LocationIdAndTitleDto;
import com.green.project_quadruaple.search.model.strf_list.StrfShortInfoDto;
import com.green.project_quadruaple.entity.repository.SearchWordRepository;
import com.green.project_quadruaple.trip.model.Category;
import com.green.project_quadruaple.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final AuthenticationFacade authenticationFacade;
    private final SearchMapper searchMapper;
    private final SearchWordRepository searchWordRepository;
    private final UserRepository userRepository;

    @Value("${const.default-search-size}")
    private int size;

    // 기존
    /*public List<LocationResponse> getTripLocation(String searchWord, int offset, int pageSize) {
        // SQL 매퍼 호출
        return searchMapper.getTripLocation(searchWord, offset, pageSize);
    }*/


    /* public List<LocationResponse> getTripLocation(String searchWord) {
         return searchMapper.getTripLocation(searchWord);
         // asdasd
     }*/
    public List<LocationResponse> getTripLocation(String searchWord) {
        List<LocationResponse> locations = searchMapper.getTripLocation(searchWord);
        System.out.println("Fetched locations: " + locations); // 디버깅용 로그 추가
        return locations;
    }

    public ResponseWrapper<GetSearchStrfListBasicRes> getStrfListBasic(long tripId, int startIdx) {
        if (tripId == 0) return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), null);
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        int more = 1;
        try {
            List<LocationIdAndTitleDto> locationIdList = searchMapper.selLocationIdByTripId(tripId);
            List<StrfShortInfoDto> dto = searchMapper.selStrfShortInfoBasic(signedUserId, locationIdList, startIdx, size + more, null, null);
            GetSearchStrfListBasicRes res = new GetSearchStrfListBasicRes();
            if (dto.size() > size) {
                dto.remove(size);
                res.setMore(true);
            }
            res.setList(dto);
            List<String> titleList = new ArrayList<>();
            for (LocationIdAndTitleDto list : locationIdList) {
                titleList.add(list.getLocationTitle());
            }
            res.setLocationTitleList(titleList);
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public ResponseWrapper<GetSearchStrfListBasicRes> getStrfListWithSearchWord(long tripId,
                                                                                int startIdx,
                                                                                String category,
                                                                                String searchWord) {
        if (tripId == 0) return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), null);
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        int more = 1;
        try {
            List<LocationIdAndTitleDto> locationIdList = searchMapper.selLocationIdByTripId(tripId);
            List<StrfShortInfoDto> dto = searchMapper.selStrfShortInfoBasic(signedUserId, locationIdList, startIdx, size + more, category, searchWord);
            GetSearchStrfListBasicRes res = new GetSearchStrfListBasicRes();
            if (dto.size() >= size) {
                dto.remove(size);
                res.setMore(true);
            }
            res.setList(dto);
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 검색창 - 최근 검색어 출력
    public ResponseWrapper<List<SearchGetRes>> searchGetList() {
        Long userId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        List<SearchWord> search = searchWordRepository.searchByUser(user.getUserId());

        List<SearchGetRes> res = searchMapper.searchGetList(user.getUserId());
        for (SearchGetRes searchGetRes : res) {
            searchGetRes.setUserId(user.getUserId());
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    // 밑으로 상품 검색
    public ResponseWrapper<List<SearchBasicRecentRes>> searchBasicRecent() {
        Long userId = authenticationFacade.getSignedUserId();
        List<SearchBasicRecentRes> res = searchMapper.searchBasicRecent(userId);
        if (res.isEmpty()) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }
        try {
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null);
        }
    }

    public ResponseWrapper<List<SearchBasicPopualarRes>> searchBasicPopular() {
        List<SearchBasicPopualarRes> res = searchMapper.searchBasicPopular();
        if (res.isEmpty()) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }
        try {
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null);
        }
    }

    public ResponseWrapper<List<Stay>> searchAll(String searchWord) {
        long startTime = System.currentTimeMillis(); // 요청 시작
        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof JwtUser) {
            userId = authenticationFacade.getSignedUserId();
        }
        if (userId > 0) {
            searchMapper.searchIns(searchWord, userId);
        }

        try {
            List<Stay> stays = searchMapper.searchAllList(searchWord, userId);
            stays.forEach(stay -> {
                if (stay.getAverageRating() != null) {
                    double roundedRating = Math.round(stay.getAverageRating() * 10) / 10.0;
                    stay.setAverageRating(roundedRating);
                }
            });
            System.out.println("쿼리 실행 시간: " + startTime + "ms");
            long endTime = System.currentTimeMillis(); // 요청값 전달 종료 시간
            long executionTime = endTime - startTime; // 종료시간 - 시작 시간 = 최종 시간

            System.out.println("쿼리 요청 종료 시간: " + endTime + "ms");
            System.out.println("쿼리 최종 시간: " + executionTime + "ms");
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), stays);

        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
    }

    public ResponseWrapper<Integer> categoryCount(String category, String searchWord) {
        String categoryValue = null;
        if (category != null && Category.getKeyByName(category) != null) {
            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
        }
        int count = searchMapper.categoryCount(categoryValue, searchWord);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), count);
    }

    public ResponseWrapper<SearchCategoryRes> searchCategory(int startIdx, String category, String searchWord, String orderType) {
        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof JwtUser) {
            userId = authenticationFacade.getSignedUserId();
        }

        if (userId > 0) {
            searchMapper.searchIns(searchWord, userId);
        }

        String categoryValue = null;
        if (category != null && Category.getKeyByName(category) != null) {
            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
        }

        int pageSize = SizeConstants.getDefault_page_size();
        List<SearchCategoryDto> dtoList = searchMapper.searchCategory(startIdx, pageSize + 1, categoryValue, searchWord, userId, orderType);

        boolean hasMore = dtoList.size() > pageSize;
        if (hasMore) {
            dtoList.remove(dtoList.size() - 1); // 다음 페이지 존재 여부 판별용 데이터 제거
        }

        // 평점 반올림
        dtoList.forEach(dto -> {
            if (dto.getRatingAvg() != null) {
                double roundedRating = Math.round(dto.getRatingAvg() * 10) / 10.0;
                dto.setRatingAvg(roundedRating);
            }
        });

        SearchCategoryRes res = new SearchCategoryRes();
        res.setDtoList(dtoList);
        res.setMore(hasMore);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    public ResponseWrapper<Integer> amenityCnt(List<Long> amenityId) {
        int count = searchMapper.amenityCnt(amenityId);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), count);
    }

    public ResponseWrapper<List<SearchStay>> searchStayFilter(int startIdx, String category, String searchWord, List<Long> amenityId) {
        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof JwtUser) {
            userId = authenticationFacade.getSignedUserId();
        }
        String categoryValue = null;
        if (category != null && Category.getKeyByName(category) != null) {
            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
        }
        List<SearchStay> stays = searchMapper.searchStay(startIdx, SizeConstants.getDefault_page_size(), categoryValue, searchWord, userId, amenityId);

        if (stays.size() == 0) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), stays);
        }

        stays.forEach(stay -> {
            if (stay.getRatingAvg() != null) {
                double roundedRating = Math.round(stay.getRatingAvg() * 10) / 10.0;
                stay.setRatingAvg(roundedRating);
            }
        });

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), stays);
    }
    public ResponseWrapper<List<SearchManyOneDto>> SearchManyOne (long menuId){
        List<SearchManyOneDto> many = searchMapper.SearchManyOne(menuId);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), many);
    }
}