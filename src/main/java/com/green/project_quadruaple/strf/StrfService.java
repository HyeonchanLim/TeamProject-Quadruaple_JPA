package com.green.project_quadruaple.strf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.project_quadruaple.booking.repository.MenuRepository;
import com.green.project_quadruaple.booking.repository.ParlorRepository;
import com.green.project_quadruaple.booking.repository.RoomRepository;
import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.coupon.repository.CouponRepository;
import com.green.project_quadruaple.coupon.repository.ReceiveCouponRepository;
import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.entity.repository.LocationDetailRepository;
import com.green.project_quadruaple.notice.NoticeService;
import com.green.project_quadruaple.strf.model.*;
import com.green.project_quadruaple.strf.model.StrfMenu;
import com.green.project_quadruaple.trip.model.Category;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StrfService {
    private final StrfMapper strfMapper;
    private final AuthenticationFacade authenticationFacade;
    private final AmenipointRepository amenipointRepository;
    private final MenuRepository menuRepository;
    private final ParlorRepository parlorRepository;
    private final RoleRepository roleRepository;
    private final StrfRepository strfRepository;
    private final BusinessNumRepository businessNumRepository;
    private final LocationDetailRepository locationDetailRepository;
    private final MyFileUtils myFileUtils;
    private final StrfPicRepository strfPicRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RestDateRepository restDateRepository;
    private final AmenityRepository amenityRepository;
//    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CouponRepository couponRepository;
    private final ReceiveCouponRepository receiveCouponRepository;
    private final NoticeService noticeService;

    public ResponseWrapper<StrfSelRes> busiMemberDetail(Long strfId) {
        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof JwtUser) {
            userId = authenticationFacade.getSignedUserId();
        }
        if (strfId == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        StrfSelRes res = strfMapper.getMemberDetail(userId, strfId);
        if (res == null) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }
        if (res.getRatingAvg() != null) {
            double roundedRating = Math.round(res.getRatingAvg() * 10) / 10.0;
            res.setRatingAvg(roundedRating);
        }
        if (userId > 0) {
            strfMapper.strfUpsert(userId, strfId);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }
    public ResponseWrapper<StrfSelRes> getMemberDetail(Long strfId) {
        Long userId = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof JwtUser) {
            userId = authenticationFacade.getSignedUserId();
        }
        if (strfId == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        StrfSelRes res = strfMapper.getMemberDetail(userId, strfId);
        if (res == null) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }
        if (res.getRatingAvg() != null) {
            double roundedRating = Math.round(res.getRatingAvg() * 10) / 10.0;
            res.setRatingAvg(roundedRating);
        }
        if (userId > 0) {
            strfMapper.strfUpsert(userId, strfId);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    public ResponseWrapper<List<StrfAmenity>> getStrfAmenity(Long strfId , String category) {
        String categoryValue = null;
        if (category != null && Category.getKeyByName(category) != null) {
            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
        }
        List<StrfAmenity> amenities = strfMapper.strfAmenity(strfId, categoryValue);
        if (amenities == null || amenities.isEmpty()){
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), amenities);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), amenities);
    }

    public ResponseWrapper<List<StrfMenu>> getStrfMenu(Long strfId) {
        List<StrfMenu> menus = strfMapper.strfMenu(strfId);

        if (menus == null || menus.isEmpty()) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), menus);
        }

        boolean hasValidMenuId = menus.stream().anyMatch(menu -> menu.getMenuId() != null);
        if (!hasValidMenuId) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), menus);
    }

    public ResponseWrapper<List<StrfParlorDto>> getStrfParlor(Long strfId, String category) {
        String categoryValue = null;
        if (category != null && Category.getKeyByName(category) != null) {
            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
        }
        List<StrfParlorDto> parlor = strfMapper.strfParlor(strfId , categoryValue);
        if (parlor == null || parlor.isEmpty()){
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), parlor);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), parlor);
    }

    @Transactional
    public ResponseWrapper<StrfInfoInsRes> strfInfoIns(List<MultipartFile> strfPic, StrfInsReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));

        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());

        if (businessNum == null) {
            businessNum = new BusinessNum();
            businessNum.setBusiNum(p.getBusiNum());
            businessNumRepository.save(businessNum);
        }

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }

        // Redis 를 이용해 location_detail_id 호출


        Long locationDetailId = locationDetailRepository.findByTitle(p.getLocationTitle())
                .map(LocationDetail::getLocationDetailId)
                .orElseThrow(() -> new RuntimeException("Location title not found in DB"));

        LocationDetail locationDetail = locationDetailRepository.findById(locationDetailId)
                .orElseThrow(() -> new RuntimeException("LocationDetail not found for ID: " + locationDetailId));

        Category categoryValue = null;
        if (p.getCategory() != null && Category.getKeyByName(p.getCategory()) != null) {
            categoryValue = Category.getKeyByName(p.getCategory());
        }

        StayTourRestaurFest strf = StayTourRestaurFest.builder()
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
        try {
            strfRepository.save(strf);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null);
        }

        if (p.getRestdates() != null && !p.getRestdates().isEmpty()) {
            StrfRestDate restDateHandler = new StrfRestDate();
            restDateHandler.addRestDays(p.getRestdates());  // "sun", "wed", "fri" 등 받아서 숫자로 변환

            List<Integer> restDays = restDateHandler.getRestDays();
            for (Integer day : restDays) {
                RestDateId id = new RestDateId();
                id.setDayWeek(day);
                id.setStrfId(strf.getStrfId());

                RestDate restDate = RestDate.builder()
                        .id(id)
                        .strfId(strf)
                        .build();
                restDateRepository.save(restDate);
            }
        }
        List<StrfPic> strfPics = new ArrayList<>();
        List<String> picNameList = new ArrayList<>();
        String middlePathStrf = String.format("strf/%d", strf.getStrfId());
        myFileUtils.makeFolders(middlePathStrf);

        for (MultipartFile pic : strfPic) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
