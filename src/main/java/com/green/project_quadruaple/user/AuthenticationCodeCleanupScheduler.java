package com.green.project_quadruaple.user;

import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.entity.repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationCodeCleanupScheduler {
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final UserRepository userRepository;

    /**
     * 매일 자정(00:00)에 실행되어 5개 이상인 이메일의 인증 코드를 삭제하는 스케줄러
     */
    @Scheduled(cron = "0 0 0 * * ?") // 매일 00:00 실행
    @Transactional
    public void cleanUpExpiredAuthenticationCodes() {
        // 5개 이상인 이메일을 찾아서
        List<String> emailsToDelete = authenticationCodeRepository.findEmailsWithFiveOrMoreCodes();

        for (String email : emailsToDelete) {
            // 해당 이메일로 인증 코드를 모두 가져옴
            List<AuthenticationCode> authenticationCodes = authenticationCodeRepository.findByEmail(email);

            for (AuthenticationCode authenticationCode : authenticationCodes) {
                // 해당 authentication_id가 User 테이블에 존재하는지 확인
                Optional<User> user = userRepository.findByAuthenticationCode_AuthenticatedId(authenticationCode.getAuthenticatedId());

                if (user.isEmpty()) {
                    // 유저가 없으면 해당 인증 코드 삭제
                    authenticationCodeRepository.delete(authenticationCode);
                    System.out.println("삭제된 인증 코드: " + authenticationCode.getCodeNum());
                } else {
                    // 유저가 있으면 삭제하지 않음
                    System.out.println("유저가 존재하여 삭제되지 않은 인증 코드: " + authenticationCode.getCodeNum());
                }
            }
        }

        System.out.println("인증 코드 정리 완료");
    }
}
