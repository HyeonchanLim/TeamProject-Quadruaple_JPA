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
import org.springdoc.core.parsers.ReturnTypeParser;
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
    private final AmenityRepository amenityRepository;
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

    public ResponseWrapper<GetNonDetail> getNonMemberDetail(Long strfId) {
        if (strfId == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        GetNonDetail detail = strfMapper.getNonMemberDetail(strfId);
        double roundedRating = Math.round(detail.getRatingAvg() * 10) / 10.0;
        detail.setRatingAvg(roundedRating);
        if (detail == null) {
            return new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), detail);
    }

    @Transactional
    public ResponseWrapper<Integer> strfIns(List<MultipartFile> strfPic, List<MultipartFile> menuPic, StrfInsReq p) {
        long userId = authenticationFacade.getSignedUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));
        BusinessNum businessNum = businessNumRepository.findByBusiNum(p.getBusiNum());

        List<Role> roles = roleRepository.findByUserUserId(user.getUserId());
        boolean isBusi = roles.stream().anyMatch(role -> role.getRole() == UserRole.BUSI);

        if (!isBusi) {
            return new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0);
        }

        LocationDetail locationDetail = locationDetailRepository.findById(p.getLocationDetailId())
                .orElseThrow(() -> new RuntimeException("locationDetailId wrong input"));

        Category categoryValue = null;
        if (p.getCategory() != null && Category.getKeyByName(p.getCategory()) != null) {
            categoryValue = Category.getKeyByName(p.getCategory());
        }

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

        if (categoryValue == Category.RESTAUR || categoryValue == Category.STAY) {
            List<Menu> menus = new ArrayList<>();
            List<Parlor> parlors = new ArrayList<>();
            List<Room> rooms = new ArrayList<>();
            List<Amenipoint> amenipoints = new ArrayList<>();

            String middlePathMenu = String.format("strf/%d/menu", strf.getStrfId());
            myFileUtils.makeFolders(middlePathMenu);

            for (MultipartFile pic : menuPic) {
                String savedPicName = myFileUtils.makeRandomFileName(pic);
                String filePath = String.format("%s/%s", middlePathMenu, savedPicName);

                try {
                    for (StrfMenu strfMenu : p.getMenus()) {
                        Menu newMenu = Menu.builder()
                                .stayTourRestaurFest(strf)
                                .title(strfMenu.getMenuTitle())
                                .price(strfMenu.getMenuPrice())
                                .menuPic(savedPicName)
                                .build();
                        menus.add(newMenu);
                        if (categoryValue == Category.STAY) {
                            p.getParlors().stream()
                                    .filter(parlor -> parlor.getMenuId() == strfMenu.getMenuId())
                                    .forEach(strfParlor -> {
                                        Parlor newParlor = Parlor.builder()
                                                .menu(newMenu)
                                                .maxCapacity(strfParlor.getMaxCapacity())
                                                .recomCapacity(strfParlor.getRecomCapacity())
                                                .surcharge(strfParlor.getSurcharge())
                                                .build();
                                        parlors.add(newParlor);
                                    });
                            for (Long roomId : p.getRooms()) {
                                Room newRoom = Room.builder()
                                        .menu(newMenu)
                                        .roomId(roomId)
                                        .roomNum(1)
                                        .build();
                                rooms.add(newRoom);
                            }
                            amenipoints = p.getAmenipoints().stream()
                                    .map(amenityId -> Amenipoint.builder()
                                            .id(new AmenipointId(amenityId, strf.getStrfId()))
                                            .build())
                                    .collect(Collectors.toList());
                        }
                    }
                    myFileUtils.transferTo(pic, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    myFileUtils.deleteFolder(middlePathMenu, true);
                    throw new RuntimeException("메뉴 이미지 저장 실패", e);
                }
            }

            menuRepository.saveAll(menus);
            if (categoryValue == Category.STAY) {
                parlorRepository.saveAll(parlors);
                roomRepository.saveAll(rooms);
                amenipointRepository.saveAll(amenipoints);
            }
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> updateStrf(Long strfId, List<MultipartFile> strfPic, List<MultipartFile> menuPic, StrfInsReq p) {
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));

        // STRF 기본 정보 업데이트
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

        // 이미지 업데이트
        if (strfPic != null && !strfPic.isEmpty()) {
            strfPicRepository.deleteAllByStrfId(strf);  // 기존 이미지 삭제
            saveStrfPics(strfPic, strf);                // 새 이미지 저장
        }

        // 카테고리에 따라 메뉴/객실/편의시설 업데이트
        if (strf.getCategory() == Category.RESTAUR || strf.getCategory() == Category.STAY) {
            menuRepository.deleteByStayTourRestaurFest(strf); // 기존 메뉴 삭제

            List<Menu> menus = saveMenusWithPics(menuPic, p, strf); // 새 메뉴 저장

            if (strf.getCategory() == Category.STAY) {
                parlorRepository.deleteAllByMenuIn(menus);           // 기존 Parlor 삭제
                roomRepository.deleteAllByMenuIn(menus);             // 기존 Room 삭제
                amenipointRepository.deleteByStayTourRestaurFest(strf); // 기존 Amenity 삭제

                saveStayDetails(menus, p, strf);                    // 새 Parlor, Room, Amenity 저장
            }
        }

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }

    @Transactional
    public ResponseWrapper<Integer> deleteStrf(Long strfId) {
        StayTourRestaurFest strf = strfRepository.findById(strfId)
                .orElseThrow(() -> new RuntimeException("STRF ID not found"));

        // 연관 엔티티 삭제
        amenipointRepository.deleteByStayTourRestaurFest(strf);
        menuRepository.deleteByStayTourRestaurFest(strf);
        parlorRepository.deleteAllByMenuStayTourRestaurFest(strf);
        roomRepository.deleteAllByMenuStayTourRestaurFest(strf);
        strfPicRepository.deleteAllByStrfId(strf);

        // STRF 삭제
        strfRepository.delete(strf);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1);
    }
    private void saveStrfPics(List<MultipartFile> pics, StayTourRestaurFest strf) {
        String middlePathStrf = String.format("strf/%d", strf.getStrfId());
        myFileUtils.makeFolders(middlePathStrf);

        pics.forEach(pic -> {
            try {
                String savedPicName = myFileUtils.makeRandomFileName(pic);
                String filePath = String.format("%s/%s", middlePathStrf, savedPicName);

                StrfPic strfPic = StrfPic.builder()
                        .strfId(strf)
                        .picName(savedPicName)
                        .build();
                strfPicRepository.save(strfPic);
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        });
    }

    private List<Menu> saveMenusWithPics(List<MultipartFile> menuPic, StrfInsReq p, StayTourRestaurFest strf) {
        List<Menu> menus = new ArrayList<>();
        String middlePathMenu = String.format("strf/%d/menu", strf.getStrfId());
        myFileUtils.makeFolders(middlePathMenu);

        for (MultipartFile pic : menuPic) {
            try {
                String savedPicName = myFileUtils.makeRandomFileName(pic);
                String filePath = String.format("%s/%s", middlePathMenu, savedPicName);

                for (StrfMenu strfMenu : p.getMenus()) {
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
                throw new RuntimeException("Menu image upload failed", e);
            }
        }

        return menuRepository.saveAll(menus);
    }

    private void saveStayDetails(List<Menu> menus, StrfInsReq p, StayTourRestaurFest strf) {
        List<Parlor> parlors = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<Amenipoint> amenipoints = new ArrayList<>();

        for (Menu menu : menus) {
            p.getParlors().stream()
                    .filter(parlor -> parlor.getMenuId() == menu.getMenuId())
                    .forEach(strfParlor -> {
                        Parlor newParlor = Parlor.builder()
                                .menu(menu)
                                .maxCapacity(strfParlor.getMaxCapacity())
                                .recomCapacity(strfParlor.getRecomCapacity())
                                .surcharge(strfParlor.getSurcharge())
                                .build();
                        parlors.add(newParlor);
                    });

            for (Long roomId : p.getRooms()) {
                Room newRoom = Room.builder()
                        .menu(menu)
                        .roomId(roomId)
                        .roomNum(1)
                        .build();
                rooms.add(newRoom);
            }
        }

        amenipoints = p.getAmenipoints().stream()
                .map(amenityId -> Amenipoint.builder()
                        .id(new AmenipointId(amenityId, strf.getStrfId()))
                        .build())
                .collect(Collectors.toList());

        parlorRepository.saveAll(parlors);
        roomRepository.saveAll(rooms);
        amenipointRepository.saveAll(amenipoints);
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











