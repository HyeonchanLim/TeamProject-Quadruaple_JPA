package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.booking.repository.MenuRepository;
import com.green.project_quadruaple.booking.repository.ParlorRepository;
import com.green.project_quadruaple.booking.repository.RoomRepository;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.BusinessNum;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.*;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StrfService {
    private final StrfMapper strfMapper;
    private final AuthenticationFacade authenticationFacade;
    private final AmenipointRepository amenipointRepository;
    private final AmenityRepository amenityRepository;
    private final MenuRepository menuRepository;
    private final ParlorRepository parlorRepository;
    private final RoomRepository repository;
    private final RoleRepository roleRepository;
    private final StrfRepository strfRepository;
    private final BusinessNumRepository businessNumRepository;


    public ResponseWrapper<StrfSelRes> getMemberDetail(String strfId) {
        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof JwtUser) {
            userId = authenticationFacade.getSignedUserId();
        }

        if (strfId == null){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }

        StrfSelRes res = strfMapper.getMemberDetail(userId,strfId);

        if (res == null) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }

        if (res.getRatingAvg() != null){
            double roundedRating = Math.round(res.getRatingAvg() * 10) / 10.0;
            res.setRatingAvg(roundedRating);
        }

        if (userId > 0){
            strfMapper.strfUpsert(userId,strfId);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    public ResponseWrapper<GetNonDetail> getNonMemberDetail (Long strfId){
        if (strfId == null){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        GetNonDetail detail = strfMapper.getNonMemberDetail(strfId);
        double roundedRating = Math.round(detail.getRatingAvg() * 10) / 10.0;
        detail.setRatingAvg(roundedRating);
        if (detail == null){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), detail);
    }

    @Transactional
    public ResponseWrapper<Integer> strfIns (StrfInsReq p){
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi){
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(),0 );
        }
        BusinessNum businessNum = businessNumRepository.findByBusiNum(p.getBusiNum());

        // 관련된 StayTourRestaurFest 정보 조회
//        StayTourRestaurFest stayTourRestaurFest = strfRepository.findById(p.getStrfId()).orElseThrow()
//                .orElseThrow(() -> new RuntimeException("Invalid StayTourRestaurFest ID"));
//
//        // 해당 사업자가 올린 게시물인지 확인
//        if (!stayTourRestaurFest.getBusiNum().getUser().getUserId().equals(userId)) {
//            log.error("해당 사업자가 올린 게시물이 아닙니다. 사용자 ID: {}", userId);
//            return 0;  // 사업자가 올린 게시물이 아니면 쿠폰 발급하지 않음
//        }

        return null;
    }

}
