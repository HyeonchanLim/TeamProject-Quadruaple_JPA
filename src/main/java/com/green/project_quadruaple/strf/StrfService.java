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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CouponRepository couponRepository;
    private final ReceiveCouponRepository receiveCouponRepository;
    private final NoticeService noticeService;

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
    public ResponseWrapper<Long> strfInfoIns(List<MultipartFile> strfPic, StrfInsReq p) {
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
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0L);
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
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0L);
        }

        if (p.getRestdates() != null && !p.getRestdates().isEmpty()) {
            StrfRestDate restDateHandler = new StrfRestDate();
            restDateHandler.addRestDays(p.getRestdates());  // "sun", "wed", "fri" 등 받아서 숫자로 변환

            List<Integer> restDays = restDateHandler.getRestDays();
            for (Integer day : restDays) {
                RestDateId id = new RestDateId();
                id.setDayWeek(day);
                id.setStrfId(strf.getStrfId());

                // RestDate 엔티티 저장
                RestDate restDate = RestDate.builder()
                        .id(id)
                        .strfId(strf)
//                        .dayWeek(day)  // "sun" -> 0, "wed" -> 3 등
                        .build();
                restDateRepository.save(restDate);  // 휴무일 저장
            }
        }

        String middlePathStrf = String.format("strf/%d", strf.getStrfId());
        myFileUtils.makeFolders(middlePathStrf);

        for (MultipartFile pic : strfPic) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePathStrf, savedPicName);
            try {
                StrfPic strfPics = new StrfPic();
                strfPics.setStrfId(strf);
                strfPics.setPicName(savedPicName);
                strfPicRepository.save(strfPics);
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
                myFileUtils.deleteFolder(middlePathStrf, true);
                throw new RuntimeException(e);
            }
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), strf.getStrfId());
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
            // ✅ 서비스 로직에서 만든 파일명 가져오기
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePathMenu, savedPicName);  // 🔥 원본 확장자 포함된 경로

            try {
                for (MenuIns strfMenu : p.getMenus()) {
                    Menu newMenu = Menu.builder()
                            .stayTourRestaurFest(strf)
                            .title(strfMenu.getMenuTitle())
                            .price(strfMenu.getMenuPrice())
                            .menuPic(savedPicName.replace(".png", ".webp"))  // ✅ DB 저장 시 .webp 확장자로 저장
                            .build();
                    menus.add(newMenu);
                }
                // ✅ WebP 변환 시 기존 파일 확장자 제거 후 .webp로 변경하여 저장
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

            for (StrfParlor strfParlor : p.getParlors()) {
                Menu menu = menuRepository.findById(p.getMenuId())
                        .orElseThrow(() -> new RuntimeException("Menu not found"));

                Parlor newParlor = Parlor.builder()
                        .menu(menu)
                        .maxCapacity(strfParlor.getMaxCapacity())
                        .recomCapacity(strfParlor.getRecomCapacity())
                        .surcharge(strfParlor.getSurcharge())
                        .build();
                parlors.add(newParlor);
            }

            for (Long roomId : p.getRooms()) {
                Menu menu = menuRepository.findById(p.getMenuId())
                        .orElseThrow(() -> new RuntimeException("Menu not found"));

                Room newRoom = Room.builder()
                        .menu(menu)
                        .roomId(roomId)
                        .roomNum(1)
                        .build();
                rooms.add(newRoom);
            }

            parlorRepository.saveAll(parlors);
            roomRepository.saveAll(rooms);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

//    @Transactional
//    public ResponseWrapper<Integer> updateStrf(Long strfId, StrfUpdInfo p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        updateStrfBasicInfo(strf, p);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updState(Long strfId, int state , String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//
//        updateStrfState(strf, state);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updDetail(Long strfId, String detail,String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        updateStrfDetail(strf, detail);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updFestTime(Long strfId, StrfFestTime p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());
//        updateStrfFestTime(strf, p);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updTime(StrfTime p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(p.getStrfId())
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());
////        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
////            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
////        }
//
//        updateStrfTime(strf, p);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updTell(Long strfId, String tell,String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        if (strfRepository.existsByTell(tell)) {
//            throw new RuntimeException("Title already exists");
//        }
//        updateStrfTell(strf, tell);
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updTitle(Long strfId, String title,String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//
//        if (strfRepository.existsByTitle(title)) {
//            throw new RuntimeException("Title already exists");
//        }
//        updateStrfTitle(strf, title);
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updAddress(Long strfId, StrfUpdAddress p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());
//        if (!strf.getBusiNum().toString().equals(p.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
//
//        updateStrfAddressInfo(strf, p);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updStrfPic(Long strfId, List<MultipartFile> strfPic,String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        updateStrfPics(strfPic, strf);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updRest(Long strfId, List<String> restDates , String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        updateRestDays(strf,restDates);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updStrfMenu(List<MultipartFile> menuPic, StrfUpdMenu req) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());
//        Menu menu = menuRepository.findById(req.getMenuId()).orElseThrow( () -> new RuntimeException("menuId not found"));
//
//        List<Menu> savedMenus = saveMenusWithPics(menuPic, strf, req);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updateStay(StrfStayUpdReq req) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());
//
//        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
//        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
//        if (!isBusi) {
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
//
//        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//
//        Category categoryValue = null;
//        if (req.getCategory() != null && Category.getKeyByName(req.getCategory()) != null) {
//            categoryValue = Category.getKeyByName(req.getCategory());
//        }
//        if (categoryValue == Category.STAY) {
//            List<Parlor> parlors = new ArrayList<>();
//            List<Room> rooms = new ArrayList<>();
//            for (StrfParlor strfParlor : req.getParlors()) {
//                Menu menu = menuRepository.findById(req.getMenuId())
//                        .orElseThrow(() -> new RuntimeException("Menu not found"));
//
//                Parlor newParlor = Parlor.builder()
//                        .menu(menu)
//                        .maxCapacity(strfParlor.getMaxCapacity())
//                        .recomCapacity(strfParlor.getRecomCapacity())
//                        .surcharge(strfParlor.getSurcharge())
//                        .build();
//                parlors.add(newParlor);
//            }
//
//            for (Long roomId : req.getRooms()) {
//                Menu menu = menuRepository.findById(req.getMenuId())
//                        .orElseThrow(() -> new RuntimeException("Menu not found"));
//
//                Room newRoom = Room.builder()
//                        .menu(menu)
//                        .roomId(roomId)
//                        .roomNum(1)
//                        .build();
//                rooms.add(newRoom);
//            }
//            parlorRepository.saveAll(parlors);
//            roomRepository.saveAll(rooms);
//        }
//            Menu menu = menuRepository.findById(req.getMenuId())
//                .orElseThrow( () -> new RuntimeException("menu id not found"));
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updateAmenity(StrfJpaAmenity req) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());
//        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
//
//        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
//        if (!isBusi) {
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
//
//        if (req.getAmeniPoints() == null) {
//            throw new RuntimeException("Amenity가 null 상태입니다!");
//        }
//
//        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        Category categoryValue = null;
//        if (req.getCategory() != null && Category.getKeyByName(req.getCategory()) != null) {
//            categoryValue = Category.getKeyByName(req.getCategory());
//        }
//
//        if (categoryValue == Category.STAY) {
//            List<Amenipoint> amenipoints = new ArrayList<>();
//
//            for (Long amenityId : req.getAmeniPoints()) {
//                Amenity amenity = amenityRepository.findById(amenityId)
//                        .orElseThrow(() -> new RuntimeException("Amenity ID " + amenityId + "를 찾을 수 없습니다."));
//
//                Amenipoint amenipoint = Amenipoint.builder()
//                        .id(new AmenipointId(amenity.getAmenityId(), strf.getStrfId()))
//                        .amenity(amenity)
//                        .stayTourRestaurFest(strf)
//                        .build();
//                amenipoints.add(amenipoint);
//            }
//
//            amenipointRepository.saveAll(amenipoints);
//        }
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }


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

    private void updateStrfState(StayTourRestaurFest strf, int p) {
        strf.setState(p);
        strfRepository.save(strf);
    }

    private void updateStrfDetail(StayTourRestaurFest strf, String p) {
        strf.setDetail(p);
        strfRepository.save(strf);
    }

    private void updateStrfFestTime(StayTourRestaurFest strf, StrfFestTime p) {
        strf.setStartAt(p.getStartAt());
        strf.setEndAt(p.getEndAt());
        strfRepository.save(strf);
    }

    private void updateStrfTime(StayTourRestaurFest strf, StrfTime p) {
        strf.setOpenCheckIn(p.getOpenCheckIn());
        strf.setCloseCheckOut(p.getCloseCheckOut());
        strfRepository.save(strf);
    }

    private void updateStrfTell(StayTourRestaurFest strf, String tell) {

        strf.setTell(tell);
        strfRepository.save(strf);
    }

    private void updateStrfTitle(StayTourRestaurFest strf, String title) {

        strf.setTitle(title);
        strfRepository.save(strf);
    }

    // strf 주소 업데이트
    private void updateStrfAddressInfo(StayTourRestaurFest strf, StrfUpdAddress p) {
        LocationDetail locationDetail = locationDetailRepository.findById(p.getLocationDetailId())
                .orElseThrow(() -> new RuntimeException("LocationDetail not found"));
        BusinessNum busiNum = businessNumRepository.findByBusiNum(p.getBusiNum());

        strf.setLocationDetail(locationDetail);
        strf.setLat(p.getLat());
        strf.setLng(p.getLng());
        strf.setAddress(p.getAddress());
        strf.setPost(p.getPost());
        strfRepository.save(strf);
    }
    // STRF 기본 정보 업데이트
    private void updateStrfBasicInfo(StayTourRestaurFest strf, StrfUpdInfo p) {
        LocationDetail locationDetail = locationDetailRepository.findById(p.getLocationDetailId())
                .orElseThrow(() -> new RuntimeException("LocationDetail not found"));
        BusinessNum busiNum = businessNumRepository.findByBusiNum(p.getBusiNum());

        strf.setBusiNum(busiNum);
        strf.setLocationDetail(locationDetail);
        strf.setTitle(p.getTitle());
        strf.setLat(p.getLat());
        strf.setLng(p.getLng());
        strf.setAddress(p.getAddress());
        strf.setPost(p.getPost());
        strf.setTell(p.getTell());
        strf.setStartAt(p.getStartAt());
        strf.setEndAt(p.getEndAt());
        strf.setOpenCheckIn(p.getOpenCheckIn());
        strf.setCloseCheckOut(p.getCloseCheckOut());
        strf.setDetail(p.getDetail());
        strf.setState(p.getState());
        strfRepository.save(strf);
    }

    // 이미지 업데이트
    private void updateStrfPics(List<MultipartFile> strfPic, StayTourRestaurFest strf) {
        if (strfPic != null && !strfPic.isEmpty()) {
            strfPicRepository.deleteAllByStrfId(strf);
            saveStrfPics(strfPic, strf);
        }
    }

    private void updateRestDays(StayTourRestaurFest strf, List<String> restdates) {
        if (restdates != null && !restdates.isEmpty()) {
            StrfRestDate restDateHandler = new StrfRestDate();
            restDateHandler.addRestDays(restdates);
            List<Integer> restDays = restDateHandler.getRestDays();

//            List<RestDate> restDates = restdates.stream()
//                    .map(day -> {
//                        RestDateId id = new RestDateId(Integer.parseInt(day), strf.getStrfId());  // 복합 키 생성
//                        return RestDate.builder()
//                                .id(id)               // 복합 키 설정
//                                .strfId(strf)        // 연관된 엔티티 설정
//                                .build();
//                    })
//                    .collect(toList());
            for (Integer day : restDays) {
                RestDateId id = new RestDateId();
                id.setDayWeek(day);
                id.setStrfId(strf.getStrfId());

                // RestDate 엔티티 저장
                RestDate restDate = RestDate.builder()
                        .id(id)
                        .strfId(strf)
//                        .dayWeek(day)  // "sun" -> 0, "wed" -> 3 등
                        .build();
                restDateRepository.save(restDate);  // 휴무일 저장

//            restDateRepository.saveAll(restDates);  // RestDate 객체들을 DB에 저장
            }
        }
    }
    // 메뉴 저장 (사진 포함)
    private List<Menu> saveMenusWithPics(List<MultipartFile> menuPic, StayTourRestaurFest strf, StrfUpdMenu menuReq) {
        List<Menu> menus = new ArrayList<>();

        for (int i = 0; i < menuReq.getMenus().size(); i++) {
            MenuIns menuIns = menuReq.getMenus().get(i);
            Menu menu = Menu.builder()
                    .stayTourRestaurFest(strf)
                    .title(menuIns.getMenuTitle())
                    .price(menuIns.getMenuPrice())
                    .menuPic(saveMenuPic(menuPic.get(i)))
                    .build();
            menus.add(menu);
        }

        return menuRepository.saveAll(menus);
    }

    private boolean isRestaurantOrStay(StayTourRestaurFest strf) {
        return strf.getCategory().equals("RESTAUR") || strf.getCategory().equals("STAY");
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

    public boolean stayBookingExists(Long strfId, Long menuId, LocalDate checkIn, LocalDate checkOut) {
        // ✅ 1. 예약 존재 여부 확인
        boolean isBooked = strfMapper.stayBookingExists(menuId, checkIn.atStartOfDay(), checkOut.atTime(23, 59));
        if (isBooked) {
            return false;  // 이미 예약된 경우
        }

        // ✅ 2. 휴무일 확인
        List<Integer> restDays = strfMapper.getRestDaysByStrfId(strfId);
        if (restDays != null && !restDays.isEmpty()) {
            for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
                int dayOfWeek = date.getDayOfWeek().getValue() % 7; // Java의 요일(1=월~7=일) → DB 요일(0=일~6=토) 변환
                if (restDays.contains(dayOfWeek)) {
                    return false; // 해당 날짜가 휴무일이면 예약 불가능
                }
            }
        }

        return true; // 예약 가능
    }





