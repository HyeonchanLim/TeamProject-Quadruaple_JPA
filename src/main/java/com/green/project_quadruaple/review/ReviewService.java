package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.Review;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.review.model.*;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


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

    @Value("${const.default-review-size}")
    private int size;

    public List<ReviewSelRes> getReviewWithPics(Long strfId,int startIdx ) {
        int more = 1;
        List<ReviewSelRes> dtoList = reviewMapper.getReviewWithPics(strfId,startIdx,size+more);
        if (dtoList == null || dtoList.isEmpty()) {
            return null;
        }
        boolean hasMore = dtoList.size() > size;
        if (hasMore) {
            dtoList.get(dtoList.size()-1).setMore(true);
            dtoList.remove(dtoList.size()-1);
        }
        Map<Long, ReviewSelRes> reviewMap = new LinkedHashMap<>();
        for (ReviewSelRes item : dtoList) {
            ReviewSelRes review = reviewMap.get(item.getReviewId());
        }
        return dtoList;
    }
    public List<MyReviewSelRes> getMyReviews(int startIdx) {
        Long userId = authenticationFacade.getSignedUserId();
        int more = 1;
        List<MyReviewSelRes> dtoList = reviewMapper.getMyReviews(userId,startIdx,size+more);
        if (dtoList == null || dtoList.isEmpty()) {
            return null;
        }
        boolean hasMore = dtoList.size() > size;
        if (hasMore) {
            dtoList.get(dtoList.size()-1).setMore(true);
            dtoList.remove(dtoList.size()-1);
        }
        Map<Long, MyReviewSelRes> reviewMap = new LinkedHashMap<>();
        for (MyReviewSelRes item : dtoList) {
            // 기존 리뷰 ID로 저장된 객체가 있는지 확인
            MyReviewSelRes review = reviewMap.get(item.getReviewId());
        }
        return dtoList;
    }

    @Transactional
    public int postRating(List<MultipartFile> pics, ReviewPostJpaReq p) {
        Long userId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(userId).orElseThrow( () -> new RuntimeException("user id not found"));

        StayTourRestaurFest strf = strfRepository.findById(p.getStrfId()).orElseThrow( () -> new RuntimeException("strf id not found"));

        Review review = new Review();
        review.setRating(p.getRating());
        review.setContent(p.getContent());
        review.setStayTourRestaurFest(strf);
        review.setUser(user);
        Review savedReview = reviewRepository.save(review);

//        long reviewId = p.getReviewId();
        String middlePath = String.format("reviewId/%d", savedReview.getReviewId());
        myFileUtils.makeFolders(middlePath);

        List<String> picNameList = new ArrayList<>(pics.size());
        for (MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            picNameList.add(savedPicName);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                // 폴더 삭제 처리
                String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
                myFileUtils.deleteFolder(delFolderPath, true);
                return 0;
            }
        }
        ReviewPicDto reviewPicDto = new ReviewPicDto();
        reviewPicDto.setReviewId(savedReview.getReviewId());
        reviewPicDto.setPics(picNameList);

        // DB에 사진 저장
        int resultPics = reviewMapper.postReviewPic(reviewPicDto);
        if (resultPics == 0) {
            throw new RuntimeException("리뷰 사진 저장 실패");
        }
        return 1;
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

    public ResponseEntity<ResponseWrapper<Integer>> deleteReview(Long reviewId) {
        Long userId = authenticationFacade.getSignedUserId();

        ReviewDelPicReq picReq = new ReviewDelPicReq();
        picReq.setReviewId(reviewId);

        int affectedRowsPic = reviewMapper.deleteReviewPic(reviewId);
        int affectedRowsReview = reviewMapper.deleteReview(reviewId,userId);

        String deletePath = String.format("%s/feed/%d", myFileUtils.getUploadPath(), reviewId);

        myFileUtils.deleteFolder(deletePath, true);

        if (affectedRowsReview > 0) {
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), affectedRowsReview));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
    }






}
