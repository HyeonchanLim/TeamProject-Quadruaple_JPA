package com.green.project_quadruaple.recent.service;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.recent.RecentMapper;
import com.green.project_quadruaple.recent.model.RecentGetListRes;
import com.green.project_quadruaple.recent.repository.RecentRepository;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecentService {
    private final RecentMapper recentMapper;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final StrfRepository strfRepository;
    private final RecentRepository recentRepository;

    @Value("${const.default-review-size}")
    private int size;

    public ResponseWrapper<List<RecentGetListRes>> recentList (){
        Long userId = authenticationFacade.getSignedUserId();
        List<RecentGetListRes> res = recentMapper.recentList(userId);

        try {
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
        } catch (Exception e) {
            return new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null);
        }
    }

    @Transactional
    public ResponseWrapper<Long> recentHide(Long strfId) {
        Long userId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("user id not fount"));
        StayTourRestaurFest strf = strfRepository
                .findById(strfId).orElseThrow( () -> new RuntimeException("strf id not fount"));

        int updatedRows = recentRepository.hideRecent(user.getUserId(),strf.getStrfId());

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), (long) updatedRows);
    }

    @Transactional
    public ResponseWrapper<Long> recentAllHide() {
        Long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("user id not fount"));
        int updatedRows = recentRepository.hideAllRecent(user.getUserId());
        if (updatedRows == 0) {
            throw new RuntimeException("No recent records found for this user");
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), (long) updatedRows);
    }
}