//
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
//
//    @Transactional
//    public ResponseWrapper<Integer> updState(Long strfId, int state , String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//
//        if (strf.getState() == state) {
//            return new ResponseWrapper<>(ResponseCode.OK.getCode(), 0); // 변경 사항 없음
//        }
//
//        strf.setState(state);
//        strfRepository.save(strf);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updTime(StrfTime p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(p.getStrfId())
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());
//
//        boolean updated = updStrfTime(strf, p);
//
//        if (updated) {
//            strfRepository.save(strf);
//        }
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), updated ? 1 : 0);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updTell(Long strfId, String tell, String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//
//        if (strfRepository.existsByTell(tell)) {
//            throw new RuntimeException("전화번호가 이미 존재합니다.");
//        }
//
//        if (strf.getTell().equals(tell)) {
//            return new ResponseWrapper<>(ResponseCode.OK.getCode(), 0);
//        }
//
//        strf.setTell(tell);
//        strfRepository.save(strf);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
////    @Transactional
////    public ResponseWrapper<Integer> updRest(Long strfId, List<String> restDates, String busiNum) {
////        long userId = authenticationFacade.getSignedUserId();
////        User user = userRepository.findById(userId)
////                .orElseThrow(() -> new RuntimeException("user id not found"));
////
////        StayTourRestaurFest strf = strfRepository.findById(strfId)
////                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
////
////        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
////
////        // 기존 데이터 삭제 후 새 데이터 삽입
////        restDateRepository.deleteByStrfId(strfId);
////        updateRestDays(strf, restDates);
////
////        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
////    }
//
////    @Transactional
////    public ResponseWrapper<Integer> updStrfMenu(List<MultipartFile> menuPic, StrfUpdMenu req) {
////        long userId = authenticationFacade.getSignedUserId();
////        User user = userRepository.findById(userId)
////                .orElseThrow(() -> new RuntimeException("user id not found"));
////
////        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
////                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
////
////        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());
////
////        // 기존 메뉴 삭제 후 새로운 메뉴 추가
////        menuRepository.deleteByMenuId(req.getStrfId());
////        List<Menu> savedMenus = saveMenusWithPics(menuPic, strf, req);
////
////        return new ResponseWrapper<>(ResponseCode.OK.getCode(), savedMenus.size());
////    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updDetail(Long strfId, String detail, String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        if (strf.getDetail().equals(detail)) {
//            return new ResponseWrapper<>(ResponseCode.OK.getCode(), 0);
//        }
//
//        strf.setDetail(detail);
//        strfRepository.save(strf);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updFestTime(Long strfId, StrfFestTime p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        boolean updated = updStrfFestTime(strf, p);
//
//        if (updated) {
//            strfRepository.save(strf);
//        }
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), updated ? 1 : 0);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updTitle(Long strfId, String title, String busiNum) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        if (strfRepository.existsByTitle(title)) {
//            throw new RuntimeException("Title already exists");
//        }
//
//        if (strf.getTitle().equals(title)) {
//            return new ResponseWrapper<>(ResponseCode.OK.getCode(), 0);
//        }
//
//        strf.setTitle(title);
//        strfRepository.save(strf);
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
//    }
//
//    @Transactional
//    public ResponseWrapper<Integer> updAddress(Long strfId, StrfUpdAddress p) {
//        long userId = authenticationFacade.getSignedUserId();
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user id not found"));
//
//        StayTourRestaurFest strf = strfRepository.findById(strfId)
//                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
//
//        if (!strf.getBusiNum().toString().equals(p.getBusiNum())) {
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
//
//        boolean updated = updStrfAddressInfo(strf, p);
//
//        if (updated) {
//            strfRepository.save(strf);
//        }
//
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), updated ? 1 : 0);
//    }
//
////    @Transactional
////    public ResponseWrapper<Integer> updStrfPic(Long strfId, List<MultipartFile> strfPic, String busiNum) {
////        long userId = authenticationFacade.getSignedUserId();
////        User user = userRepository.findById(userId)
////                .orElseThrow(() -> new RuntimeException("user id not found"));
////
////        StayTourRestaurFest strf = strfRepository.findById(strfId)
////                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
////
////        strfPicRepository.deleteByStrfId(strfId);
////        updateStrfPics(strfPic, strf);
////
////        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
////    }
//
////    @Transactional
////    public ResponseWrapper<Integer> updateStay(StrfStayUpdReq req) {
////        long userId = authenticationFacade.getSignedUserId();
////        User user = userRepository.findById(userId)
////                .orElseThrow(() -> new RuntimeException("user id not found"));
////
////        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());
////
////        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
////                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
////
////        if (req.getCategory() != null && Category.getKeyByName(req.getCategory()) == Category.STAY) {
////            parlorRepository.deleteByMenuId(req.getMenuId());
////            roomRepository.deleteByMenuId(req.getMenuId());
////
////            List<Parlor> parlors = req.getParlors().stream().map(strfParlor -> {
////                Menu menu = menuRepository.findById(req.getMenuId())
////                        .orElseThrow(() -> new RuntimeException("Menu not found"));
////
////                return Parlor.builder()
////                        .menu(menu)
////                        .maxCapacity(strfParlor.getMaxCapacity())
////                        .recomCapacity(strfParlor.getRecomCapacity())
////                        .surcharge(strfParlor.getSurcharge())
////                        .build();
////            }).collect(Collectors.toList());
////
////            List<Room> rooms = req.getRooms().stream().map(roomId -> {
////                Menu menu = menuRepository.findById(req.getMenuId())
////                        .orElseThrow(() -> new RuntimeException("Menu not found"));
////
////                return Room.builder()
////                        .menu(menu)
////                        .roomId(roomId)
////                        .roomNum(1)
////                        .build();
////            }).collect(Collectors.toList());
////
////            parlorRepository.saveAll(parlors);
////            roomRepository.saveAll(rooms);
////        }
////
////        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
////    }
//
////    @Transactional
////    public ResponseWrapper<Integer> updateAmenity(StrfJpaAmenity req) {
////        long userId = authenticationFacade.getSignedUserId();
////        User user = userRepository.findById(userId)
////                .orElseThrow(() -> new RuntimeException("user id not found"));
////
////        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
////                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
////
////        amenipointRepository.deleteByStrfId(req.getStrfId());
////
////        List<Amenipoint> amenipoints = req.getAmeniPoints().stream().map(amenityId -> {
////            Amenity amenity = amenityRepository.findById(amenityId)
////                    .orElseThrow(() -> new RuntimeException("Amenity ID " + amenityId + "를 찾을 수 없습니다."));
////
////            return Amenipoint.builder()
////                    .id(new AmenipointId(amenity.getAmenityId(), strf.getStrfId()))
////                    .amenity(amenity)
////                    .stayTourRestaurFest(strf)
////                    .build();
////        }).collect(Collectors.toList());
////
////        amenipointRepository.saveAll(amenipoints);
////
////        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
////    }
//
//    public boolean updStrfBasicInfo(StayTourRestaurFest strf, StrfUpdInfo p) {
//        boolean isUpdated = false;
//
//        if (!strf.getTitle().equals(p.getTitle())) {
//            strf.setTitle(p.getTitle());
//            isUpdated = true;
//        }
//
//        if (!strf.getDetail().equals(p.getDetail())) {
//            strf.setDetail(p.getDetail());
//            isUpdated = true;
//        }
//
//        if (isUpdated) {
//            strfRepository.save(strf);
//        }
//
//        return isUpdated;
//    }
//
//    public boolean updStrfAddressInfo(StayTourRestaurFest strf, StrfUpdAddress p) {
//        boolean isUpdated = false;
//
//        if (!strf.getAddress().equals(p.getAddress())) {
//            strf.setAddress(p.getAddress());
//            isUpdated = true;
//        }
//
//        if (!strf.getPost().equals(p.getPost())) {
//            strf.setPost(p.getPost());
//            isUpdated = true;
//        }
//
//        if (isUpdated) {
//            strfRepository.save(strf);
//        }
//
//        return isUpdated;
//    }
//
//    public boolean updStrfTime(StayTourRestaurFest strf, StrfTime p) {
//        boolean isUpdated = false;
//
//        if (!strf.getOpenCheckIn().equals(p.getOpenCheckIn())) {
//            strf.setOpenCheckIn(p.getOpenCheckIn());
//            isUpdated = true;
//        }
//
//        if (!strf.getCloseCheckOut().equals(p.getCloseCheckOut())) {
//            strf.setCloseCheckOut(p.getCloseCheckOut());
//            isUpdated = true;
//        }
//
//        if (isUpdated) {
//            strfRepository.save(strf);
//        }
//
//        return isUpdated;
//    }
//    public boolean updStrfFestTime(StayTourRestaurFest strf, StrfFestTime p) {
//        boolean isUpdated = false;
//
//        if (!strf.getStartAt().equals(p.getStartAt()) || !strf.getEndAt().equals(p.getEndAt())) {
//            strf.setStartAt(p.getStartAt());
//            strf.setEndAt(p.getEndAt());
//            isUpdated = true; // 값이 변경되었으므로 true 설정
//        }
//
//        return isUpdated;
//    }



}




















