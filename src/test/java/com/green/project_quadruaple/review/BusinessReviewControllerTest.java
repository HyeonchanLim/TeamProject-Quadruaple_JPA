package com.green.project_quadruaple.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyPostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BusinessReviewControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BusinessReviewController businessReviewController;

    @Mock
    private BusinessReviewService businessReviewService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("대댓글 생성 API 성공")
    void createReviewReply_Success() throws Exception {
        // Given
        Long reviewId = 10L;
        String replyContent = "대댓글 테스트";
        Long expectedReplyId = 100L;

        ReviewReplyPostDto requestDto = new ReviewReplyPostDto(reviewId, replyContent);

        when(businessReviewService.createReviewReply(requestDto)).thenReturn(expectedReplyId);

        // MockMvc 설정
        mockMvc = MockMvcBuilders.standaloneSetup(businessReviewController).build();

        // When & Then
        mockMvc.perform(post("/api/review/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedReplyId)));
    }
}