package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.user.Repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.user.model.UserSignUpReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)  // Mockito 확장 적용 (JUnit5)
class UserServiceTest extends UserServiceParentTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationCodeRepository authenticationCodeRepository;

    private static final String EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // 최신 방식으로 변경

        AuthenticationCode mockAuthCode = new AuthenticationCode();
        mockAuthCode.setEmail(EMAIL);
        mockAuthCode.setGrantedAt(LocalDateTime.now());

        given(authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(EMAIL))
                .willReturn(Optional.of(mockAuthCode));
    }

    @Test
    @DisplayName("회원가입 테스트 - 프로필 사진 있음")
    @Transactional
    public void insUserProfile() throws Exception {
        // given
        UserSignUpReq p = new UserSignUpReq();
        p.setEmail(EMAIL);
        p.setName("test");
        p.setPw("test");
        p.setState(0);
        p.setVerified(1);

        MockMultipartFile file = new MockMultipartFile("pic", "profile.jpg", "image/jpeg", new byte[]{1, 2, 3});

        // 이메일 중복 체크
        given(userRepository.existsByAuthenticationCode_Email(EMAIL)).willReturn(false);

        // 닉네임 중복 체크
        given(userRepository.existsByName(p.getName())).willReturn(false);

        // 비밀번호 해싱 Mock 설정
        String hashedPassword = "hashedPassword";
        given(passwordEncoder.encode(Mockito.anyString())).willReturn(hashedPassword);

        // 파일 처리 Mock
        String savedPicName = "savedProfilePic.jpg";
        given(myFileUtils.makeRandomFileName(file)).willReturn(savedPicName);
        doNothing().when(myFileUtils).transferToUser(file, savedPicName);

        // ✅ AuthenticationCode 엔티티 먼저 저장
        AuthenticationCode authCode = new AuthenticationCode();
        authCode.setEmail(p.getEmail());
        authCode.setCodeNum("1234");
        authCode.setGrantedAt(LocalDateTime.now());
        authCode.setAuthenticatedId(1L);

        // ✅ User 엔티티 생성 후 AuthenticationCode 설정
        User user = new User();
        user.setAuthenticationCode(authCode); // ✅ ID가 있는 AuthenticationCode를 User에 설정
        user.setName(p.getName());
        user.setPassword(hashedPassword);
        user.setState(p.getState());
        user.setVerified(p.getVerified());
        user.setProfilePic(savedPicName);
        user.setProviderType(SignInProviderType.LOCAL);
        user.setUserId(1L);

        // userRepository.save() 호출 시 user 반환하도록 설정
        given(userRepository.save(Mockito.any(User.class))).willReturn(user);

        // when
        int result = userService.signUp(file, p);

        // then
        assertEquals(1, result);  // 회원가입이 성공적으로 완료되어야 함

        // ✅ AuthenticationCode가 저장되었는지 검증
        ArgumentCaptor<AuthenticationCode> authCaptor = ArgumentCaptor.forClass(AuthenticationCode.class);
        verify(authenticationCodeRepository).save(authCaptor.capture());

        AuthenticationCode savedAuthCode = authCaptor.getValue();
        assertEquals(EMAIL, savedAuthCode.getEmail());
        assertNotNull(savedAuthCode.getAuthenticatedId()); // ✅ ID가 정상적으로 부여되었는지 확인

        // ✅ User가 저장되었는지 검증
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(EMAIL, savedUser.getAuthenticationCode().getEmail());
        assertEquals(savedAuthCode.getAuthenticatedId(), savedUser.getAuthenticationCode().getAuthenticatedId()); // ✅ authentication_id가 들어갔는지 확인
        assertEquals("hashedPassword", savedUser.getPassword());
        assertEquals(savedPicName, savedUser.getProfilePic());
    }

}
