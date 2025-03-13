package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.base.Period;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.Trip;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.trip.model.dto.TripDto;
import com.green.project_quadruaple.user.Repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class TripMapperTest {

    @Autowired TripMapper tripMapper;

    @Autowired UserRepository userRepository;

    @Autowired AuthenticationCodeRepository authenticationCodeRepository;

    @Autowired private TripRepository tripRepository;

    @PersistenceContext private EntityManager em;

    private User user = null;

    private final Long locationId = 1L;

    @BeforeEach
    void initData() {

        em.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 159").executeUpdate();
        em.createNativeQuery("ALTER TABLE authentication_code AUTO_INCREMENT = 158").executeUpdate();
        em.createNativeQuery("ALTER TABLE trip AUTO_INCREMENT = 1151").executeUpdate();
        em.createNativeQuery("ALTER TABLE trip_user AUTO_INCREMENT = 1780").executeUpdate();

        AuthenticationCode existedAuthCode = AuthenticationCode.builder()
                .codeNum("123456")
                .email("user@example.com")
                .build();
        authenticationCodeRepository.save(existedAuthCode);

        user = new User(
                null,
                existedAuthCode,
                SignInProviderType.LOCAL,
                "test_profile.webp",
                "test_user_name",
                LocalDate.of(2000, 5, 5),
                "test_user_pw",
                1,
                0,
                "test_user_tell"
        );
        userRepository.save(user);
        userRepository.flush();
    }

    @Test
    @Transactional
    @DisplayName("로그인 유저의 여행 목록 조회")
    public void selUserTripListTest() {

        // given
        Period period = new Period(LocalDate.of(2025,3,11), LocalDate.of(2025,3,16));
        Trip trip = Trip.builder()
                .title("test_title")
                .manager(user)
                .period(period)
                .build();
        tripRepository.save(trip);
        tripRepository.flush();
        tripMapper.insTripUser(trip.getTripId(), List.of(user.getUserId()));
        tripMapper.insTripLocation(trip.getTripId(), List.of(locationId));

        // when
        List<TripDto> tripList = tripMapper.getTripList(user.getUserId());

        // then
        for (TripDto tripDto : tripList) {
            assertThat(tripDto.getTripId()).isEqualTo(trip.getTripId());
            assertThat(tripDto.getTitle()).isEqualTo(trip.getTitle());
            assertThat(tripDto.getStartAt()).isEqualTo(String.valueOf(trip.getPeriod().getStartAt()));
            assertThat(tripDto.getEndAt()).isEqualTo(String.valueOf(trip.getPeriod().getEndAt()));
            assertThat(tripDto.getScheduleCnt()).isEqualTo(0);
        }

    }
}