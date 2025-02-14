package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StrfService {
    private final StrfMapper strfMapper;
    private final AuthenticationFacade authenticationFacade;


    public ResponseWrapper<StrfSelRes> getMemberDetail(Long strfId) {
        Long signedUserId = authenticationFacade.getSignedUserId();
        if (signedUserId == null || strfId == null){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }


        StrfSelRes res = strfMapper.getMemberDetail(signedUserId,strfId);

        if (res == null) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }

        strfMapper.strfUpsert(signedUserId,strfId);

        double roundedRating = Math.round(res.getRatingAvg() * 10) / 10.0;
        res.setRatingAvg(roundedRating);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    public ResponseWrapper<GetNonDetail> getNonMemberDetail (Long strfId){
        if (strfId == null){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        GetNonDetail detail = strfMapper.getNonMemberDetail(strfId);
        if (detail == null){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), detail);
    }

}