//            String getExt = myFileUtils.getExt(savedPicName);

            String filePath = String.format("%s/%s", middlePathStrf, savedPicName);
            try {
                StrfPic newPic = StrfPic.builder()
                        .strfId(strf)
                        .picName(savedPicName.replaceAll("\\.[^.]+$", ".webp"))
                        .build();

                strfPics.add(newPic);

                myFileUtils.convertAndSaveToWebp(pic , filePath.replaceAll("\\.[^.]+$", ".webp"));
            } catch (IOException e) {
                e.printStackTrace();
                myFileUtils.deleteFolder(middlePathStrf, true);
                throw new RuntimeException(e);
            }
        }
        strfPicRepository.saveAll(strfPics);
        StrfInfoInsRes res = StrfInfoInsRes.builder().strfId(strf.getStrfId()).category(categoryValue).title(strf.getTitle()).build();

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    @Transactional
    public ResponseWrapper<Long> strfMenuIns(List<MultipartFile> menuPic, StrfMenuInsReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));

        // 비즈니스 번호 조회
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());

        // 비즈니스 번호가 없으면 에러 처리
        if (businessNum == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0L); // 404 Not Found
        }

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0L);
        }

        StayTourRestaurFest strf = strfRepository.findById(p.getStrfId()).orElseThrow(() -> new RuntimeException("StayTourRestaurFest not found"));

        List<Menu> menus = new ArrayList<>();

        String middlePathMenu = String.format("strf/%d/menu", strf.getStrfId());
        myFileUtils.makeFolders(middlePathMenu);

        for (MultipartFile pic : menuPic) {

            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePathMenu, savedPicName);

            try {
                for (MenuIns strfMenu : p.getMenus()) {
                    Menu newMenu = Menu.builder()
                            .stayTourRestaurFest(strf)
                            .title(strfMenu.getMenuTitle())
                            .price(strfMenu.getMenuPrice())
                            .menuPic(savedPicName.replaceAll("\\.[^.]+$", ".webp"))
                            .build();
                    menus.add(newMenu);
                }

                myFileUtils.convertAndSaveToWebp(pic, filePath.replaceAll("\\.[^.]+$", ".webp"));
            } catch (IOException e) {
                e.printStackTrace();
                myFileUtils.deleteFolder(middlePathMenu, true);
                throw new RuntimeException("메뉴 이미지 저장 실패", e);
            }
        }
        menuRepository.saveAll(menus);
        menuRepository.flush();
        // 첫 번째 메뉴의 ID를 반환
        if (!menus.isEmpty()) {
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), menus.get(0).getMenuId());
        } else {
            throw new RuntimeException("No menus were saved.");
        }
    }

    @Transactional
    public ResponseWrapper<Integer> strfStayIns(StrfStayInsReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        StayTourRestaurFest strf = strfRepository.findByStrfId(businessNum.getBusiNum())
                .orElseThrow( () -> new RuntimeException("businessNum not found"));

        Category categoryValue = null;
        if (p.getCategory() != null && Category.getKeyByName(p.getCategory()) != null) {
            categoryValue = Category.getKeyByName(p.getCategory());
        }

        if (categoryValue == Category.STAY) {
            List<Parlor> parlors = new ArrayList<>();
            List<Room> rooms = new ArrayList<>();
            List<Amenipoint> amenipointList = new ArrayList<>();

            if (p.getAmeniPoints() != null) {
                for (Long amenityId : p.getAmeniPoints()) {
                    Amenity amenity = amenityRepository.findById(amenityId)
                            .orElseThrow(() -> new RuntimeException("Amenity ID " + amenityId + "를 찾을 수 없습니다."));

                    Amenipoint amenipoint = Amenipoint.builder()
                            .id(new AmenipointId(amenity.getAmenityId(), strf.getStrfId()))
                            .amenity(amenity)
                            .stayTourRestaurFest(strf)
                            .build();
                    amenipointList.add(amenipoint);
                }
            }

            Menu menu = null;
            if (p.getMenuId() != null) {
                menu = menuRepository.findById(p.getMenuId())
                        .orElse(null); // menuId가 없으면 예외 발생 없이 null 처리
            }

            if (p.getParlors() != null){
                for (StrfParlor strfParlor : p.getParlors()) {
                    if (strfParlor.getRecomCapacity() > strfParlor.getMaxCapacity()) {
                        throw new RuntimeException("권장 인원은 최대 인원보다 클 수 없습니다.");
                    }

                    Parlor newParlor = Parlor.builder()
                            .menu(menu)
                            .maxCapacity(strfParlor.getMaxCapacity())
                            .recomCapacity(strfParlor.getRecomCapacity())
                            .surcharge(strfParlor.getSurcharge())
                            .build();
                    parlors.add(newParlor);
                }
            }
            if (p.getRooms() > 0){
                for (int i = 0; i < p.getRooms(); i++) {
                    Room newRoom = Room.builder()
                            .menu(menu)
                            .roomNum(i + 1)
                            .build();
                    rooms.add(newRoom);
                }
            }

            parlorRepository.saveAll(parlors);
            roomRepository.saveAll(rooms);
            amenipointRepository.saveAll(amenipointList);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> deleteStrf(Long strfId) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));

        String businessNum = businessNumRepository.findBusinessNumByUserId(user.getUserId());

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());

        StayTourRestaurFest strf = strfRepository.findById(strfId).orElseThrow(() -> new RuntimeException("STRF ID not found"));

        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        log.info("==========businessNum : {}" , businessNum);
        log.info("==========strf.getBusiNum : {}" , strf.getBusiNum());

        if (businessNum != null && !businessNum.equals(strf.getBusiNum().getBusiNum())) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        if (strf.getCategory() == Category.STAY){
            amenipointRepository.deleteByStayTourRestaurFest(strf);
            parlorRepository.deleteAllByMenuStayTourRestaurFest(strf);
            roomRepository.deleteAllByMenuStayTourRestaurFest(strf);
        }
        if (strf.getCategory() == Category.RESTAUR || strf.getCategory() == Category.STAY){
            menuRepository.deleteByStayTourRestaurFest(strf);

        }

        strfPicRepository.deleteAllByStrfId(strf);
        strfRepository.delete(strf);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    private void updateStrfPics(List<MultipartFile> strfPic, StayTourRestaurFest strf) {
        if (strfPic != null && !strfPic.isEmpty()) {
            strfPicRepository.deleteAllByStrfId(strf);
            saveStrfPics(strfPic, strf);
        }
    }

    private String saveMenuPic(MultipartFile file) {
        // 이미지 저장 로직 (예: S3, 로컬 스토리지)
        return file != null ? file.getOriginalFilename() : null;
    }

    private void saveStrfPics(List<MultipartFile> files, StayTourRestaurFest strf) {
        files.forEach(file -> {
            StrfPic pic = StrfPic.builder()
                    .strfId(strf)  // strf 객체를 설정
                    .picName(file.getOriginalFilename())
                    .build();
            strfPicRepository.save(pic);
        });
    }

    public ResponseWrapper<Integer> reviewCount(long strfId){

        Integer count = strfMapper.reviewCount(strfId);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(),count);
    }

    public List<StrfCouponGetRes> couponList(long strfId){
        List<StrfCouponGetRes> couponGetList = strfMapper.couponList(strfId);
        return couponGetList;
    }

    public int couponReceive (long couponId){
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        Coupon coupon=couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("coupon id not found"));
        receiveCouponRepository.save(ReceiveCoupon.builder().user(user).coupon(coupon).build());
        String title=coupon.getTitle()+"을 받았습니다! ";
        StringBuilder contents = new StringBuilder().append(coupon.getDiscountPer()).append("% 할인되는 ")
                .append(title).append(coupon.getExpiredAt()).append("까지 ")
                .append(coupon.getStrf().getTitle()!=null?coupon.getStrf().getTitle():
                        strfRepository.findTitleByStrfId(coupon.getStrf().getStrfId()))
                .append("에서 사용하여 더 저렴한 혜택을 놓치지 마세요!");
        noticeService.postNotice(NoticeCategory.COUPON,title,contents.toString(),user,couponId);

        return 1;
    }

    @Transactional
    public ResponseWrapper<List<StrfCheckRes>> stayBookingExists(Long strfId, LocalDate checkIn, LocalDate checkOut) {
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("strf id not found"));

        List<Menu> menus = menuRepository.findByStayTourRestaurFest(strf); // 해당 상품의 모든 메뉴 조회
        // 각 메뉴별 예약 가능 여부 조회
        List<StrfCheckRes> checkResList = strfMapper.stayBookingExists(strfId, checkIn.atStartOfDay(), checkOut.atTime(23, 59));

