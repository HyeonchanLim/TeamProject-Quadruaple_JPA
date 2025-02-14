package com.green.project_quadruaple.search;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.search.model.*;

import com.green.project_quadruaple.search.model.SearchCategoryRes;
import com.green.project_quadruaple.search.model.strf_list.GetSearchStrfListBasicRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/search") // 겹치지 않도록 설정
@Tag(name = "검색")
public class SearchController {

    private final SearchService searchService;

    // 중복 생성자를 제거하고 아래처럼 정의
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/location")
    @Operation(summary = "일정 추가 지역 검색")
    public ResponseEntity<?> getTripLocation(@RequestParam String search_word) {
        System.out.println("Received search_word: " + search_word); // 디버깅용 로그 추가
        List<LocationResponse> locations = searchService.getTripLocation(search_word);

        if (locations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("검색 결과가 없습니다.");
        }
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/strf-list-basic")
    @Operation(summary = "일정 추가 검색 기본", description = "일정 추가 검색 화면 처음 접근시 불러올 해당지역 추천 장소들 요청 API")
    public ResponseWrapper<GetSearchStrfListBasicRes> getStrfListBasic(@RequestParam("trip_id") Long tripId,
                                                                       @RequestParam("last_index") int lastIdx)
    {
        return searchService.getStrfListBasic(tripId, lastIdx);
    }

    @GetMapping("/strf-list-word")
    @Operation(summary = "일정 추가 검색", description = "일정 추가 검색, 검색어 입력 시 요청 API, 현재 필터는 카테고리와 검색어 뿐")
    public ResponseWrapper<GetSearchStrfListBasicRes> getStrfListWithSearchWord(@RequestParam("trip_id") Long tripId,
                                                                       @RequestParam("last_index") int lastIdx,
                                                                       @RequestParam(required = false) String category,
                                                                       @RequestParam(value = "search_word", required = false) String searchWord)
    {
        return searchService.getStrfListWithSearchWord(tripId, lastIdx, category, searchWord);
    }



    // 상품 검색어 최근 리스트

    @GetMapping("/list")
    @Operation(summary = "검색창 최근 검색어 리스트", description = "user_id 가 없으면 반환하는 데이터 없음")
    public ResponseWrapper<?> searchGetList (){
        return searchService.searchGetList();
    }


    // 밑으로 상품 검색

    @GetMapping("/basic")
    @Operation(summary = "상품 검색 최근 본 목록 리스트", description = "user_id 가 없으면 반환하는 데이터 없음")
    public ResponseWrapper<?> searchBasicList() {
        return searchService.searchBasicRecent();
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 상품", description = "현재 날짜 기준 3개월 동안 검색이 많은 상품")
    public ResponseWrapper<?> searchBasicPopular(){
        return searchService.searchBasicPopular();
    }

    @PostMapping("/all")
    @Operation(summary = "검색 - 전체 페이지 리스트", description = "최대 3개 출력 - 나머지는 더 보기 누르면 category 로 이전")
    public ResponseWrapper<?> searchAll(@RequestParam("search_word") String searchWord) {
        return searchService.searchAll(searchWord);
    }

    @GetMapping("/category")
    public ResponseEntity<?> searchCategory(@RequestParam("last_index") int lastIdx,
                                            @RequestParam String category,
                                            @RequestParam(value = "search_word", required = false) String searchWord,
                                            @RequestParam(value = "order_type",required = false) String orderType) {

        ResponseWrapper<List<SearchCategoryRes>> list = searchService.searchCategory(lastIdx,category,searchWord, orderType);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/filter")
    public ResponseWrapper<?> searchStayFilter(@RequestParam("last_index") int lastIdx,
                                              @RequestParam String category,
                                              @RequestParam(value = "search_word") String searchWord,
                                              @RequestParam(value = "amenity_id") List<Long> amenityId){

        return searchService.searchStayFilter(lastIdx,category,searchWord,amenityId);
    }

}