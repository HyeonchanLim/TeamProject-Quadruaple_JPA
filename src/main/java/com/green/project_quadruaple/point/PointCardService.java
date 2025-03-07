package com.green.project_quadruaple.point;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.PointCard;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.point.model.dto.PointCardGetDto;
import com.green.project_quadruaple.point.model.res.PointCardProductRes;
import com.green.project_quadruaple.point.model.dto.PointCardPostDto;
import com.green.project_quadruaple.point.model.dto.PointCardUpdateDto;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointCardService {
    private final PointCardRepository pointCardRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final AuthenticationFacade authenticationFacade;
    private final RoleRepository roleRepository;

    //관리자 포인트 상품 추가
    public int intPointCard(PointCardPostDto dto) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (ADMIN 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);

        if (!isAdmin) {
            log.error("상품권 발급 권한이 없습니다. 사용자 권한: {}", roles.isEmpty() ? "없음" : roles.get(0).getRole());
            return 0;  // 권한이 없으면 상품권 발급하지 않음
        }

        // 포인트 카드 객체 생성
        PointCard pointCard = new PointCard();
        pointCard.setAvailable(dto.getAvailable());
        pointCard.setDiscountPer(dto.getDiscountPer());

        // 할인율 적용하여 최종 결제 금액 계산
        double discountRate = 1 - 0.01 * dto.getDiscountPer();
        int finalPayment = (int) Math.round(dto.getAvailable() * discountRate);
        pointCard.setFinalPayment(finalPayment);

        pointCardRepository.save(pointCard);

        return 1;
    }

    // 회원 or 비회원 포인트 카드 조회
    public PointCardProductRes getPointCardProduct() {
        Long userId = authenticationFacade.getSignedUserId();
        Integer remainPoints=null;
        if(userId!=null){
            remainPoints=pointHistoryRepository.findRemainPointByUserId(userId);
        }
        return new PointCardProductRes(remainPoints,pointCardRepository.findAll());
    }



    public List<PointCardGetDto> getPointCard() {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (ADMIN 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);

        if (!isAdmin) {
            log.error("상품권 조회 권한이 없습니다. 사용자 권한: {}", roles.isEmpty() ? "없음" : roles.get(0).getRole());
            return new ArrayList<>();  // 권한이 없으면 상품권 조회 불가능
        }

        return pointCardRepository.findAll().stream()
                .map(pointCard -> new PointCardGetDto(
                        pointCard.getPointCardId(),
                        pointCard.getAvailable(),
                        pointCard.getDiscountPer(),
                        pointCard.getFinalPayment()
                )).collect(Collectors.toList());
    }

    public int updPointCard(PointCardUpdateDto dto) {
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (ADMIN 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == UserRole.ADMIN);

        if (!isAdmin) {
            log.error("상품권 수정 권한이 없습니다. 사용자 권한: {}", roles.isEmpty() ? "없음" : roles.get(0).getRole());
            return 0;  // 권한이 없으면 상품권 수정 불가능
        }

        // 기존 PointCard 조회 (존재하지 않으면 예외 발생)
        PointCard pointCard = pointCardRepository.findById(dto.getPointCardId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품권입니다."));


        // 선택적으로 필드만 업데이트
        if (dto.getAvailable() != 0) {
            pointCard.setAvailable(dto.getAvailable());
        }

        if (dto.getDiscountPer() != 0) {
            pointCard.setDiscountPer(dto.getDiscountPer());
        }

        // discountPer 변경 시, finalPayment를 다시 계산
        if (dto.getDiscountPer() != 0 || dto.getAvailable() != pointCard.getAvailable()) {
            // 할인율 적용하여 최종 결제 금액 계산
            double discountRate = 1 - 0.01 * pointCard.getDiscountPer();
            int finalPayment = (int) Math.round(pointCard.getAvailable() * discountRate);
            pointCard.setFinalPayment(finalPayment);
        }

        // 변경 사항 저장
        pointCardRepository.save(pointCard);

        return 1;
    }

    // 보유 포인트 확인화면
    //public
}
