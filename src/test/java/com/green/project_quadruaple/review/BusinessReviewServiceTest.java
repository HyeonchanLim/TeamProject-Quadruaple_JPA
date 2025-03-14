package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.Review;
import com.green.project_quadruaple.entity.model.ReviewReply;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.review.repository.ReviewReplyRepository;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyPostDto;
import com.green.project_quadruaple.strf.BusinessNumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessReviewServiceTest {

    @InjectMocks
    private BusinessReviewService businessReviewService;

    @Mock
    private ReviewReplyRepository reviewReplyRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BusinessNumRepository businessNumRepository;

    @Mock
    private AuthenticationFacade authenticationFacade;

    private Long userId;
    private Long reviewId;
    private Long strfId;
    private String businessNum;
    private Review review;

    @BeforeEach
    void setUp() {
        userId = 1L;
        reviewId = 100L;
        strfId = 200L;
        businessNum = "BUSI123";


        review = mock(Review.class);
        when(review.getStayTourRestaurFest()).thenReturn(mock(StayTourRestaurFest.class));
        when(review.getStayTourRestaurFest().getStrfId()).thenReturn(strfId);
    }

    @BeforeEach
    void setup() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);


        when(authenticationFacade.getSignedUserId()).thenReturn(userId);
    }

    @Test
    void createReviewReply_Success() {
        // Given
        ReviewReplyPostDto requestDto = new ReviewReplyPostDto();
        requestDto.setReviewId(reviewId);
        requestDto.setContent("This is a test reply");

        when(authenticationFacade.getSignedUserId()).thenReturn(userId);
        when(businessNumRepository.findBusiNumByUserId(userId)).thenReturn(Collections.singletonList(businessNum));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(businessNumRepository.findBusiNumByStrfId(strfId)).thenReturn(Optional.of(businessNum));

        ReviewReply savedReviewReply = mock(ReviewReply.class);
        when(savedReviewReply.getReplyId()).thenReturn(999L);
        when(reviewReplyRepository.save(any(ReviewReply.class))).thenReturn(savedReviewReply);

        // When
        Long replyId = businessReviewService.createReviewReply(requestDto);

        // Then
        assertNotNull(replyId);
        assertEquals(999L, replyId);
        verify(reviewReplyRepository, times(1)).save(any(ReviewReply.class));
    }

    @Test
    void createReviewReply_Fail_NoBusinessInfo() {
        // Given
        ReviewReplyPostDto requestDto = new ReviewReplyPostDto();
        requestDto.setReviewId(reviewId);
        requestDto.setContent("This is a test reply");

        when(authenticationFacade.getSignedUserId()).thenReturn(userId);
        when(businessNumRepository.findBusiNumByUserId(userId)).thenReturn(Collections.emptyList());

        // When & Then
        assertThrows(AccessDeniedException.class, () -> businessReviewService.createReviewReply(requestDto));
        verify(reviewRepository, never()).findById(anyLong());
    }

    @Test
    void createReviewReply_Fail_NoReviewFound() {
        // Given
        ReviewReplyPostDto requestDto = new ReviewReplyPostDto();
        requestDto.setReviewId(reviewId);
        requestDto.setContent("This is a test reply");

        when(authenticationFacade.getSignedUserId()).thenReturn(userId);
        when(businessNumRepository.findBusiNumByUserId(userId)).thenReturn(Collections.singletonList(businessNum));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> businessReviewService.createReviewReply(requestDto));
    }

    @Test
    void createReviewReply_Fail_NoBusinessMatch() {
        // Given
        ReviewReplyPostDto requestDto = new ReviewReplyPostDto();
        requestDto.setReviewId(reviewId);
        requestDto.setContent("This is a test reply");

        when(authenticationFacade.getSignedUserId()).thenReturn(userId);
        when(businessNumRepository.findBusiNumByUserId(userId)).thenReturn(Collections.singletonList("DIFFERENT_BUSI_NUM"));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(businessNumRepository.findBusiNumByStrfId(strfId)).thenReturn(Optional.of(businessNum));

        // When & Then
        assertThrows(AccessDeniedException.class, () -> businessReviewService.createReviewReply(requestDto));
    }
}