//        for (Menu menu : menus) {
//            List<Integer> restDays = strfMapper.getRestDaysByStrfId(strfId);
//            if (restDays != null && !restDays.isEmpty()) {
//                for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
//                    int dayOfWeek = date.getDayOfWeek().getValue() % 7;
//                    if (restDays.contains(dayOfWeek)) {
//                        isBooked = true;
//                        break;
//                    }
//                }
//            }
//            checkResList.add(new StrfCheckRes(menu.getMenuId(), !isBooked)); // 예약 가능 여부 반환
//        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), checkResList);
    }

//    @Transactional
//    public ResponseWrapper<List<StrfCheckRes>> stayBookingExists(Long strfId, LocalDate checkIn, LocalDate checkOut) {
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("strf id not found"));
//
//        List<Menu> menus = menuRepository.findByStayTourRestaurFest(strf); // 해당 상품의 모든 메뉴 조회
//        List<StrfCheckRes> checkResList = new ArrayList<>();
//
//        for (Menu menu : menus) {
//            boolean isBooked = strfMapper.stayBookingExists(menu.getMenuId(), checkIn.atStartOfDay(), checkOut.atTime(23, 59));
//
//            List<Integer> restDays = strfMapper.getRestDaysByStrfId(strfId);
//            if (restDays != null && !restDays.isEmpty()) {
//                for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
//                    int dayOfWeek = date.getDayOfWeek().getValue() % 7;
//                    if (restDays.contains(dayOfWeek)) {
//                        isBooked = true;
//                        break;
//                    }
//                }
//            }
//            checkResList.add(new StrfCheckRes(menu.getMenuId(), !isBooked)); // 예약 가능 여부 반환
//        }
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), checkResList);
//    }


