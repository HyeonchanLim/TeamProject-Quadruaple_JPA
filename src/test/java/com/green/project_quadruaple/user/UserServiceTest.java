package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.CookieUtils;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.entity.repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.entity.repository.TemporaryPwRepository;
import com.green.project_quadruaple.entity.repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import com.green.project_quadruaple.user.model.UserSignInReq;
import com.green.project_quadruaple.user.model.UserSignInRes;
import com.green.project_quadruaple.user.model.UserSignUpReq;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "file.directory=D:/LHC/download/quadruaple")
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock private UserRepository userRepository;
    @Mock private AuthenticationCodeRepository authenticationCodeRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private TemporaryPwRepository temporaryPwRepository;

    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MyFileUtils myFileUtils;
    @Mock private TokenProvider jwtTokenProvider;
    @Mock private CookieUtils cookieUtils;
    @Mock private AuthenticationFacade authenticationFacade;

    static final String FROM_ADDRESS = "quadrupleart@gmail.com";
    static final String FILE_DIRECTORY = "D:/LHC/download/quadruaple";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "fileDirectory", FILE_DIRECTORY); // fileDirectory 직접 주입
    }

    @Test
    @DisplayName("회원가입")
    void signUp_withNoPic_shouldUseDefaultPic() throws IOException {
        // given
        UserSignUpReq signUpReq = new UserSignUpReq();
        signUpReq.setEmail("test@example.com");
        signUpReq.setPw("password!");
        signUpReq.setName("홍길동");
        signUpReq.setBirth(LocalDate.of(2000, 1, 1));
        signUpReq.setTell("010-0000-0000");
        signUpReq.setVerified(1);
        signUpReq.setRole(Arrays.asList(UserRole.USER));
        signUpReq.setProfilePic(null); // 프로필 사진 없음

        AuthenticationCode authCode = new AuthenticationCode();
        authCode.setEmail("test@example.com");
        authCode.setCodeNum("123456");
        authCode.setGrantedAt(LocalDateTime.now());

        doReturn(Optional.of(authCode))
                .when(authenticationCodeRepository).findFirstByEmailOrderByGrantedAtDesc("test@example.com");

        given(userRepository.existsByAuthenticationCode_Email("test@example.com")).willReturn(false);
        given(userRepository.existsByName("홍길동")).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("hashedPassword");

        // MultipartFile은 null로 설정하여 프로필 사진이 없는 경우 테스트
        MultipartFile pic = mock(MultipartFile.class); // 사진을 moc

        String savedPicName = "user_profile.webp";

        // 프로필 사진 관련 처리 추가
        given(myFileUtils.makeRandomFileName(any(MultipartFile.class))).willReturn(savedPicName); // 기본 파일명으로 설정
        when(myFileUtils.makeFolders(anyString())).thenReturn("user/1");  // 디렉토리 생성 Mocking

        // getExt가 null을 반환할 가능성을 방지
        given(myFileUtils.getExt(anyString())).willReturn(".webp");

        // userRepository.save() Mocking - userId와 profilePic 기본값 설정
        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> {
                    User savedUser = invocation.getArgument(0);
                    savedUser.setUserId(1L);
                    signUpReq.setUserId(savedUser.getUserId());// userId 설정
                    if (savedUser.getProfilePic() == null) {
                        savedUser.setProfilePic(savedPicName);
                    }
                    return savedUser;
    });

        signUpReq.setProfilePic(savedPicName);// 기본 프로필 이미지 설정

        // RoleRepository mock 설정
        given(roleRepository.save(any(Role.class))).willAnswer(invocation -> {
            Role savedRole = invocation.getArgument(0);
            savedRole.getId().setUserId(1L); // Role에도 userId 설정
            return savedRole;
        });

        // when
        int result = userService.signUp(pic, signUpReq);

        // then
        assertEquals(1, result);  // 회원가입이 정상적으로 진행되면 1이 반환됨
        verify(myFileUtils).makeFolders(anyString());  // 폴더 생성 메서드 호출 확인
        verify(userRepository).save(any(User.class));  // 유저가 저장된 것을 검증
        verify(roleRepository).save(any(Role.class));  // 역할이 저장된 것을 검증
    }

    @Test
    @DisplayName("로그인")
    void signIn() {
        // 테스트용 요청 객체 생성
        UserSignInReq req = new UserSignInReq("test@example.com", "password");

        // Mock AuthenticationCode 객체 생성
        AuthenticationCode authCode = new AuthenticationCode();
        authCode.setEmail("test@example.com");
        authCode.setCodeNum("123456");
        authCode.setGrantedAt(LocalDateTime.now());

        // Mock User 객체 생성
        User user = new User();
        user.setUserId(1L); // 예시로 ID 설정
        user.setProviderType(SignInProviderType.LOCAL); // 예시로 LOCAL 설정
        user.setName("Test User");
        user.setPassword("hashedPassword");
        user.setVerified(1); // 이메일 인증된 상태
        user.setState(1); // 활성화된 상태
        user.setTell("123-456-7890"); // 예시로 전화번호 설정
        user.setAuthenticationCode(authCode); // 인증 코드 객체 설정

        // Mock 설정
        given(userRepository.findByAuthenticationCode_EmailAndProviderType("test@example.com", SignInProviderType.LOCAL))
                .willReturn(user);
        given(passwordEncoder.matches("password", "hashedPassword")).willReturn(true);

        // 이메일에 대한 인증 코드 조회를 mock
        doReturn(Optional.of(authCode))
                .when(authenticationCodeRepository).findFirstByEmailOrderByGrantedAtDesc("test@example.com");

        //JwtTokenProvider Mock 설정
        String fakeAccessToken = "fakeAccessToken";
        String fakeRefreshToken = "fakeRefreshToken";
        given(jwtTokenProvider.generateToken(any(JwtUser.class), eq(Duration.ofHours(6))))
                .willReturn(fakeAccessToken);  // accessToken을 반환
        given(jwtTokenProvider.generateToken(any(JwtUser.class), eq(Duration.ofDays(15))))
                .willReturn(fakeRefreshToken);  // refreshToken을 반환

        // CookieUtils Mock 설정
        CookieUtils cookieUtils = Mockito.mock(CookieUtils.class);

        // Mock HttpServletResponse
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        UserSignInRes.builder()
                .accessToken(fakeAccessToken)
                .userId(user.getUserId())
                .name(user.getName())
                .roles(Arrays.asList(UserRole.USER))
                .build();

        // signIn() 호출
        UserSignInRes result = userService.signIn(req, response);  // 서비스 인스턴스를 사용하여 호출


        // 결과 검증
        assertNotNull(result); // 결과 객체가 null이 아님을 확인
        assertEquals(1L, result.getUserId()); // userId 확인
        assertEquals(fakeAccessToken, result.getAccessToken()); // accessToken 확인
    }
}