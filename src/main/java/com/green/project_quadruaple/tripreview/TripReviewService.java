package com.green.project_quadruaple.tripreview;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.entity.repository.*;
import com.green.project_quadruaple.trip.TripMapper;
import com.green.project_quadruaple.tripreview.model.*;
import com.green.project_quadruaple.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripReviewService {
    private final TripReviewMapper tripReviewMapper;
    private final TripMapper tripMapper;
    private final MyFileUtils myFileUtils;
    private final AuthenticationFacade authenticationFacade;
    private final TripReviewRepository tripReviewRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TripLikeRepository likeRepository;
    private final RecentTrRepository recentTrRepository;
    private final ScrapRepository scrapRepository;
    private final TripReviewPicRepository tripReviewPicRepository;
    private final TripLikeRepository tripLikeRepository;
    private final ScheMemoRepository scheMemoRepository;

    @Value("${const.default-review-size}")
    private int size;

    // 여행기 등록
    @Transactional
    public TripReviewPostRes postTripReview(List<MultipartFile> tripReviewPic, TripReviewPostReq req) {
        req.setUserId(authenticationFacade.getSignedUserId());

        TripReview tripReview = new TripReview();

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Trip trip = tripRepository.findById(req.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (!scheMemoRepository.existsByTrip_TripId(trip.getTripId())) {
            throw new RuntimeException("ScheMemo not fount");
        }

        // 여행 시작일 이후인지 검증
        LocalDate today = LocalDate.now();
        if (today.isBefore(trip.getPeriod().getStartAt())) {
            throw new RuntimeException("여행이 시작되기 전에 여행기를 등록할 수 없습니다.");
        }

        tripReview.setUser(user);
        tripReview.setTrip(trip);
        tripReview.setTitle(req.getTitle());
        tripReview.setContent(req.getContent());

        tripReviewRepository.save(tripReview);

        // 파일 등록
        long tripReviewId = tripReview.getTripReviewId();
        String middlePath = String.format("tripReview/%d", tripReviewId);
        myFileUtils.makeFolders(middlePath);

        List<String> picNameList = new ArrayList<>();
        // 사진 파일이 있을 경우만 처리
        if (tripReviewPic != null && !tripReviewPic.isEmpty()) {
            myFileUtils.makeFolders(middlePath);

            for (MultipartFile pic : tripReviewPic) {
                String savedPicName = myFileUtils.makeRandomFileName(pic);
                String getExt = myFileUtils.getExt(savedPicName);
                picNameList.add(savedPicName.replace(getExt, ".webp"));
                String filePath = String.format("%s/%s", middlePath, savedPicName);
                try {
                    myFileUtils.convertAndSaveToWebp(pic, filePath.replaceAll("\\.[^.]+$", ".webp"));
                } catch (IOException e) {
                    // 업로드된 폴더 삭제
                    String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
                    myFileUtils.deleteFolder(delFolderPath, true);
                    throw new RuntimeException(e);
                }
            }

            // 사진 파일이 있는 경우에만 DB 저장
            if (!picNameList.isEmpty()) {
                tripReviewMapper.insTripReviewPic(tripReviewId, picNameList);
            }
        }


        TripReviewPostRes tripReviewPostRes = new TripReviewPostRes();
        tripReviewPostRes.setTripReviewId(tripReviewId);
        tripReviewPostRes.setTripReviewPic(picNameList);


        return tripReviewPostRes;
    }

    //여행기 조회
    // 로그인한 사용자의 여행기만 조회
    public List<TripReviewGetDto> getMyTripReviews(String orderType) {
        long userId = authenticationFacade.getSignedUserId(); // 현재 로그인한 유저 ID 가져오기
        return tripReviewMapper.getMyTripReviews(userId, orderType);
    }
    public int getMyTripReviewsCount() {
        long userId = authenticationFacade.getSignedUserId();
        return tripReviewRepository.countByUser_UserId(userId);
    }

    // 모든 사용자의 여행기 조회
    public TripReviewGetResponse getAllTripReviews(String orderType, int pageNumber) {
        // 로그인한 유저의 ID 가져오기 (로그인 안 했으면 null 처리)
        Long signedUserId = null;
        try {
            signedUserId = authenticationFacade.getSignedUserId();
        } catch (Exception e) {
            // 인증되지 않은 사용자일 경우 signedUserId는 null 유지
        }

        // OFFSET 계산
        int startIdx = (pageNumber - 1) * size;

        // 현재 저장된 전체 개수를 조회
        int totalCount = tripReviewMapper.getTotalTripReviewsCount(); // 여행기 총 개수

        // startIdx가 totalCount보다 크면 빈 리스트 반환
        if (startIdx >= totalCount) {
            return new TripReviewGetResponse(Collections.emptyList(), false);
        }

        // 여행기 목록 조회 (size + 1로 한 개 더 가져오기)
        List<TripReviewGetDto> result = tripReviewMapper.getAllTripReviews(orderType, startIdx, size + 1, signedUserId);

        // 다음 페이지가 있는지 확인
        boolean hasMore = result.size() > size;

        if (hasMore) {
            // 초과된 1개 데이터 삭제
            result.remove(result.size() - 1);
        }

        return new TripReviewGetResponse(result, hasMore);
    }
    public int getTripReviewCount() {
        return tripReviewRepository.countAllBy();
    }

    // 다른 사용자의 여행기 조회
    public List<TripReviewGetDto> getOtherTripReviews(long tripReviewId) {
        Long userId = 0L; // 로그인 여부 확인 (nullable)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userId = authenticationFacade.getSignedUserId();
        }
        if (userId > 0) {
            tripReviewMapper.insTripReviewRecent(userId, tripReviewId);
        }

        List<TripReviewGetDto> tripReviewGetDto = tripReviewMapper.getOtherTripReviewById(tripReviewId, userId);
        if (tripReviewGetDto == null || tripReviewGetDto.isEmpty()) {
            throw new RuntimeException("해당 여행기를 찾을 수 없습니다.");
        }

        return tripReviewGetDto;
    }

    // 여행기 수정
    @Transactional
    public int patchTripReview(List<MultipartFile> tripPic, TripReviewPatchDto dto) {
        dto.setUserId(authenticationFacade.getSignedUserId());

        TripReview tripReview = tripReviewRepository.findById(dto.getTripReviewId())
                .orElseThrow(() -> new RuntimeException("해당 여행기를 찾을 수 없습니다."));

        // 현재 로그인한 유저와 여행기 작성자가 같은지 확인
        if (!tripReview.getUser().getUserId().equals(dto.getUserId())) {
            throw new RuntimeException("본인의 여행기만 수정할 수 있습니다.");
        }

        tripReview.setTitle(dto.getTitle());
        tripReview.setContent(dto.getContent());

        tripReviewRepository.save(tripReview);


        if (tripPic != null && !tripPic.isEmpty()) {
            // 기존 DB의 여행기 사진 삭제
            tripReviewMapper.delTripReviewPic(dto.getTripReviewId());

            String basePath = myFileUtils.getUploadPath(); // 기본 업로드 경로
            String middlePath = String.format("tripReview/%d", dto.getTripReviewId());
            String targetPath = String.format("%s/%s", basePath, middlePath); // 중복 경로 방지

            myFileUtils.deleteFolder(targetPath, true);

            File newFolder = new File(targetPath);
            if (!newFolder.exists()) {
                boolean created = newFolder.mkdirs();
                if (!created) {
                    throw new RuntimeException("여행기 사진 폴더 생성에 실패했습니다: " + targetPath);
                }
            }

            List<String> picNameList = new ArrayList<>();


            for (MultipartFile pic : tripPic) {
                if (pic != null && !pic.isEmpty()) {
                    String savedPicName = myFileUtils.makeRandomFileName(pic);
                    String getExt = myFileUtils.getExt(savedPicName);
                    picNameList.add(savedPicName.replace(getExt, ".webp"));
                    String filePath = String.format("%s/%s", middlePath, savedPicName); // 중복 경로 수정

                    try {
                        myFileUtils.convertAndSaveToWebp(pic, filePath.replaceAll("\\.[^.]+$", ".webp"));
                    } catch (IOException e) {
                        throw new RuntimeException("여행기 사진 저장에 실패했습니다.", e);
                    }
                }
            }

            if (!picNameList.isEmpty()) {
                tripReviewMapper.insTripReviewPic(dto.getTripReviewId(), picNameList);
            } else {
                System.out.println("저장할 사진이 없음!");
            }
        }

        return 1;
    }

    //여행기 삭제
    @Transactional
    public int deleteTripReview(long tripReviewId) {
        long signedUserId = authenticationFacade.getSignedUserId();

        // 여행기 조회
        TripReview tripReview = tripReviewRepository.findById(tripReviewId)
                .orElseThrow(() -> new RuntimeException("해당 여행기를 찾을 수 없습니다."));

        // 본인 작성 여행기인지 확인
        if (!tripReview.getUser().getUserId().equals(signedUserId)) {
            return 0;
        }

        recentTrRepository.deleteByTripReviewId_TripReviewId(tripReviewId);
        scrapRepository.deleteByTripReviewId_TripReviewId(tripReviewId);
        likeRepository.deleteByTripReviewId_TripReviewId(tripReviewId);
        tripReviewPicRepository.deleteByTripReview_TripReviewId(tripReviewId);

        String basePath = myFileUtils.getUploadPath(); // 기본 업로드 경로
        String middlePath = String.format("tripReview/%d", tripReviewId);
        String targetPath = String.format("%s/%s", basePath, middlePath); // 중복 경로 방지
        myFileUtils.deleteFolder(targetPath, true);

        tripReviewRepository.delete(tripReview);

        return 1;
    }


    // 여행기 추천
    @Transactional
    public int insTripLike(TripLikeDto like) {
        Long userId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TripReview tripReview = tripReviewRepository.findById(like.getTripReviewId())
                .orElseThrow(() -> new RuntimeException("TripReview not found"));

        TripLikeId tripLikeId = new TripLikeId(like.getTripReviewId(), userId);

        // 이미 좋아요를 눌렀는지 확인
        if (tripLikeRepository.existsById(tripLikeId)) {
            throw new RuntimeException("이미 좋아요를 눌렀습니다.");
        }

        TripLike tripLike = new TripLike();
        tripLike.setId(tripLikeId);
        tripLike.setUserId(user);
        tripLike.setTripReviewId(tripReview);

        tripLikeRepository.save(tripLike);

        return 1;
    }


    @Transactional
    public int delTripLike(TripLikeDto like) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // TripLikeId 객체를 생성
        TripLikeId tripLikeId = new TripLikeId(like.getTripReviewId(), userId);

        // TripLikeId를 사용하여 TripLike 엔티티를 조회
        TripLike tripLike = tripLikeRepository.findById(tripLikeId)
                .orElseThrow(() -> new RuntimeException("TripReviewLike not found"));

        // 삭제
        tripLikeRepository.delete(tripLike);

        return 1;
    }

    public int getTripLikeCount(Long tripReviewId) {
        return tripLikeRepository.countByTripReviewId_TripReviewId(tripReviewId);
    }

    // 여행기 스크랩
    public int copyTripReview(CopyInsertTripDto trip) {
        long userId = authenticationFacade.getSignedUserId();
        trip.setUserId(userId);

        // 1. tripReviewId와 tripId 유효성 검증
        validateTripReview(trip.getTripReviewId(), trip.getCopyTripId());

        // 2. 여행 복사 실행 (기존 여행 정보 그대로 가져와 삽입)
        tripReviewMapper.copyInsTrip(trip);

        // 새롭게 생성된 tripId 가져오기
        Long newTripId = trip.getTripId();
        if (newTripId == null || newTripId <= 0) {
            throw new RuntimeException("Failed to copy trip. No new tripId generated.");
        }

        tripReviewMapper.insTripUser(newTripId, userId);

        // 3. 일정/메모 복사
        CopyInsertScheMemoDto copyInsertScheMemoDto = new CopyInsertScheMemoDto();
        copyInsertScheMemoDto.setTripId(newTripId);
        copyInsertScheMemoDto.setCopyTripId(trip.getCopyTripId());
        int copyScheMemo = tripReviewMapper.copyInsScheMemo(copyInsertScheMemoDto);

        // 4. 기존 sche_memo_id 목록 조회
        List<Long> originalScheMemoIds = tripReviewMapper.getOriginalScheMemoIds(trip.getCopyTripId());

        // 5. 새롭게 생성된 scheduleMemoId 목록 조회
        List<Long> newScheMemoIds = tripReviewMapper.getNewScheMemoIds(newTripId);

        // 6. 기존 일정의 schedule_id 목록 조회
        List<Long> originalScheduleIds = tripReviewMapper.getOriginalScheduleIds(originalScheMemoIds);

        // 7. 일정 개수 확인
        if (originalScheduleIds.size() != newScheMemoIds.size()) {
            throw new RuntimeException("Mismatch in schedule_memo mapping. Expected: "
                    + originalScheduleIds.size() + ", but got: " + newScheMemoIds.size());
        }

        // 8. 일정 복사
        for (int i = 0; i < originalScheduleIds.size(); i++) {
            CopyScheduleDto copyScheduleDto = new CopyScheduleDto();
            copyScheduleDto.setScheduleMemoId(newScheMemoIds.get(i)); // 새로 생성된 scheduleMemoId 사용
            copyScheduleDto.setOriginalScheduleId(originalScheduleIds.get(i)); // 기존 schedule_id 사용
            int copySchedule = tripReviewMapper.copyInsSchedule(copyScheduleDto);
        }

        // 9. 여행 위치 복사
        List<Long> originalLocationIds = tripReviewMapper.getOriginalLocationIds(trip.getCopyTripId());
        if (!originalLocationIds.isEmpty()) {
            tripReviewMapper.copyInsTripLocation(trip.getCopyTripId(), newTripId);
        }

        // 10. 스크랩 테이블에 담기
        TripReviewScrapDto tripReviewScrapDto = new TripReviewScrapDto();
        tripReviewScrapDto.setTripReviewId(trip.getTripReviewId());
        tripReviewScrapDto.setTripId(newTripId);
        int insScrap = tripReviewMapper.insTripReviewScrap(tripReviewScrapDto);

        return 1;
    }



    // tripReviewId와 tripId의 유효성 검증
    private void validateTripReview(long tripReviewId, long tripId) {
        int count = tripReviewMapper.countTripReview(tripReviewId, tripId);
        if (count == 0) {
            throw new RuntimeException("tripReviewId 또는 tripId가 잘못되었습니다. 제공된 ID가 일치하지 않습니다.");
        }
    }

}