//    @Transactional
//    public ResponseWrapper<Integer> updateStrf(Long strfId, StrfUpdInfo p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        // 기존 데이터가 변경되었는지 확인하고 업데이트
//        boolean updated = updStrfBasicInfo(strf, p);
//
//        if (updated) {
//            strfRepository.save(strf);  // 변경사항 저장
//        }
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), updated ? 1 : 0);
//    }

    @Transactional
    public ResponseWrapper<Integer> updState(Long strfId, int state , String busiNum) {
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strf = getStayTourRestaurFest(strfId);
        if (strf.getState().equals(state)) {
            throw new RuntimeException("기존의 영업 상태와 같습니다.");
        }

        strf.setState(state);
        strfRepository.save(strf);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), state);
    }

    @Transactional
    public ResponseWrapper<Integer> updTime(StrfTime p) {
        User user = validateUserAndBusiness(p.getBusiNum());
        StayTourRestaurFest strf = getStayTourRestaurFest(p.getStrfId());

        strf.setOpenCheckIn(p.getOpenCheckIn());
        strf.setCloseCheckOut(p.getCloseCheckOut());
        strfRepository.save(strf);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updTell(Long strfId, String tell, String busiNum) {
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strf = getStayTourRestaurFest(strfId);
        if (tell == null || tell.trim().isEmpty()) {
            throw new RuntimeException("전화번호는 필수입니다.");
        }
        if (strfRepository.existsByTell(tell)) {
            throw new RuntimeException("전화번호가 이미 존재합니다.");
        }
        if (strf.getTell().equals(tell)) {
            throw new RuntimeException("기존의 전화번호와 같습니다. ");
        }
        strf.setTell(tell);
        strfRepository.save(strf);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updRest(Long strfId, List<String> restDates, String busiNum) {
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strf = getStayTourRestaurFest(strfId);
        restDateRepository.findByStrfId(strf.getStrfId()).orElseThrow( () -> new RuntimeException("id not found"));
        if (restDates != null && !restDates.isEmpty()) {
            StrfRestDate restDateHandler = new StrfRestDate();
            restDateHandler.addRestDays(restDates);  // "sun", "wed", "fri" → 숫자로 변환
            List<Integer> restDays = restDateHandler.getRestDays();
            for (Integer day : restDays) {
                RestDateId id = new RestDateId();
                id.setDayWeek(day);
                id.setStrfId(strf.getStrfId());

                RestDate restDate = RestDate.builder()
                        .id(id)
                        .strfId(strf)
                        .build();
                restDateRepository.save(restDate);
            }
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updStrfMenu(List<MultipartFile> menuPic, StrfUpdMenu req) {
        User user = validateUserAndBusiness(req.getBusiNum());
        StayTourRestaurFest strf = getStayTourRestaurFest(req.getStrfId());

        List<Menu> updatedMenus = new ArrayList<>();

        for (int i = 0; i < req.getMenus().size(); i++) {
            MenuIns menuIns = req.getMenus().get(i);
            long menuId = req.getMenuId();
            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("Menu ID " + menuId + " not found"));

            String savedPic = (menuPic != null && menuPic.size() > i) ? saveMenuPic(menuPic.get(i)) : null;

            menu.setTitle(menuIns.getMenuTitle());
            menu.setPrice(menuIns.getMenuPrice());
            menu.setMenuPic(savedPic);
        }
        menuRepository.saveAll(updatedMenus);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), updatedMenus.size());
    }

    @Transactional
    public ResponseWrapper<Integer> updDetail(Long strfId, String detail, String busiNum) {
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strf = getStayTourRestaurFest(strfId);
        if (strfRepository.existsByDetail(detail)) {
            throw new RuntimeException("기존의 상품 소개와 같습니다.");
        }
        if (strf.getDetail().equals(detail)) {
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), 0);
        }
        strf.setDetail(detail);
        strfRepository.save(strf);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updFestTime(StrfFestTime p) {
        User user = validateUserAndBusiness(p.getBusiNum());
        StayTourRestaurFest strf = getStayTourRestaurFest(p.getStrfId());

        strf.setStartAt(p.getStartAt());
        strf.setEndAt(p.getEndAt());
        strfRepository.save(strf);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updTitle(Long strfId, String title, String busiNum) {
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strf = getStayTourRestaurFest(strfId);

        if (strfRepository.existsByTitle(title)) {
            throw new RuntimeException("상품명이 이미 존재합니다.");
        }
        if (strf.getTitle().equals(title)) {
            throw new RuntimeException("기존의 상품명과 같습니다.");
        }
        strf.setTitle(title);
        strfRepository.save(strf);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updAddress(StrfUpdAddress p) {
        User user = validateUserAndBusiness(p.getBusiNum());
        StayTourRestaurFest strf = getStayTourRestaurFest(p.getStrfId());
        if (strf.getBusiNum() == null || p.getBusiNum() == null) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

// ✅ BusinessNum 객체에서 실제 번호를 가져와 비교
        String strfBusiNum = strf.getBusiNum().getBusiNum();  // BusinessNum 객체에서 번호 가져오기

        if (!p.getBusiNum().equals(strfBusiNum)) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        Long locationDetailId = locationDetailRepository.findByTitle(p.getLocationDetailTitle())
                .map(LocationDetail::getLocationDetailId)
                .orElseThrow(() -> new RuntimeException("Location title not found in DB"));

        LocationDetail locationDetail = locationDetailRepository.findById(locationDetailId)
                .orElseThrow(() -> new RuntimeException("LocationDetail not found for ID: " + locationDetailId));

        strf.setAddress(p.getAddress());
        strf.setLat(p.getLat());
        strf.setLng(p.getLng());
        strf.setPost(p.getPost());
        strf.setLocationDetail(locationDetail);
        strfRepository.save(strf);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updStrfPic(Long strfId, List<MultipartFile> strfPic, String busiNum) {
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strf = getStayTourRestaurFest(strfId);

        String middlePathStrf = String.format("strf/%d", strf.getStrfId());
        myFileUtils.makeFolders(middlePathStrf);

        if (strfPic == null || strfPic.isEmpty()) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
        }

        List<StrfPic> strfPics = new ArrayList<>();

        for (MultipartFile pic : strfPic) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePathStrf, savedPicName);
            String webpFilePath = filePath.replaceAll("\\.[^.]+$", ".webp");
            try {
                StrfPic newPic = StrfPic.builder()
                        .strfId(strf)
                        .picName(savedPicName.replaceAll("\\.[^.]+$", ".webp"))
                        .build();
                strfPics.add(newPic);

                myFileUtils.convertAndSaveToWebp(pic, webpFilePath);
            } catch (IOException e) {
                e.printStackTrace();
                myFileUtils.deleteFolder(webpFilePath , true);
                throw new RuntimeException("파일 변환 오류: " + savedPicName, e);
            }
        }

        strfPicRepository.saveAll(strfPics);

//        updateStrfPics(strfPic, strf);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), strfPics.size());
    }

    @Transactional
    public ResponseWrapper<Integer> updateStay(StrfStayUpdReq req) {
        User user = validateUserAndBusiness(req.getBusiNum());
        StayTourRestaurFest strf = getStayTourRestaurFest(req.getStrfId());

        Category categoryValue = (req.getCategory() != null) ? Category.getKeyByName(req.getCategory()) : null;

        if (categoryValue == Category.STAY) {
            Menu menu = menuRepository.findById(req.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu not found"));

            parlorRepository.deleteByMenuId(req.getMenuId());
            roomRepository.deleteByMenuId(req.getMenuId());

            List<Parlor> parlors = new ArrayList<>();

            if (req.getParlors() != null){
                for (StrfParlor strfParlor : req.getParlors()) {
                    if (strfParlor.getRecomCapacity() > strfParlor.getMaxCapacity()) {
                        throw new RuntimeException("권장 인원은 최대 인원보다 클 수 없습니다.");
                    }

                    Parlor newParlor = Parlor.builder()
                            .menu(menu)
                            .maxCapacity(strfParlor.getMaxCapacity())
                            .recomCapacity(strfParlor.getRecomCapacity())
                            .surcharge(strfParlor.getSurcharge())
                            .build();
                    parlors.add(newParlor);
                }
            }
            parlorRepository.saveAll(parlors);

            List<Room> rooms = new ArrayList<>();
            if (req.getRooms() > 0){
                for (int i = 0; i < req.getRooms(); i++) {
                    Room newRoom = Room.builder()
                            .menu(menu)
                            .roomNum(i + 1)
                            .build();
                    rooms.add(newRoom);
                }
            }
            roomRepository.saveAll(rooms);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updateAmenity(StrfJpaAmenity req) {
        User user = validateUserAndBusiness(req.getBusiNum());
        StayTourRestaurFest strf = getStayTourRestaurFest(req.getStrfId());

        Category categoryValue = (req.getCategory() != null) ? Category.getKeyByName(req.getCategory()) : null;
        List<Amenipoint> amenipoints = new ArrayList<>();
        if (categoryValue == Category.STAY) {
            amenipoints = req.getAmeniPoints().stream().map(amenityId -> {
                Amenity amenity = amenityRepository.findById(amenityId)
                        .orElseThrow(() -> new RuntimeException("Amenity ID " + amenityId + "를 찾을 수 없습니다."));

                return Amenipoint.builder()
                        .id(new AmenipointId(amenity.getAmenityId(), strf.getStrfId()))
                        .amenity(amenity)
                        .stayTourRestaurFest(strf)
                        .build();
            }).toList();

        }
        amenipointRepository.saveAll(amenipoints);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> deleteAmenity(Long strfId, String busiNum,List<Long> amenityIds){
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strf = getStayTourRestaurFest(strfId);

        if (amenityIds == null || amenityIds.isEmpty()) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), 0);
        }

        int delCnt = amenipointRepository.deleteByAmenityIdAndStrfId(amenityIds,strfId);

        if (delCnt == 0) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), delCnt);
    }

    @Transactional
    public ResponseWrapper<Integer> deleteMenu(long menuId, String busiNum){
        User user = validateUserAndBusiness(busiNum);

        if (menuId < 0) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), 0);
        }
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow( () -> new RuntimeException("menu id not found"));

        StayTourRestaurFest strf = menu.getStayTourRestaurFest();

        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);

        if (!strf.getBusiNum().equals(businessNum)){
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), 0);
        }

        int del = roomRepository.deleteByRoomId(menuId);
        int del2 = 0;
        int del3 = menuRepository.deleteByMenuId(menuId);

        if (del3 == 0) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
        }

        if ("STAY".equals(strf.getCategory().name())) {
            del2 = parlorRepository.deleteByMenuId(menuId);

            if (del2 == 0 && parlorRepository.existsByMenu_MenuId(menuId)) {
                return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
            }
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), del3);

    }

    @Transactional
    public ResponseWrapper<Integer> deleteParlor(long menuId, String busiNum){
        User user = validateUserAndBusiness(busiNum);

        if (menuId < 0) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), 0);
        }

        Parlor parlor = parlorRepository.findById(menuId)
                .orElseThrow( () -> new RuntimeException("room id not found"));

        StayTourRestaurFest strf = parlor.getMenu().getStayTourRestaurFest();
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);

        if (!strf.getBusiNum().equals(businessNum)){
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), 0);
        }
        int del = roomRepository.deleteByRoomId(menuId);

        int del2 = parlorRepository.deleteByMenuId(menuId);
        if (del2 == 0) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), del2);
    }

    @Transactional
    public ResponseWrapper<Integer> deleteStrfPic(String busiNum, String picName) {
        validateUserAndBusiness(busiNum);

        StrfPic pics = strfPicRepository.findByPicName(picName)
                .orElseThrow(() -> new RuntimeException("Picture not found"));

        StayTourRestaurFest strf = pics.getStrfId();

        if (!strf.getBusiNum().equals(businessNumRepository.findByBusinessNum(busiNum))) {
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), 0);
        }

        long picCount = strfPicRepository.countByStrfId(strf);
        if (picCount <= 1) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), 0);
        }

        int del = strfPicRepository.deleteByPicName(picName);
        strfPicRepository.flush();

        if (del == 0) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), del);
    }

    @Transactional
    public ResponseWrapper<Integer> deleteRest(Long strfId,  String busiNum){
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strfSel = getStayTourRestaurFest(strfId);

        int del = restDateRepository.deleteByStrfId(strfId);

        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);

        if (!strfSel.getBusiNum().equals(businessNum)){
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), 0);
        }

        if (del == 0){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), del);
    }

    @Transactional
    public ResponseWrapper<Integer> delAllAmenity (long strfId , String busiNum){
        User user = validateUserAndBusiness(busiNum);
        StayTourRestaurFest strfSel = getStayTourRestaurFest(strfId);

        int del = amenipointRepository.deleteByStrfId(strfId);

        if (del < 0){
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), del);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), del);
    }


    private User validateUserAndBusiness(String busiNum) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user id not found"));

        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());

        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi) {
            throw new RuntimeException("사업자가 아닙니다.");
        }

        return user;
    }

    private StayTourRestaurFest getStayTourRestaurFest(Long strfId) {
        return strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
    }


}

//@Transactional
//public ResponseWrapper<Integer> deleteRoom(long roomId, String busiNum){
//    User user = validateUserAndBusiness(busiNum);
//    if (roomId < 0) {
//        return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), 0);
//    }
//    Room room = roomRepository.findById(roomId)
//            .orElseThrow( () -> new RuntimeException("room id not found"));
//
//    StayTourRestaurFest strf = room.getMenu().getStayTourRestaurFest();
//    BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//
//    if (!strf.getBusiNum().equals(businessNum)){
//        return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), 0);
//    }
//
//    int del = roomRepository.deleteByRoomId(roomId);
//    if (del == 0) {
//        return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0);
//    }
//    return new ResponseWrapper<>(ResponseCode.OK.getCode(), del);
//}