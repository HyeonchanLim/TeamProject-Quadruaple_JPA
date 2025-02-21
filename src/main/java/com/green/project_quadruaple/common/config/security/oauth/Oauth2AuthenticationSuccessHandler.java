package com.green.project_quadruaple.common.config.security.oauth;

import com.green.project_quadruaple.common.config.CookieUtils;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.model.GlobalOauth2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Oauth2AuthenticationRequestBasedOnCookieRepository repository;
    private final TokenProvider tokenProvider;
    private final GlobalOauth2 globalOauth2;
    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException, ServletException {
        if (res.isCommitted()) {//응답 객체가 만료된 경우(이전 프로세스에서 응답 처리를 했는 상태)
            log.error("onAuthenticationSuccess called with a committed response {}", res);
            return;
        }
        String targetUrl = this.determineTargetUrl(req, res, auth);
        log.info("onAuthenticationSuccess targetUrl={}", targetUrl);
        clearAuthenticationAttributes(req, res);

        //"fe/redirect?access_token=dddd&user_id=12"
        getRedirectStrategy().sendRedirect(req, res, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        String redirectUrl = cookieUtils.getValue(req, globalOauth2.getRedirectUriParamCookieName(), String.class);
        log.info("determineTargetUrl > getDefaultTargetUrl(): {}", getDefaultTargetUrl());
        String targetUrl = redirectUrl == null ? getDefaultTargetUrl() : redirectUrl;

        JwtUser jwtUser = (JwtUser) auth.getPrincipal(); // MyUserDetails -> JwtUser로 변경
        long signedUserId = jwtUser.getSignedUserId();

        String nickName = ""; // 기본적으로 빈 값 설정
        String pic = ""; // 기본적으로 빈 값 설정

        // OAuth2JwtUser로 캐스팅하여 nickName과 pic 가져오기
        if (jwtUser instanceof OAuth2JwtUser) {
            OAuth2JwtUser oAuth2JwtUser = (OAuth2JwtUser) jwtUser;
            nickName = oAuth2JwtUser.getNickName();
            pic = oAuth2JwtUser.getPic();
        }

        //AT, RT 생성
        String accessToken = tokenProvider.generateToken(jwtUser, Duration.ofHours(8));
        String refreshToken = tokenProvider.generateToken(jwtUser, Duration.ofDays(15));

        int maxAge = 1_296_000; // 15 * 24 * 60 * 60 > 15일의 초(second) 값
        cookieUtils.setCookie(res, "refreshToken", refreshToken, maxAge, "/api/user/access-token");

        /*
        쿼리스트링 생성
        targetUrl: /fe/redirect
        accessToken: aaa
        userId: 20
        nickName: 홍길동
        pic: abc.jpg

        "?access_token=aaa&user_id=20&nick_name=홍길동&pic=abc.jpg"
         */
        return redirectUrl == null ? "/" : UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", accessToken)
                .queryParam("user_id", signedUserId)
                .queryParam("nick_name", nickName).encode()
                .queryParam("pic", pic)
                .build()
                .toUriString();

    }

    private void clearAuthenticationAttributes(HttpServletRequest req, HttpServletResponse res) {
        super.clearAuthenticationAttributes(req);
        repository.removeAuthorizationCookies(res);
    }
}
