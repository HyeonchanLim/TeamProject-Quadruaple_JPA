package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
@ActiveProfiles("test")
class TripServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TripServiceTest.class);
    private final Long willReturnId = 131L;
    @BeforeEach
    void setUp() {
//        given(AuthenticationFacade.getSignedUserId()).willReturn(willReturnId);
    }

    @Test
    @DisplayName("로그인 유저 여행 리스트 서비스 테스트")
    @Transactional
    void selUserTripListTest() {
//        Long signedUserId = authenticationFacade.getSignedUserId();
//        log.info("signedUserId: {}", signedUserId);
    }
}