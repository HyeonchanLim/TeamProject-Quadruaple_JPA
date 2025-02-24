package com.green.project_quadruaple.common.config.security;
// Spring Security 세팅

import com.green.project_quadruaple.common.config.jwt.JwtAuthenticationEntryPoint;
import com.green.project_quadruaple.common.config.jwt.TokenAuthenticationFilter;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.oauth.*;
import com.green.project_quadruaple.common.model.GlobalOauth2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration // 빈등록 가능, 메소드 빈등록이 있어야 의미가 있다.
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //OAuth2
    private final Oauth2AuthenticationCheckRedirectUriFilter oauth2AuthenticationCheckRedirectUriFilter;
    private final Oauth2AuthenticationRequestBasedOnCookieRepository repository;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    private final MyOauth2UserService myOauth2UserService;
    private final GlobalOauth2 globalOauth2;
    // Spring Security 기능 비활성화(Spring Security 가 관여하지 않았으면 하는 부분)
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                         .requestMatchers(new AntPathRequestMatcher("/static/**"));
//    }
    @Bean // Spring이 메소드 호출을 하고 리턴한 객체의 주소값을 관리한다.(빈 등록)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session을 security가 사용하지 않는다
                .httpBasic(h -> h.disable()) // SSR(Server Side Rendering)이 아니다. 화면을 만들지 않을 것이기 때문에 비활성화 시킨다. 시큐리티 로그인 창이 나타나지 않을 것이다.
                .formLogin(form -> form.disable()) // SSR(Server Side Rendering)이 아니다. 폼로그인 기능 자체를 비활성화
                .csrf(csrf -> csrf.disable()) // SSR(Server Side Rendering)이 아니다. 보안 관련 SSR이 아니면 보안 이슈가 없기 때문에 기능을 끈다.
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .authorizeHttpRequests(req -> req.requestMatchers(HttpMethod.GET, "/api/user/userInfo").hasAuthority(UserRole.USER.name())  // "USER"로 체크됨
                        .requestMatchers(HttpMethod.PATCH, "/api/user").hasAuthority(UserRole.USER.name()) // "USER"로 체크됨
                        .anyRequest().permitAll())// 나머지 요청은 모두 허용
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login( oauth2 -> oauth2.authorizationEndpoint( auth -> auth.baseUri(globalOauth2.getBaseUri())
                                .authorizationRequestRepository(repository))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/*/oauth2/code/*")) // BE가 사용하는 redirect Uri, 플랫폼마다 설정을 할 예정
                        .userInfoEndpoint(userInfo -> userInfo.userService(myOauth2UserService))
                        .successHandler(oauth2AuthenticationSuccessHandler)
                        .failureHandler(oauth2AuthenticationFailureHandler))
                .build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedMethods(List.of("*"));
//        configuration.setAllowedHeaders(List.of("*"));
//        //configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}