package com.green.project_quadruaple.user;

import com.green.project_quadruaple.entity.model.AuthenticationCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationCodeService {
    private final AuthenticationCodeRepository authenticationCodeRepository;

    //인증 코드 조회
    public Optional<AuthenticationCode> getAuthenticationCodeByEmail(String email) {
        return authenticationCodeRepository.findByEmail(email);
    }

    //인증 횟수 조회
    public int countAuthenticationCode(String email) {
        return authenticationCodeRepository.countByEmail(email);
    }

    public boolean isEmailExceeded(String email) {
        int count = authenticationCodeRepository.countByEmail(email);
        return count > 5;
    }
}
