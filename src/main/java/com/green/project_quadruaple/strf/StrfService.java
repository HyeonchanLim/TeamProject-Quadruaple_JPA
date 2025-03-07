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
import com.green.project_quadruaple.strf.model.StrfMenu;
import com.green.project_quadruaple.trip.model.Category;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @Transactional
    public ResponseWrapper<Integer> strfInfoIns(List<MultipartFile> strfPic, StrfInsReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));

        BusinessNum businessNum = businessNumRepository.findByBusiNum(p.getBusiNum());
        if (businessNum.getBusiNum().equals(p.getBusiNum())) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        // 프론트에서 받은 locationTitle 이름이 맞는 지역이 있는지 확인 ->
        // 있다면 해당 지역의 location_detail_id 가져와서 매핑
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
        strfRepository.save(strf);

        if (p.getRestdates() != null && !p.getRestdates().isEmpty()) {
            StrfRestDate restDateHandler = new StrfRestDate();
            restDateHandler.addRestDays(p.getRestdates());  // "sun", "wed", "fri" 등 받아서 숫자로 변환

            List<Integer> restDays = restDateHandler.getRestDays();
            for (Integer day : restDays) {
                // RestDate 엔티티 저장
                RestDate restDate = RestDate.builder()
                        .strfId(strf)
                        .dayWeek(day)  // "sun" -> 0, "wed" -> 3 등
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
    public ResponseWrapper<Integer> strfMenuIns(List<MultipartFile> menuPic, StrfMenuInsReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        BusinessNum businessNum = businessNumRepository.findByBusiNum(p.getBusiNum());
        if (!businessNum.getBusiNum().equals(p.getBusiNum())) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        StayTourRestaurFest strf = strfRepository.findById(p.getStrfId()).orElseThrow(() -> new RuntimeException("StayTourRestaurFest not found"));

        Category categoryValue = null;
        if (p.getCategory() != null && Category.getKeyByName(p.getCategory()) != null) {
            categoryValue = Category.getKeyByName(p.getCategory());
        }

        if (categoryValue == Category.RESTAUR || categoryValue == Category.STAY) {
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
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> strfStayIns(StrfStayInsReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        BusinessNum businessNum = businessNumRepository.findByBusiNum(p.getBusiNum());

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);
        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        StayTourRestaurFest strf = strfRepository.findById(p.getStrfId())
                .orElseThrow(() -> new RuntimeException("StayTourRestaurFest not found"));

        Category categoryValue = null;
        if (p.getCategory() != null && Category.getKeyByName(p.getCategory()) != null) {
            categoryValue = Category.getKeyByName(p.getCategory());
        }

        List<Menu> menus = new ArrayList<>();

        if (categoryValue == Category.RESTAUR || categoryValue == Category.STAY) {
            for (MenuIns strfMenu : p.getMenus()) {
                Menu newMenu = Menu.builder()
                        .stayTourRestaurFest(strf)
                        .title(strfMenu.getMenuTitle())
                        .price(strfMenu.getMenuPrice())
                        .menuPic(strfMenu.getMenuPic())
                        .build();
                menus.add(newMenu);
            }
            menuRepository.saveAll(menus);
        }

        if (categoryValue == Category.STAY) {
            List<Parlor> parlors = new ArrayList<>();
            List<Room> rooms = new ArrayList<>();
            List<Amenipoint> amenipoints = new ArrayList<>();

            for (StrfParlor strfParlor : p.getParlors()) {
                Menu menu = menus.stream()
                        .filter(m -> m.getMenuId().equals(strfParlor.getMenuId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Menu not found"));

                Parlor newParlor = Parlor.builder()
                        .menu(menu)
                        .maxCapacity(strfParlor.getMaxCapacity())
                        .recomCapacity(strfParlor.getRecomCapacity())
                        .surcharge(strfParlor.getSurcharge())
                        .build();
                parlors.add(newParlor);
            }

            amenipoints = p.getAmenipoints().stream()
                    .map(amenityId -> Amenipoint.builder()
                            .id(new AmenipointId(amenityId, strf.getStrfId()))
                            .build())
                    .collect(Collectors.toList());

            for (Long roomId : p.getRooms()) {
                Menu menu = menus.stream()
                        .filter(m -> m.getMenuId().equals(roomId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Room menu not found"));

                Room newRoom = Room.builder()
                        .menu(menu)
                        .roomId(roomId)
                        .roomNum(1)
                        .build();
                rooms.add(newRoom);
            }

            parlorRepository.saveAll(parlors);
            roomRepository.saveAll(rooms);
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

    @Transactional
    public ResponseWrapper<Integer> updateStrf(Long strfId,
                                               List<MultipartFile> strfPic,
                                               List<MultipartFile> menuPic,
                                               StrfUpdInfo p,
                                               StrfMenuInsReq menuReq,
                                               StrfStayInsReq stayReq) {
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));

        updateStrfBasicInfo(strf, p);

        updateStrfPics(strfPic, strf);

        updateRestDays(strf, p.getRestdates());

        if (isRestaurantOrStay(strf)) {
            List<Menu> savedMenus = saveMenusWithPics(menuPic, strf, menuReq);
            if (strf.getCategory().equals("STAY")) {
                updateStayDetails(savedMenus, strf, menuReq, stayReq);
            }
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
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
                    .map(day -> RestDate.builder()
                            .strfId(strf)  // StayTourRestaurFest 객체 설정
                            .dayWeek(Integer.parseInt(day))  // dayWeek 필드를 Integer로 변환하여 설정
                            .build())
                    .collect(Collectors.toList());

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

    // 숙박 세부 정보 업데이트
    private void updateStayDetails(List<Menu> menus, StayTourRestaurFest strf,
                                   StrfMenuInsReq menuReq, StrfStayInsReq stayReq) {
        // Parlor 저장
        List<Parlor> parlors = stayReq.getParlors().stream()
                .map(parlorReq -> Parlor.builder()
                        .menu(menus.get(0))
                        .maxCapacity(parlorReq.getMaxCapacity())
                        .recomCapacity(parlorReq.getRecomCapacity())
                        .surcharge(parlorReq.getSurcharge())
                        .build())
                .collect(Collectors.toList());

        // Room 저장
        List<Room> rooms = stayReq.getRooms().stream()
                .map(roomId -> Room.builder()
                        .menu(menus.get(0))
                        .roomId(roomId)
                        .roomNum(1)
                        .build())
                .collect(Collectors.toList());

        // Amenity 저장
        List<Amenipoint> amenipoints = stayReq.getAmenipoints().stream()
                .map(amenityId -> Amenipoint.builder()
                        .id(new AmenipointId(amenityId, strf.getStrfId()))
                        .build())
                .collect(Collectors.toList());

        parlorRepository.saveAll(parlors);
        roomRepository.saveAll(rooms);
        amenipointRepository.saveAll(amenipoints);
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



/*
String middlePathMenu = String.format("strf/%d/menu", strf.getStrfId());
        myFileUtils.makeFolders(middlePathMenu);
List<Menu> menus = new ArrayList<>();
        List<Parlor> parlors = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<RestDate> restDates = new ArrayList<>();
        List<String> picNameList = new ArrayList<>(strfPic.size());

        for (MultipartFile pic : menuPic) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            picNameList.add(savedPicName);
            String filePath = String.format("%s/%s", middlePathMenu, savedPicName);
            try {
                for (StrfMenu menu : p.getMenus()){
                    Menu menu1 = new Menu();
                }
                    for (Menu menu : p.getMenus()) {
                        Menu newMenu = new Menu();
                        newMenu.setMenuId(menu.getMenuId());
                        newMenu.setPrice(menu.getPrice());
                        newMenu.setMenuPic(savedPicName);
                        menus.add(newMenu);

                        Parlor newParlor = new Parlor();
                        newParlor.setMenuId(menu.getMenuId());
                        newParlor.setMenu(newMenu);
                        newParlor.setSurcharge(); // 필요한 값으로 설정
                        newParlor.setRecomCapacity(); // 필요한 값으로 설정
                        newParlor.setMaxCapacity(); // 필요한 값으로 설정
                        parlors.add(newParlor);

                        Room newRoom = new Room();
                        newRoom.setMenu(newMenu);
                        newRoom.setRoomId(); // 필요한 값으로 설정
                        newRoom.setRoomNum(1); // 필요한 값으로 설정
                        rooms.add(newRoom);
                    }

                    menuRepository.saveAll(menus);
                    parlorRepository.saveAll(parlors);
                    roomRepository.saveAll(rooms);

                    myFileUtils.transferTo(pic, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePathMenu);
                    myFileUtils.deleteFolder(delFolderPath, true);
                    throw new RuntimeException(e);
                }
            }
            return null;
    }
 */