//    public List<AmenipointId> getAmeniIdList(List<Long> amenityIds, Long strfId) {
//        long startTime = System.currentTimeMillis();
//
//        String cacheKey = "amenity:" + amenityIds.toString() + ":strf:" + strfId;
//
//        Object cachedKey = redisTemplate.opsForValue().get(cacheKey);
//
//        if (cachedKey instanceof String) {
//            try {
//                List<AmenipointId> cachedList = objectMapper.readValue(
//                        (String) cachedKey, new TypeReference<List<AmenipointId>>() {});
//                long elapsedTime = System.currentTimeMillis() - startTime;
//                log.info("Cache hit: {} (retrieval time: {} ms)", cacheKey, elapsedTime);
//                return cachedList;
//            } catch (Exception e) {
//                log.error("Failed to parse cached data", e);
//            }
//        }
//
//        log.info("Cache miss: {}", cacheKey);
//
//        // 캐시 데이터가 없으면 DB에서 조회
//        long dbStartTime = System.currentTimeMillis();
//        List<AmenipointId> ameniPointIds = amenipointRepository.findAllByAmenityIdInAndStrfId(amenityIds, strfId);
//
//        if (!ameniPointIds.isEmpty()) {
//            try {
//                // Redis에 JSON으로 저장 (TTL: 10분)
//                String jsonValue = objectMapper.writeValueAsString(ameniPointIds);
//                redisTemplate.opsForValue().set(cacheKey, jsonValue, Duration.ofMinutes(10));
//            } catch (Exception e) {
//                log.error("Failed to serialize data for Redis", e);
//            }
//        }
//
//        long dbElapsedTime = System.currentTimeMillis() - dbStartTime;
//        log.info("DB retrieval time: {} ms", dbElapsedTime);
//
//        return ameniPointIds;
//
//    }
//    public List<Amenipoint> getAmeniPointList(Long ameniPointId) {
//        long startTime = System.currentTimeMillis();
//
//        // Redis 캐시 키 생성
//        String cacheKey = "amenipoint:" + ameniPointId;
//
//        // Redis에서 캐시 데이터 조회
//        List<Amenipoint> cachedAmeniPoints = (List<Amenipoint>) redisTemplate.opsForValue().get(cacheKey);
//
//        if (cachedAmeniPoints != null) {
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            log.info("Cache hit: {} (retrieval time: {} ms)", cacheKey, elapsedTime);
//            return cachedAmeniPoints;
//        }
//
//        log.info("Cache miss: {}", cacheKey);
//
//        // 캐시 데이터가 없으면 DB에서 조회
//        long dbStartTime = System.currentTimeMillis();
//        List<Amenipoint> ameniPoints = amenipointRepository.findAllByAmeniPointId(ameniPointId);
//
//        // Redis에 저장 (TTL 설정: 10분)
//        redisTemplate.opsForValue().set(cacheKey, ameniPoints, Duration.ofMinutes(10));
//
//        long dbElapsedTime = System.currentTimeMillis() - dbStartTime;
//        log.info("DB retrieval time: {} ms", dbElapsedTime);
//
//        return ameniPoints;
//    }
//    public List<Amenity> getAmenityList(){
//        return null;
//    }