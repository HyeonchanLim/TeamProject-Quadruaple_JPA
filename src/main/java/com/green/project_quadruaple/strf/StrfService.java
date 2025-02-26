package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StrfService {
    private final StrfMapper strfMapper;
    private final AuthenticationFacade authenticationFacade;
    private final GenericResponseService responseBuilder;


    public ResponseWrapper<StrfSelRes> getMemberDetail(Long strfId) {
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

}
