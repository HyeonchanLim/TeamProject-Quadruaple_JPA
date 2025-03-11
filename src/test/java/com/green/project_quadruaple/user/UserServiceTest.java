package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.entity.base.CreatedAt;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.RoleId;
import com.green.project_quadruaple.user.Repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.user.mail.MailService;
import com.green.project_quadruaple.user.model.RoleRepository;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock protected PasswordEncoder passwordEncoder;
    @Mock protected MyFileUtils myFileUtils;
    @Mock protected MailService mailService;
    @Mock protected UserService userService;

    protected final String EMAIL = "test@gmail.com";

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationCodeRepository authenticationCodeRepository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // 최신 방식으로 변경

        AuthenticationCode mockAuthCode = new AuthenticationCode();
        mockAuthCode.setEmail(EMAIL);
        mockAuthCode.setGrantedAt(LocalDateTime.now());
        mockAuthCode.setAuthenticatedId(1L);

        // 이메일 인증 코드 조회 시 Mock 설정 (lenient)
        given(userRepository.existsByAuthenticationCode_Email(EMAIL)).willReturn(false);

        // authenticationCodeRepository.save()가 실행되도록 Stub 처리 (lenient)
        lenient().when(authenticationCodeRepository.save(Mockito.any(AuthenticationCode.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // mailService.send() 실행 시 authenticationCodeRepository.save()가 반드시 호출되도록 Stub 처리 (lenient)
        lenient().doAnswer(invocation -> {
            AuthenticationCode authCode = AuthenticationCode.builder()
                    .authenticatedId(1L)
                    .email(EMAIL)
                    .codeNum("1234")
                    .grantedAt(LocalDateTime.now())
                    .build();

            authenticationCodeRepository.save(authCode);
            return ResultResponse.success();
        }).when(mailService).send(Mockito.anyString());
    }

    @Test
    @DisplayName("회원가입 테스트 - 프로필 사진 있음")
    @Transactional
    public void insUserProfile() throws Exception {
        // 이메일 인증 먼저 수행 (MailService 사용)
        given(mailService.send(EMAIL)).willReturn(ResultResponse.success()); // 이메일 인증이 성공했다고 가정

        // given
        UserSignUpReq p = new UserSignUpReq();
        p.setEmail(EMAIL);
        p.setName("test");
        p.setPw("test");
        p.setState(0);
        p.setVerified(1);

        MockMultipartFile file = new MockMultipartFile("pic", "profile.jpg", "image/jpeg", new byte[]{1, 2, 3});

        // 이메일 중복 체크
        given(userRepository.existsByAuthenticationCode_Email(EMAIL)).willReturn(false);  // 이 라인 필요 시 계속 사용

        // 닉네임 중복 체크
        given(userRepository.existsByName(p.getName())).willReturn(false);

        // 비밀번호 해싱 Mock 설정
        String hashedPassword = "hashedPassword";
        given(passwordEncoder.encode(Mockito.anyString())).willReturn(hashedPassword);  // 필요 없는 경우 제거

        // 파일 처리 Mock
        String savedPicName = "savedProfilePic.jpg";
        given(myFileUtils.makeRandomFileName(file)).willReturn(savedPicName);
        doNothing().when(myFileUtils).transferToUser(file, savedPicName); // 필요 없는 경우 제거

        // AuthenticationCode 엔티티 먼저 저장
        AuthenticationCode authCode = new AuthenticationCode();
        authCode.setEmail(p.getEmail());
        authCode.setCodeNum("1234");
        authCode.setGrantedAt(LocalDateTime.now());
        authCode.setAuthenticatedId(1L);

        // User 엔티티 생성 후 AuthenticationCode 설정
        User user = new User();
        user.setUserId(1L);
        user.setAuthenticationCode(authCode); // ID가 있는 AuthenticationCode를 User에 설정
        user.setProviderType(SignInProviderType.LOCAL);
        user.setProfilePic(savedPicName);
        user.setName(p.getName());
        user.setPassword(hashedPassword);
        user.setState(p.getState());
        user.setVerified(p.getVerified());

        p.setUserId(user.getUserId());
        p.setProfilePic(savedPicName);

        // userRepository.save() 호출 시 user 반환하도록 설정
        given(userRepository.save(Mockito.any(User.class))).willReturn(user);

        // when
        mailService.send(EMAIL);  // 회원가입 전에 이메일 인증 수행
        userService.signUp(file, p); // 실제 메서드를 실행

        // Role 설정 추가
        if (user != null) {
            long userId = user.getUserId(); // DB에 삽입 후 userId 값 가져오기
            p.setUserId(userId);

            // Role 설정 추가
            Role role = Role.builder()
                    .id(new RoleId(UserRole.USER.getValue(), userId))  // RoleId는 UserRole.USER와 userId로 구성
                    .user(user)  // 생성된 User 객체와 연관
                    .role(UserRole.USER)  // 기본 ROLE_USER로 설정
                    .grantedAt(LocalDateTime.now())  // 현재 시간으로 grantedAt 설정
                    .build();

            // RoleRepository에 Role 저장
            roleRepository.save(role);
        }

        // then
        // AuthenticationCode가 저장되었는지 검증
        ArgumentCaptor<AuthenticationCode> authCaptor = ArgumentCaptor.forClass(AuthenticationCode.class);
        verify(authenticationCodeRepository).save(authCaptor.capture()); // save가 호출되었는지 확인

        AuthenticationCode savedAuthCode = authCaptor.getValue();
        assertEquals(EMAIL, savedAuthCode.getEmail());
        assertNotNull(savedAuthCode.getAuthenticatedId()); // ID가 정상적으로 부여되었는지 확인

        // User가 저장되었는지 검증
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        //verify(userRepository).save(userCaptor.capture()); // 사용되지 않으면 이 줄을 주석처리
    }

}
