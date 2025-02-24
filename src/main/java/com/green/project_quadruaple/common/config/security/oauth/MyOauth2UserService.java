package com.green.project_quadruaple.common.config.security.oauth;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.common.config.security.oauth.userinfo.Oauth2UserInfo;
import com.green.project_quadruaple.common.config.security.oauth.userinfo.Oauth2UserInfoFactory;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.RoleId;
import com.green.project_quadruaple.user.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Oauth2UserInfoFactory oauth2UserInfoFactory;
    private final AuthenticationCodeRepository authenticationCodeRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) {
        try {
            return process(req);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }

    }

    private OAuth2User process(OAuth2UserRequest req) {
        OAuth2User oAuth2User = super.loadUser(req);

        /*
        req.getClientRegistration().getRegistrationId().toUpperCase() : 소셜 로그인으로 신청한 플랫폼 문자열 값이 넘어온다.(yaml에 있는 것들이 넘어옴)
        플랫폼 문자열 값은 spring.security.oauth2.client.registration 아래에 있는 속성값들이다.(google, kakao, naver)
         */
        SignInProviderType signInProviderType = SignInProviderType.valueOf(req.getClientRegistration()
                .getRegistrationId()
                .toUpperCase());

        //사용하기 편하도록 규격화된 객체로 변환
        Oauth2UserInfo oauth2UserInfo = oauth2UserInfoFactory.getOauth2UserInfo(signInProviderType, oAuth2User.getAttributes());

        //기존에 회원가입이 되어있는 지를 체크
        User user = userRepository.findByAuthenticationCode_EmailAndProviderType(oauth2UserInfo.getEmail(), signInProviderType);
        if (user == null) {
            user = new User();
            user.setProviderType(signInProviderType);
            user.setPassword("");
            user.setName(oauth2UserInfo.getName());
            user.setProfilePic(oauth2UserInfo.getProfileImageUrl());

            // 소셜 로그인 시 이메일 인증을 완료된 상태로 설정
            user.setVerified(1);  // 이메일 인증 상태를 1로 설정

            // 소셜 로그인 후 이메일을 authentication_code 테이블에 저장
            String email = oauth2UserInfo.getEmail();
            String code = generateRandomCode();  // 랜덤 인증 코드 생성
            AuthenticationCode authCode = new AuthenticationCode();
            authCode.setEmail(email);
            authCode.setCodeNum(code);
            authCode.setGrantedAt(LocalDateTime.now());

            authenticationCodeRepository.save(authCode);  // 인증 코드 저장

            user.setAuthenticationCode(authCode); // User와 연결

            userRepository.save(user);

            Role role = Role.builder()
                    .id(new RoleId(UserRole.USER.getValue(), user.getUserId()))
                    .user(user)
                    .role(UserRole.USER)
                    .grantedAt(LocalDateTime.now())
                    .build();
            roleRepository.save(role);
        }

        List<UserRole> roles = Arrays.asList(UserRole.USER);

        OAuth2JwtUser oAuth2JwtUser = new OAuth2JwtUser(user.getName(), user.getProfilePic(), user.getUserId(), roles);

        return oAuth2JwtUser;
    }

    private String generateRandomCode() {
        // 랜덤 4자리 인증 코드 생성
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append((int)(Math.random() * 10));
        }
        return code.toString();
    }
}
