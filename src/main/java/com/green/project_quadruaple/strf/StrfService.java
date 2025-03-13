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
    private final RedisTemplate redisTemplate;

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


//    public List<Amenipoint> getAmeniIdList(List<Long> amenityId , Long strfId){
//        long startTime = System.currentTimeMillis();
//        String cachedkey = "amenityId:" + amenityId + ":strfId:" + strfId;
//        RedisTemplate<String , List<Amenipoint>> redisTemplate = new RedisTemplate<>();
//        List<Amenipoint> amenipointId = redisTemplate.opsForValue().get(cachedkey);
//
//
////        List<AmenipointId> amenipointIds = (List<AmenipointId>) redisTemplate.opsForValue().get(cachekey);
//        if (amenipointId != null) {
//            long endTime = System.currentTimeMillis();
//            long elapedTime = endTime - startTime;
//            log.info("cache retrieval time: {} ms" , elapedTime);
//            return amenipointId;
//        }
//
//        log.info("Cache miss: {}", cachedkey);
//
//        // 캐시 데이터가 없으면 DB에서 조회
//        long dbStartTime = System.currentTimeMillis();
//        List<Amenipoint> ameniPoints = amenipointRepository.findAllByAmenityIdAndStrfId(amenityId, strfId);
//
//        // Redis에 저장 (TTL 설정: 10분)
//        redisTemplate.opsForValue().set(cachedkey, ameniPoints, Duration.ofMinutes(10));
//
//        long dbElapsedTime = System.currentTimeMillis() - dbStartTime;
//        log.info("DB retrieval time: {} ms", dbElapsedTime);
//
//        return ameniPoints;
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

    public ResponseWrapper<List<StrfMenu>> getStrfMenu(Long strfId) {
//        String categoryValue = null;
//        if (category != null && Category.getKeyByName(category) != null) {
//            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
//        }
        List<StrfMenu> menus = strfMapper.strfMenu(strfId);
        if (menus == null || menus.isEmpty()){
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), menus);
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
    public ResponseWrapper<Integer> strfInfoIns(List<MultipartFile> strfPic, StrfInsReq p) {
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
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
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
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
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

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
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
                            .menuPic(savedPicName)
                            .build();
                    menus.add(newMenu);
                }
                myFileUtils.transferTo(pic, filePath);
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

    @Transactional
    public ResponseWrapper<Integer> updateStrf(Long strfId, StrfUpdInfo p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));

        updateStrfBasicInfo(strf, p);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updState(Long strfId, int state , String busiNum) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }

        updateStrfState(strf, state);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updDetail(Long strfId, String detail,String busiNum) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
        updateStrfDetail(strf, detail);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updFestTime(Long strfId, StrfFestTime p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
        updateStrfFestTime(strf, p);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updTime(Long strfId, StrfTime p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }

        updateStrfTime(strf, p);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updTell(Long strfId, String tell,String busiNum) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }

        if (strfRepository.existsByTell(tell)) {
            throw new RuntimeException("Title already exists");
        }
        updateStrfTell(strf, tell);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updTitle(Long strfId, String title,String busiNum) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);

//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }

        if (strfRepository.existsByTitle(title)) {
            throw new RuntimeException("Title already exists");
        }
        updateStrfTitle(strf, title);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updAddress(Long strfId, StrfUpdAddress p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(p.getBusiNum());
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
        if (!strf.getBusiNum().toString().equals(p.getBusiNum())){
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        updateStrfAddressInfo(strf, p);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updStrfPic(Long strfId, List<MultipartFile> strfPic,String busiNum) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
        updateStrfPics(strfPic, strf);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updRest(Long strfId, List<String> restDates , String busiNum) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(busiNum);
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }
        updateRestDays(strf,restDates);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updStrfMenu(List<MultipartFile> menuPic, StrfMenuInsReq req) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());
//        if (!strf.getBusiNum().toString().equals(businessNum.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }


        Menu menu = menuRepository.findById(req.getMenuId()).orElseThrow( () -> new RuntimeException("menuId not found"));

        List<Menu> savedMenus = saveMenusWithPics(menuPic, strf, req);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updateStay(StrfStayUpdReq req) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));

