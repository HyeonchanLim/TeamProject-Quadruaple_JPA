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
    private final UserRepository userRepository;

    //인증 코드 조회
    public Optional<AuthenticationCode> getAuthenticationCodeByEmail(String email) {
        return authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(email);
    }

    //인증 횟수 조회
    public int countAuthenticationCode(String email) {
        return authenticationCodeRepository.countByEmail(email);
    }

    //인증 횟수 초과로 삭제
    public boolean isEmailExceeded(String email) {
        int count = authenticationCodeRepository.countByEmail(email);
        return count >= 5;
    }

    public int deleteAuthenticationCode(String email) {
        if (isEmailExceeded(email)) {
            int result = authenticationCodeRepository.deleteByEmail(email);
            return result;
        }
        return 0;
    }
}
