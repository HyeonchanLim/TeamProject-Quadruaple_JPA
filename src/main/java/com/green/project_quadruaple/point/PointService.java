package com.green.project_quadruaple.point;

import com.green.project_quadruaple.booking.model.dto.KakaoRefundDto;
import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.view.PointView;
import com.green.project_quadruaple.notice.NoticeService;
import com.green.project_quadruaple.point.model.dto.*;
import com.green.project_quadruaple.point.model.payModel.dto.KakaoApproveDto;
import com.green.project_quadruaple.point.model.payModel.dto.KakaoReadyDto;
import com.green.project_quadruaple.common.config.constant.KakaopayConst;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.common.model.SizeConstants;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.point.model.payModel.req.PointBuyReadyReq;
import com.green.project_quadruaple.point.model.req.CancelPointUsed;
import com.green.project_quadruaple.point.model.req.PointHistoryPostReq;
import com.green.project_quadruaple.point.model.res.PointCardProductRes;
import com.green.project_quadruaple.point.model.res.PointHistoryListReq;
import com.green.project_quadruaple.point.model.res.PointUseRes;
import com.green.project_quadruaple.point.model.res.QRPointRes;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {
    private final PointCardRepository pointCardRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointViewRepository pointViewRepository;
    private final AuthenticationFacade authenticationFacade;
    private final StrfRepository strfRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final NoticeService noticeService;

    private final KakaopayConst kakaopayConst;
    private KakaoReadyDto kakaoReadyDto;

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
        Integer remainPoints = null;
        if (userId != null) {
            remainPoints = pointViewRepository.findLastRemainPointByUserId(userId)==null?
                            0:pointViewRepository.findLastRemainPointByUserId(userId);
        }
        return new PointCardProductRes(remainPoints, pointCardRepository.findAll());
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
                )).collect(toList());
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

    // point 사용
    @Transactional
    public ResponseEntity<ResponseWrapper<PointUseRes>> usePoint(PointHistoryPostReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user=userRepository.findById(userId).get();
        StayTourRestaurFest strf=strfRepository.findById(p.getStrfId()).orElse(null);
        int remainPoint = pointViewRepository.findLastRemainPointByUserId(userId)==null?
                0:pointViewRepository.findLastRemainPointByUserId(userId);
        remainPoint-=p.getAmount();
        if (remainPoint < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_Acceptable.getCode(), null));
        }
        PointHistory pointHistory = PointHistory.builder()
                .amount(p.getAmount()*(-1))
                .category(0)
                .relatedId(p.getStrfId())
                .user(user)
                .remainPoint(remainPoint)
                .build();
        pointHistoryRepository.saveAndFlush(pointHistory);

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), new PointUseRes(remainPoint,p.getAmount(),strf.getTitle())));
    }

    // point 사용 취소
    public ResponseEntity<ResponseWrapper<String>> cancelUsedPoint(CancelPointUsed p) {
        // 이미 환불한 포인트인지 검증도 할것

        long busiId=authenticationFacade.getSignedUserId();
        List<Role> roles = roleRepository.findByUserUserId(busiId);
        List<Long> strfIds = strfRepository.findStrfIdByUserId(busiId);
        boolean hasStrf = strfIds.stream().anyMatch(strfId -> strfId.equals(p.getStrfId()));
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi || !hasStrf) { //사업자 권한이 없거나 자신의 상품이 아니라면 취소 불가
            log.error("포인트 사용취소 권한이 없습니다. 사용자 권한: {}", roles.isEmpty() ? "없음" : roles.get(0).getRole());
            return null;
        }
        PointHistory used=pointHistoryRepository.findById(p.getPointHistoryId()).orElse(null);
        int amount=used.getAmount() * (-1);
        int remainPoint=amount+pointViewRepository.findLastRemainPointByUserId(used.getUser().getUserId());
        PointHistory pointHistory=PointHistory.builder()
                .amount(amount)
                .category(4)
                .user(used.getUser())
                .relatedId(p.getPointHistoryId())
                .remainPoint(remainPoint)
                .build();
        pointHistoryRepository.saveAndFlush(pointHistory);

        //사용 취소 알람 발송
        String title= amount+"P 사용취소!";
        StringBuilder content = new StringBuilder(strfRepository.findTitleByStrfId(p.getStrfId())).append("에서 ")
                .append(amount).append("P 사용취소 정상처리 되었습니다. \n 남은 잔여 포인트: ").append(remainPoint);
        noticeService.postNotice(NoticeCategory.POINT,title,content.toString(),used.getUser(), pointHistory.getPointHistoryId());

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), "성공"));
    }

    //QR코드 확인시 보일 화면
    public ResponseEntity<ResponseWrapper<QRPointRes>> QRscanned(long strfId, int amount){
        long userId = authenticationFacade.getSignedUserId();
        int remainPoint = pointViewRepository.findLastRemainPointByUserId(userId);
        StayTourRestaurFest strf=strfRepository.findById(strfId).get();
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode()
                , new QRPointRes(remainPoint,strf.getTitle(),strf.getLat(),strf.getLng(),amount)));
    }


    // 보유 포인트 확인화면
    public ResponseEntity<ResponseWrapper<PointHistoryListReq>> checkMyRemainPoint
    (LocalDate startAt, LocalDate endAt, Integer category, boolean isDesc) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).get();

        LocalDateTime durationStart = startAt.atStartOfDay();
        LocalDateTime durationEnd = endAt.atTime(LocalTime.MAX);

        Sort sort = Sort.by("pointHistoryId");
        sort = isDesc ? sort.descending() : sort.ascending();

        List<PointView> pointViews = category != null ?
                pointViewRepository.findByUserIdAndCategoryAndCreatedAtBetween(userId, category, durationStart, durationEnd, sort)
                : pointViewRepository.findByUserIdAndCreatedAtBetween(userId, durationStart, durationEnd, sort);

        List<PointHistoryListDto> historys = new ArrayList<>(pointViews.size());
        for (PointView h : pointViews) {
            String usedAt = switch (h.getCategory()) {
                case 0:
                    yield h.getTitle();
                case 1:
                    yield "포인트 충전";
                case 2:
                    yield "포인트 카드 환불";
                case 4:
                    yield "포인트 사용 취소";
                default:
                    yield null;
            };

            PointHistoryListDto dto = new PointHistoryListDto(
                    h.getPointHistoryId(), usedAt, h.getCategory(), h.getCreatedAt(), h.getAmount(), h.getRemainPoint()
            );
            historys.add(dto);
        }

        PointHistoryListReq result = new PointHistoryListReq();
        result.setUserName(user.getName());
        result.setRemainPoint(pointViewRepository.findLastRemainPointByUserId(userId));
        result.setPointList(historys);
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    // 포인트 카드 구매 (결제 준비->요청->승인(approve)
    public ResponseWrapper<String> ReadyToBuyPointCard(PointBuyReadyReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).get();

        PointCard pointCard = pointCardRepository.findById(p.getPointCardId()).get();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeaders();

        HashMap<String, String> parameters = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();
        String orderNo = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (int) (Math.random() * 1000);

        int charge = pointCard.getFinalPayment();
        String totalAmount = String.valueOf(charge);
        String taxFreeAmount = String.valueOf((charge / 10));

        parameters.put("cid", kakaopayConst.getAffiliateCode()); // 가맹점 코드 - 테스트용
        parameters.put("partner_order_id", orderNo); // 주문 번호
        parameters.put("partner_user_id", String.valueOf(userId)); // 회원 아이디
        parameters.put("item_name", pointCard.getAvailable() + " point"); // 상품 명
        parameters.put("quantity", "1"); // 상품 수량
        parameters.put("total_amount", totalAmount); // 상품 가격
        parameters.put("tax_free_amount", taxFreeAmount); // 상품 비과세 금액
        parameters.put("approval_url", kakaopayConst.getPointApprovalUrl()); // 성공시 url
        parameters.put("cancel_url", kakaopayConst.getPointCancelUrl()); // 실패시 url
        parameters.put("fail_url", kakaopayConst.getPointFailUrl());

        HttpEntity<HashMap<String, String>> body = new HttpEntity<>(parameters, headers);

        try {
            kakaoReadyDto = restTemplate.postForObject(new URI(kakaopayConst.getUrl() + "/online/v1/payment/ready"), body, KakaoReadyDto.class);
            log.info("kakaoDto = {}", kakaoReadyDto);
            if (kakaoReadyDto != null) {
                int remainPoint = pointViewRepository.findLastRemainPointByUserId(userId)==null?
                        0:pointViewRepository.findLastRemainPointByUserId(userId);
                int amount = p.getAmount();
                PointHistory pointHistory = PointHistory.builder()
                        .tid(kakaoReadyDto.getTid())
                        .remainPoint(remainPoint + amount)
                        .amount(amount)
                        .user(user)
                        .relatedId(p.getPointCardId())
                        .category(1)
                        .build();
                kakaoReadyDto.setPartnerOrderId(orderNo);
                kakaoReadyDto.setPartnerUserId(String.valueOf(userId));
                kakaoReadyDto.setPointHistory(pointHistory);
                kakaoReadyDto.setRemainPoint(remainPoint);

                return new ResponseWrapper<>(ResponseCode.OK.getCode(), kakaoReadyDto.getNextRedirectPcUrl());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), null);
    }

    public String approveBuy(String pgToken) {
        String userId = kakaoReadyDto.getPartnerUserId();
        User user = userRepository.findById(Long.parseLong(userId)).get();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeaders();

        //카카오 요청
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("cid", kakaopayConst.getAffiliateCode()); // 가맹점 코드 - 테스트용
        parameters.put("tid", kakaoReadyDto.getTid()); // 결제 고유 번호, 준비단계 응답에서 가져옴
        parameters.put("partner_order_id", kakaoReadyDto.getPartnerOrderId()); // 주문 번호
        parameters.put("partner_user_id", userId); // 회원 아이디
        parameters.put("pg_token", pgToken); // 준비 단계에서 리다이렉트떄 받은 param 값

        HttpEntity<HashMap<String, String>> body = new HttpEntity<>(parameters, headers);

        try {
            PointHistory pointHistory = kakaoReadyDto.getPointHistory();
            pointHistoryRepository.saveAndFlush(pointHistory);
            KakaoApproveDto approveDto = restTemplate.postForObject(new URI(kakaopayConst.getUrl() + "/online/v1/payment/approve"), body, KakaoApproveDto.class);
            log.info("approveDto = {}", approveDto);
            if (approveDto == null) {
                throw new RuntimeException();
            }
            String title=pointHistory.getAmount()+" point 구매완료!";
            StringBuilder content = new StringBuilder(title).append(" 즐겁게 쇼핑해 볼까요? 제때 포인트를 충전하는 것을 잊지 마세요!");
            noticeService.postNotice(NoticeCategory.POINT,title,content.toString(),user,pointHistory.getPointHistoryId());

            StringBuilder redirectParams = new StringBuilder("?amount=")
                    .append(URLEncoder.encode(pointHistory.getAmount().toString(), StandardCharsets.UTF_8)).append("&")
                    .append("remain_point=")
                    .append(URLEncoder.encode(pointHistory.getRemainPoint().toString(), StandardCharsets.UTF_8));

            return kakaopayConst.getPointCompleteUrl() + redirectParams;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 환불가능 구매리스트 확인
    public ResponseEntity<ResponseWrapper<List<RefundableDto>>> refundableList () {
        long userId = authenticationFacade.getSignedUserId();
        List<PointHistory> phList=pointHistoryRepository.findRefundablePointHistoriesByUserId(userId);
        List<Long> refunedPhList=phList.stream().filter(p -> p.getCategory() == 2)
                .map(PointHistory::getRelatedId).collect(toList());
        List<PointHistory> refundableHistory=phList.stream()
                .filter(p -> p.getCategory()==1 && ! refunedPhList.contains(p.getPointHistoryId())).toList();
        List<RefundableDto> refundableList=new ArrayList<>();
        for(PointHistory p:refundableHistory){
            refundableList.add(new RefundableDto(p.getPointHistoryId(),p.getAmount(),p.getCreatedAt()));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), refundableList));
    }

    public ResponseWrapper<String> refundPoint(long pointHistoryId) {
        long userId = authenticationFacade.getSignedUserId();

        PointHistory pointHistory = pointHistoryRepository.findById(pointHistoryId).get();
        if (pointHistory.getUser().getUserId() != userId) {
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), "잘못된 접근입니다.");
        }
        int boughtPoint = pointHistory.getAmount();
        int remainPoint = pointViewRepository.findLastRemainPointByUserId(userId)==null?
                0:pointViewRepository.findLastRemainPointByUserId(userId);
        if (boughtPoint > remainPoint) {
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), "환불 불가, 잔여 포인트 부족.");
        }

        try {
            String tid = pointHistory.getTid();
            PointCard wasBought = pointCardRepository.findByAvailable(boughtPoint);
            int refundPrice = wasBought.getFinalPayment();

            PointHistory refundHistory=PointHistory.builder()
                    .remainPoint(remainPoint-pointHistory.getAmount())
                    .relatedId(pointHistoryId)
                    .amount(boughtPoint * (-1))
                    .category(2)
                    .user(userRepository.findById(userId).get())
                    .build();
            pointHistoryRepository.saveAndFlush(refundHistory);
            String refundUrl = "/online/v1/payment/cancel";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = setHeaders();

            HashMap<String, Object> params = new HashMap<>();

            params.put("cid", kakaopayConst.getAffiliateCode());
            params.put("tid", tid);
            params.put("cancel_amount", refundPrice);
            params.put("cancel_tax_free_amount", String.valueOf(refundPrice / 10));

            HttpEntity<HashMap<String, Object>> body = new HttpEntity<>(params, headers);

            //kakaoAPI 요청 보내기
            KakaoRefundDto refundDto = restTemplate.postForObject(kakaopayConst.getUrl() + refundUrl, body, KakaoRefundDto.class);
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), "환불성공");
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", kakaopayConst.getSecretKey());
        headers.add("Content-Type", "application/json");
        return headers;
    }

}