//        if (!strf.getBusiNum().toString().equals(req.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }

        Category categoryValue = null;
        if (req.getCategory() != null && Category.getKeyByName(req.getCategory()) != null) {
            categoryValue = Category.getKeyByName(req.getCategory());
        }
        if (categoryValue == Category.STAY) {
            List<Parlor> parlors = new ArrayList<>();
            List<Room> rooms = new ArrayList<>();
            for (StrfParlor strfParlor : req.getParlors()) {
                Menu menu = menuRepository.findById(req.getMenuId())
                        .orElseThrow(() -> new RuntimeException("Menu not found"));

                Parlor newParlor = Parlor.builder()
                        .menu(menu)
                        .maxCapacity(strfParlor.getMaxCapacity())
                        .recomCapacity(strfParlor.getRecomCapacity())
                        .surcharge(strfParlor.getSurcharge())
                        .build();
                parlors.add(newParlor);
            }

            for (Long roomId : req.getRooms()) {
                Menu menu = menuRepository.findById(req.getMenuId())
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
            Menu menu = menuRepository.findById(req.getMenuId())
                .orElseThrow( () -> new RuntimeException("menu id not found"));

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updateAmenity(StrfJpaAmenity req) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        BusinessNum businessNum = businessNumRepository.findByBusinessNum(req.getBusiNum());

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        StayTourRestaurFest strf = strfRepository.findById(req.getStrfId())
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));

//        if (!strf.getBusiNum().toString().equals(req.getBusiNum())){
//            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
//        }

        Category categoryValue = null;
        if (req.getCategory() != null && Category.getKeyByName(req.getCategory()) != null) {
            categoryValue = Category.getKeyByName(req.getCategory());
        }
        if (categoryValue == Category.STAY) {
//             Amenity 저장
        List<Amenipoint> amenipoints = req.getAmeniPoints().stream()
                .map(amenityId -> Amenipoint.builder()
                        .id(new AmenipointId(amenityId, strf.getStrfId()))
                        .build())
                .toList();
        amenipointRepository.saveAll(amenipoints);
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
        strf.setCloseCheckOut(p.getCloseCheckOut());
        strf.setOpenCheckIn(p.getOpenCheckIn());
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
            List<RestDate> restDates = restdates.stream()
                    .map(day -> {
                        RestDateId id = new RestDateId(Integer.parseInt(day), strf.getStrfId());  // 복합 키 생성
                        return RestDate.builder()
                                .id(id)               // 복합 키 설정
                                .strfId(strf)        // 연관된 엔티티 설정
                                .build();
                    })
                    .collect(toList());

            restDateRepository.saveAll(restDates);  // RestDate 객체들을 DB에 저장
        }
    }
    // 메뉴 저장 (사진 포함)
    private List<Menu> saveMenusWithPics(List<MultipartFile> menuPic, StayTourRestaurFest strf, StrfMenuInsReq menuReq) {
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

}

//    // 숙박 세부 정보 업데이트
//    private void updateStayDetails(List<Menu> menus, StayTourRestaurFest strf,
//                                   StrfMenuInsReq menuReq, StrfStayInsReq stayReq) {
//        // Parlor 저장
//        List<Parlor> parlors = stayReq.getParlors().stream()
//                .map(parlorReq -> Parlor.builder()
//                        .menu(menus.get(0))
//                        .maxCapacity(parlorReq.getMaxCapacity())
//                        .recomCapacity(parlorReq.getRecomCapacity())
//                        .surcharge(parlorReq.getSurcharge())
//                        .build())
//                .collect(toList());
//
//        // Room 저장
//        List<Room> rooms = stayReq.getRooms().stream()
//                .map(roomId -> Room.builder()
//                        .menu(menus.get(0))
//                        .roomId(roomId)
//                        .roomNum(1)
//                        .build())
//                .collect(toList());
//
//        // Amenity 저장
////        List<Amenipoint> amenipoints = stayReq.getAmenipoints().stream()
////                .map(amenityId -> Amenipoint.builder()
////                        .id(new AmenipointId(amenityId, strf.getStrfId()))
////                        .build())
////                .collect(Collectors.toList());
//
//        parlorRepository.saveAll(parlors);
//        roomRepository.saveAll(rooms);
////        amenipointRepository.saveAll(amenipoints);
//    }

//