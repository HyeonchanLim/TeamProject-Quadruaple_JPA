package com.green.project_quadruaple.user;

import com.green.project_quadruaple.TestUtils;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.entity.repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.entity.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationCodeRepository authenticationCodeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    static final String profilePic = "user_profile.webp";
    static final String name = "user_name";
    static final String tell = "user_tell";
    static final LocalDate birth = LocalDate.of(1990, 1, 1);
    static final String pw = "user_pw";
    static final int state = 0;
    static final int verified = 1;

    User existedUser;
    User notExistedUser;

    @BeforeEach
    void initData() {
        // 외래키 제약 조건 비활성화
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        userRepository.deleteAll();

        AuthenticationCode existedAuthCode = AuthenticationCode.builder()
                .codeNum("123456")  // 적절한 코드 값 넣기
                .email("user@example.com")
                .build();
        authenticationCodeRepository.save(existedAuthCode);

        AuthenticationCode newAuthCode = AuthenticationCode.builder()
                .codeNum("654321")
                .email("new_user@example.com")
                .build();
        authenticationCodeRepository.save(newAuthCode);

        existedUser = new User(
                null,
                existedAuthCode,
                SignInProviderType.LOCAL,
                profilePic,
                name,
                birth,
                pw,
                state,
                verified,
                tell
        );

        notExistedUser = new User(
                null,  // 새 유저니까 ID는 null (DB가 자동 생성)
                newAuthCode,  // 다른 AuthenticationCode 사용
                SignInProviderType.LOCAL,
                "new_profile.webp",
                "new_user_name",
                LocalDate.of(2000, 5, 5),
                "new_user_pw",
                1,
                0,
                "new_user_tell"
        );

        userRepository.save(existedUser);
    }

    @Test
    @Rollback(true)
    void insUser() {
        //when
        List<User> actualUserListBefore = userRepository.findAll();
        User actualUserBefore = userRepository.findById(existedUser.getUserId()).get();

        userRepository.save(notExistedUser);

        List<User> actualUserListAfter = userRepository.findAll();
        User actualUserAfter = userRepository.findById(notExistedUser.getUserId()).get();

        //then
        assertAll(
            () -> TestUtils.assertCurrentTimestamp(actualUserAfter.getCreatedAt())
                , () -> assertEquals(actualUserListBefore.size() + 1, actualUserListAfter.size())
                , () -> assertNotNull(actualUserBefore)
                , () -> assertNotNull(actualUserAfter)

                , () -> assertEquals(notExistedUser.getUserId(), actualUserAfter.getUserId() )
        );
    }

    @AfterEach
    void cleanup() {
        // 외래키 제약 조건 활성화
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

}
