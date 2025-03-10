package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.user.Repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.mail.MailService;
import com.green.project_quadruaple.user.model.DuplicateEmailResult;
import com.green.project_quadruaple.user.model.UserSignUpReq;
import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest extends UserServiceParentTest{
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationCodeRepository authenticationCodeRepository;

    @Test
    @DisplayName("회원가입 테스트 - 프로필 사진 있음")
    public void insUserProfile() throws Exception {
        // given
        UserSignUpReq p = new UserSignUpReq();
        p.setEmail(EMAIL);
        p.setName("test");
        p.setPw("test");
        p.setState(0);
        p.setVerified(0);

        MockMultipartFile file = new MockMultipartFile("pic", "profile.jpg", "image/jpeg", new byte[]{1, 2, 3});

        // 이메일 중복 체크
        given(userRepository.existsByAuthenticationCode_Email(EMAIL)).willReturn(Boolean.FALSE);  // 중복되지 않음

        // 닉네임 중복 체크
        given(userRepository.existsByName(p.getName())).willReturn(Boolean.FALSE);

        // 비밀번호 해싱
        String hashedPassword = "hashedPassword"; // 테스트에서는 단순히 문자열을 사용
        given(passwordEncoder.encode(p.getPw())).willReturn(hashedPassword);

        // 인증 코드가 존재하는 것으로 Mock 처리
        AuthenticationCode mockAuthCode = new AuthenticationCode();
        mockAuthCode.setEmail(EMAIL);
        given(authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(EMAIL))
                .willReturn(Optional.of(mockAuthCode));

        // 파일 처리 Mock
        String savedPicName = "savedProfilePic.jpg";  // 파일 이름 mock
        given(myFileUtils.makeRandomFileName(file)).willReturn(savedPicName);
        doNothing().when(myFileUtils).transferToUser(file, savedPicName);  // void 메서드에 doNothing() 사용

        // when
        int result = userService.signUp(file, p);

        // then
        assertEquals(1, result);  // 회원가입이 성공적이어야 함
        verify(userRepository, times(1)).save(Mockito.any());  // userRepository.save()가 한 번 호출되어야 함
        verify(myFileUtils, times(1)).transferToUser(file, savedPicName);  // 파일 전송이 한 번 호출되어야 함
    }
}