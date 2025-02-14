//package com.green.project_quadruaple.common.config.security;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.green.project_quadruaple.common.config.jwt.JwtUser;
//import com.green.project_quadruaple.common.config.jwt.UserRole;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@Setter
//@Getter
//public class MyUserDetails implements UserDetails {
//
//    private JwtUser jwtUser;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // UserRole Enum을 권한 문자열로 변환하여 SimpleGrantedAuthority 생성
//        return jwtUser.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.name())) // Enum의 이름을 권한 문자열로 사용
//                .toList();
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return String.valueOf(jwtUser.getSignedUserId());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
//}
