package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.review.model.BusinessDto;
import com.green.project_quadruaple.review.model.ReviewPicDto;
import com.green.project_quadruaple.review.repository.ReviewReplyRepository;
import com.green.project_quadruaple.strf.BusinessNumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
class BusinessReviewServiceTest {

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private BusinessReviewService businessReviewService;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private ReviewService reviewService;

    @Mock
    private BusinessNumRepository businessNumRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewReplyRepository reviewReplyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void getBusinessReview_Success() {
        // Given
        Long signedUserId = 100L;
        int page = 1, pageSize = 10;
        int offset = (page - 1) * pageSize;

        when(authenticationFacade.getSignedUserId()).thenReturn(signedUserId);
        when(reviewMapper.findUserRoleByUserId(signedUserId)).thenReturn("BUSI");

        List<BusinessDto> reviews = List.of(
                new BusinessDto("2024-03-11", 1L, 1L, "리뷰 제목", 1, 10, 4.8, "리뷰 내용", 200L,
                        "사용자", "profile.jpg", 1, "대댓글 내용", "2024-03-11", List.of("image1.jpg"), 300L)
        );

        when(reviewMapper.selectBusinessReview(signedUserId, page, pageSize, offset)).thenReturn(reviews);
        when(reviewMapper.selectReviewPics(1L)).thenReturn(List.of("image1.jpg"));

        // When
        List<BusinessDto> result = businessReviewService.getBusinessReview(page, pageSize);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getStrfTitle()).isEqualTo("리뷰 제목");
        assertThat(result.get(0).getReviewPicList().size()).isEqualTo(1);

        verify(reviewMapper, times(1)).selectBusinessReview(signedUserId, page, pageSize, offset);
    }*/


    @Test
    void createReviewReply() {
    }

    @Test
    void updateReviewReply() {
    }

    @Test
    void deleteReviewReply() {
    }
}