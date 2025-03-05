package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.booking.repository.MenuRepository;
import com.green.project_quadruaple.booking.repository.ParlorRepository;
import com.green.project_quadruaple.booking.repository.RoomRepository;
import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.entity.repository.LocationDetailRepository;
import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.*;
import com.green.project_quadruaple.trip.model.Category;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private final LocationDetailRepository locationDetailRepository;
    private final MyFileUtils myFileUtils;
    private final StrfPicRepository strfPicRepository;

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

    @Transactional
    public ResponseWrapper<Integer> strfIns (List<MultipartFile> strfPic, List<MultipartFile> menuPic , StrfInsReq p){
        long userId = authenticationFacade.getSignedUserId();

        // 사용자 권한 확인 (BUSI 권한이 있는지 확인)
        List<Role> roles = roleRepository.findByUserUserId(userId);
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi){
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(),0 );
        }
        BusinessNum businessNum = businessNumRepository.findByBusiNum(p.getBusiNum());

        LocationDetail locationDetail = locationDetailRepository.findById(p.getLocationDetailId())
                .orElseThrow( () ->  new RuntimeException("locationDetailId wrong input"));

        Category categoryValue = null;
//        if (p.getCategory() != null && Category.getKeyByName(p.getCategory()) != null) {
//            categoryValue = Objects.requireNonNull(Category.getKeyByName(p.getCategory())).getValue();
//        }

        StayTourRestaurFest strf = StayTourRestaurFest.builder()
                .cid(p.getCid())
                .category(categoryValue)
                .title(p.getTitle())
                .lat(p.getLat())
                .lng(p.getLng())
                .address(p.getAddress())
                .locationDetail(locationDetail)
                .post(p.getPost())
                .tell(p.getTell())
                .startAt(p.getStartAt())
                .endAt(p.getEndAt())
                .openCheckIn(p.getOpenCheckIn())
                .closeCheckOut(p.getCloseCheckOut())
                .detail(p.getDetail())
                .busiNum(businessNum)
                .state(p.getState())
                .build();
        strfRepository.save(strf);

        String middlePath = String.format("reviewId/%d", strf.getStrfId());
        myFileUtils.makeFolders(middlePath);

        List<String> picNameList = new ArrayList<>(strfPic.size());
        for (MultipartFile pic : strfPic) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            picNameList.add(savedPicName);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                StrfPic strfPics = new StrfPic();
                strfPics.setStrfId(strf);
                strfPics.setPicName(savedPicName);

                strfPicRepository.save(strfPics);

                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
                String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
                myFileUtils.deleteFolder(delFolderPath, true);
                throw new RuntimeException(e);
            }
        }

//        List<AmenipointId> amenipointIds = AmenipointId.builder()
//                .strfId(strf.getStrfId())
//                .amenityId(p.getAmenipoints())
//                .build();
//Amenipoint
        return null;
    }

}
