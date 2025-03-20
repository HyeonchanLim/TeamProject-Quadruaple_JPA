package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.common.model.SizeConstants;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.review.model.*;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final MyFileUtils myFileUtils;
    private final AuthenticationFacade authenticationFacade;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StrfRepository strfRepository;
    private final ReviewPicRepository reviewPicRepository;

    @Value("${const.default-review-size}")
    private int size;

    public ReviewSelRes getReviewWithPics(Long strfId, int startIdx) {
        List<ReviewSelResDto> dtoList = reviewMapper.getReviewWithPics(strfId, startIdx, SizeConstants.getDefault_page_size()+1);
        if (dtoList == null || dtoList.isEmpty()) {
            return null;
        }
        boolean isMore = dtoList.size() > SizeConstants.getDefault_page_size();
        if(isMore){
            dtoList.remove(dtoList.size() - 1);
        }
        ReviewSelRes res = new ReviewSelRes(dtoList,isMore);

        return res;
    }

    public MyReviewSelRes getMyReviews(int startIdx) {
        Long userId = authenticationFacade.getSignedUserId();
        long startTime = System.currentTimeMillis(); // 요청 시작

        int pageSize = SizeConstants.getDefault_page_size();
        List<MyReviewSelResDto> dtoList = reviewMapper.getMyReviews(userId, startIdx, pageSize + 1); // +1개 더 조회

        if (dtoList == null || dtoList.isEmpty()) {
            return null;
        }

        boolean isMore = dtoList.size() > pageSize;
        if (isMore) {
            dtoList.remove(dtoList.size() - 1); // 마지막 요소 삭제하여 정확한 페이징 처리
        }

        MyReviewSelRes res = new MyReviewSelRes(dtoList, isMore);
        long endTime = System.currentTimeMillis(); // 요청값 전달 종료 시간
        long executionTime = endTime - startTime; // 종료시간 - 시작 시간 = 최종 시간

        return res;
    }

    public int myReviewCount (){
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));

        return reviewMapper.myReviewCount(userId);
    }


//    @Transactional
//    public int postRating(List<MultipartFile> pics, ReviewPostReq p) {
//        Long userId = authenticationFacade.getSignedUserId();
//
//        int result = reviewMapper.postRating(p,userId);
//        if (result == 0) {
//            return 0;
//        }
//
//        long reviewId = p.getReviewId();
//        String middlePath = String.format("reviewId/%d", reviewId);
//        myFileUtils.makeFolders(middlePath);
//
//        List<String> picNameList = new ArrayList<>(pics.size());
//        for (MultipartFile pic : pics) {
//            String savedPicName = myFileUtils.makeRandomFileName(pic);
//            picNameList.add(savedPicName);
//            String filePath = String.format("%s/%s", middlePath, savedPicName);
//            try {
//                myFileUtils.transferTo(pic, filePath);
//            } catch (IOException e) {
//                // 폴더 삭제 처리
//                String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
//                myFileUtils.deleteFolder(delFolderPath, true);
//                return 0;
//            }
//        }
//        ReviewPicDto reviewPicDto = new ReviewPicDto();
//        reviewPicDto.setReviewId(reviewId);
//        reviewPicDto.setPics(picNameList);
//
//        // DB에 사진 저장
//        int resultPics = reviewMapper.postReviewPic(reviewPicDto);
//        if (resultPics == 0) {
//            throw new RuntimeException("리뷰 사진 저장 실패");
//        }
//
//        return 1;
//    }
    @Transactional
    public ReviewPostRes postRating(List<MultipartFile> pics, ReviewPostJpaReq p) {
        Long userId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(userId).orElseThrow( () -> new RuntimeException("user id not found"));

        StayTourRestaurFest strf = strfRepository.findById(p.getStrfId()).orElseThrow( () -> new RuntimeException("strf id not found"));

        Review review = new Review();
        review.setRating(p.getRating());
        review.setContent(p.getContent());
        review.setStayTourRestaurFest(strf);
        review.setUser(user);
        review.setReviewId(p.getReviewId());
        reviewRepository.save(review);


        long reviewId = review.getReviewId();
        if (pics != null && !pics.isEmpty()){
            List<String> picNames = new ArrayList<>();

            String middlePath = String.format("reviewId/%d", reviewId);
            myFileUtils.makeFolders(middlePath);

            List<ReviewPic> picNameList = new ArrayList<>();
            for (MultipartFile pic : pics) {
                String savedPicName = myFileUtils.makeRandomFileName(pic);
                String filePath = String.format("%s/%s", middlePath, savedPicName);
                try {
                    String webpFileName = savedPicName.replaceAll("\\.[^.]+$", ".webp");
                    ReviewPicId id = new ReviewPicId();
                    id.setReviewId(reviewId);
                    id.setTitle(webpFileName);

                    ReviewPic reviewPic = ReviewPic.builder()
                            .id(id)
                            .review(review)
                            .build();

                    picNameList.add(reviewPic);
                    picNames.add(webpFileName);

                    myFileUtils.convertAndSaveToWebp(pic, filePath.replaceAll("\\.[^.]+$", ".webp"));
                } catch (IOException e) {
                    e.printStackTrace();
                    String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
                    myFileUtils.deleteFolder(delFolderPath, true);
                    throw new RuntimeException(e);
                }
            }
            reviewPicRepository.saveAll(picNameList);
            ReviewPostRes.builder()
                    .pics(picNames)
                    .build();
        }

        return ReviewPostRes.builder()
                .reviewId(reviewId)
                .build();
    }

//    @Transactional
//    public ResponseEntity<ResponseWrapper<Integer>> updateReview(List<MultipartFile> pics, ReviewUpdReq p) {
//        int updatedRows = reviewMapper.patchReview(p);
//        if (updatedRows == 0) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
//        }
//
//        long reviewId = p.getReviewId();
//        String middlePath = String.format("reviewId/%d", reviewId);
//
//        // 기존 사진 가져오기
//        List<ReviewPicSel> existingPics = reviewMapper.getReviewPics(Collections.singletonList(reviewId));
//
//        for (ReviewPicSel pic : existingPics) {
//            myFileUtils.deleteFolder(String.format("%s/%s", myFileUtils.getUploadPath(), middlePath, pic.getPic()), true);
//        }
//
//        List<ReviewPicDto> picNameList = new ArrayList<>();
//        myFileUtils.makeFolders(middlePath);
//        for (MultipartFile pic : pics) {
//            String savedPicName = myFileUtils.makeRandomFileName(pic);
//            String filePath = String.format("%s/%s", middlePath, savedPicName);
//            try {
//                myFileUtils.transferTo(pic, filePath);
//            } catch (IOException e) {
//                myFileUtils.deleteFolder(String.format("%s/%s", myFileUtils.getUploadPath(), middlePath), true);
//                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                        .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
//            }
//            ReviewPicDto reviewPicDto = new ReviewPicDto();
//            reviewPicDto.setReviewId(reviewId);
//            reviewPicDto.setPic(savedPicName);
//            picNameList.add(reviewPicDto);
//        }
//        int resultPics = reviewMapper.patchReviewPic(picNameList);
//        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), resultPics));
//    }

    @Transactional
    public ResponseWrapper<Integer> deleteReview(Long reviewId) {
        Long userId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("Review ID not found"));

        if (!review.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own reviews.");
        }

//        if (picName != null && !picName.isEmpty()) {
//            reviewPicRepository.deleteById(new ReviewPicId(picName, reviewId));
//        }

        reviewPicRepository.deleteByReviewId(reviewId);
        reviewPicRepository.flush();

        reviewRepository.delete(review);
        reviewRepository.flush();

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }
}
