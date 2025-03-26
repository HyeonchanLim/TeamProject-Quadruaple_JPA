package com.green.project_quadruaple.datamanager;


import com.green.project_quadruaple.entity.repository.MenuRepository;
import com.green.project_quadruaple.entity.repository.ParlorRepository;
import com.green.project_quadruaple.entity.repository.RoomRepository;
import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.datamanager.model.*;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.review.ReviewMapper;
import com.green.project_quadruaple.review.model.ReviewPicDto;
import com.green.project_quadruaple.review.model.ReviewPostReq;
import com.green.project_quadruaple.entity.repository.StrfRepository;
import com.green.project_quadruaple.entity.repository.ScheMemoRepository;
import com.green.project_quadruaple.entity.repository.ScheduleRepository;
import com.green.project_quadruaple.entity.repository.TripRepository;
import com.green.project_quadruaple.entity.repository.TripUserRepository;
import com.green.project_quadruaple.trip.model.Category;
import com.green.project_quadruaple.entity.repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class DataService {
    private final DataMapper dataMapper;
    private final MyFileUtils myFileUtils;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final StrfRepository strfRepository;
    private final TripRepository tripRepository;
    private final ScheMemoRepository scheMemoRepository;
    private final ScheduleRepository scheduleRepository;
    private final TripUserRepository tripUserRepository;
    private final RoomRepository roomRepository;
    private final ParlorRepository parlorRepository;
    private final MenuRepository menuRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    //카테고리별로 리뷰 더미데이터 넣기
    public ResponseEntity<ResponseWrapper<Integer>> insReviewAndPicsFromCategory(String category) {
        //카테고리별 넣어야할 strfId들 가져오기
        List<Long> strfIds = dataMapper.selectReviewStrfId(
                new StrfReviewGetReq(category, null, null, null, 0, 0, null, null));
        List<Long> reviewIds = new ArrayList<>(strfIds.size()); //리뷰id 저장
        //상품id마다 랜덤 리뷰 넣기
        for (long strfId : strfIds) {
            for (int i = 0; i < randomNum(5, 2); i++) {
                ReviewRandomReq req = randomReview(category, strfId);
                dataMapper.postRating(req);
                reviewIds.add(req.getReviewId());
            }
        }
        /*
        ${file:directory}/reviewsample/${category}/${filename} 의 사진들을
        ${file:directory}/reviewId/${reviewId} 아래로 옮기기
         */

        String filePath = String.format("%s/reviewsample/%s", myFileUtils.getUploadPath(), category);

        int fileCnt = 0; //reviewsample에 카테고리별 파일 갯수 확인
        int reviewPicCnt = 0; //리뷰 하나당 들어가 사진 갯수
        try {
            fileCnt = (int) myFileUtils.countFiles(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Map<String, Object>> reviewPics = new ArrayList<>(reviewIds.size()); //어느리뷰에 어느사진들이 들어가는지, review_pic에 insert할 내용
        for (long reviewId : reviewIds) {
            //category 내부의 랜덤 숫자 파일
            String sourcePath = String.format("%s/%d", filePath, randomNum(fileCnt));
            //review id별로 저장될 사진 경로
            String destinationPath = String.format("%s/reviewId/%d", myFileUtils.getUploadPath(), reviewId);
            try {
                //저장될 사진 갯수
                reviewPicCnt = (int) myFileUtils.countFiles(sourcePath);
                //source에서 destination으로 파일 복사
                myFileUtils.copyFolder(Path.of(sourcePath), Path.of(destinationPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int i = 1; i <= reviewPicCnt; i++) {
                Map<String, Object> pics = new HashMap<>();  //reviewPics에 넣을 객체
                pics.put("reviewId", reviewId);
                pics.put("pics", String.format("%d.png", i));
                reviewPics.add(pics);
            }
        }
        int reviewInsCnt = dataMapper.postReviewPicList(reviewPics);
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), reviewInsCnt));
    }

    //max까지의 랜덤숫자
    private int randomNum(int max) {
        return randomNum(max, 1);
    }

    //min부터 max까지 랜덤숫자
    private int randomNum(int max, int min) {
        Random random = new Random();
        return random.nextInt(max) + min;
    }

    // review 테이블에 insert될 랜덤객체
    private ReviewRandomReq randomReview(String category, long strfId) {
        ReviewRandomReq req = new ReviewRandomReq();
        req.setUserId(randomNum(138)); //랜덤 유저
        req.setStrfId(strfId);
        int random = randomNum(5); //랜덤점수
        req.setRating(random);
        switch (category) {
            case "STAY":
                switch (random) {
                    case 1:
                        req.setContent("침구가 눅눅하고 소음이 심했어요. 직원 응대도 불친절했습니다.");
                        return req;
                    case 2:
                        req.setContent("직원 서비스는 좋았지만 청결 상태가 아쉬웠어요.");
                        return req;
                    case 3:
                        req.setContent("위치가 좋아서 이동하기 편리했어요. 근데 와이파이가 제대로 안되네요.");
                        return req;
                    case 4:
                        req.setContent("객실이 넓고 깔끔했어요! 가성비 좋은 숙소였습니다.");
                        return req;
                    case 5:
                        req.setContent("완벽한 숙소였어요! 방도 깨끗하고 직원 서비스도 짱ㅠㅠ 최고의 숙소 경험이었어요!");
                        return req;
                }
            case "TOUR":
                switch (random) {
                    case 1:
                        req.setContent("너무 붐비고 기대했던 것보다 별로였어요.");
                        return req;
                    case 2:
                        req.setContent("한 번쯤은 가볼 만하지만 다시 방문하고 싶지는 않아요.");
                        return req;
                    case 3:
                        req.setContent("특별한 감동은 없었지만 괜찮은 여행지였어요.");
                        return req;
                    case 4:
                        req.setContent("사람이 많긴 했지만 방문할 만한 가치가 있었어요.");
                        return req;
                    case 5:
                        req.setContent("풍경이 너무 아름답고 사진이 잘 나와요! 필수 방문 코스!");
                        return req;
                }
            case "RESTAUR":
                switch (random) {
                    case 1:
                        req.setContent("위생 상태가 너무 안 좋았어요.");
                        return req;
                    case 2:
                        req.setContent("분위기는 좋았지만 맛은 별로였어요.");
                        return req;
                    case 3:
                        req.setContent("가격 대비 나쁘지 않은 선택이었어요.");
                        return req;
                    case 4:
                        req.setContent("분위기가 좋아서 데이트하기 딱이에요.");
                        return req;
                    case 5:
                        req.setContent("인생 맛집 발견! 너무 맛있었어요. 재방문 의사 100%!");
                        return req;
                }
            case "FEST":
                switch (random) {
                    case 1:
                        req.setContent("기대했던 것보다 실망스러웠어요.");
                        return req;
                    case 2:
                        req.setContent("주차가 너무 어려워서 불편했어요.");
                        return req;
                    case 3:
                        req.setContent("그냥 시간 보내기 좋은 정도였어요.");
                        return req;
                    case 4:
                        req.setContent("축제 분위기가 살아 있어서 즐거운 시간이었어요.");
                        return req;
                    case 5:
                        req.setContent("잊지 못할 경험이었어요. 강추합니다!");
                        return req;
                }
        }
        return null;
    }

    //제목에 따른 리뷰 더미데이터 넣기
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> insReviewAndPicsFromTitle(StrfReviewGetReq p) {
        // 리뷰 넣을 strfId list 가져오기
        List<Long> strfIds = dataMapper.selectReviewStrfId(p);
        if (strfIds == null || strfIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        String sourcePath = String.format("%s/reviewsample/%s/%s", myFileUtils.getUploadPath(), p.getCategory(), p.getPicFolder());
        int reviewPicCnt;

        try {
            //review사진 파일 갯수 확인
            reviewPicCnt = (int) myFileUtils.countFiles(sourcePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //reviewId를 저장할 리스트
        List<Long> reviewIds = new ArrayList<>();
        //여기서 insert 먼저 실행
        for (long strfId : strfIds) {
            ReviewPostReq req = new ReviewPostReq();
            req.setContent(p.getContent());
            req.setRating(p.getRating());
            req.setStrfId(strfId);
            reviewMapper.postRating(req, p.getUserId());
            reviewIds.add(req.getReviewId());
        }
        int reviewInserted = 0;

        for (long reviewId : reviewIds) {
            String middlePath = String.format("reviewId/%d", reviewId);
            myFileUtils.makeFolders(middlePath);

            String filePath = String.format("%s/reviewId/%d", myFileUtils.getUploadPath(), reviewId);
            try {
                Path source = Paths.get(sourcePath);
                Path destination = Paths.get(filePath);
                myFileUtils.copyFolder(source, destination);
            } catch (IOException e) {
                String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
                myFileUtils.deleteFolder(delFolderPath, true);
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
            }
            ReviewPicDto dto = new ReviewPicDto();
            dto.setReviewId(reviewId);
            dto.setPics(new ArrayList<>());
            for (int i = 1; i <= reviewPicCnt; i++) {
                dto.getPics().add(String.format("%d.png", i));
            }
            reviewInserted += reviewMapper.postReviewPic(dto);
        }

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), reviewInserted));
    }


    //strf 사진, 메뉴 등록
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> insPicAndMenuToStrf(StrfIdGetReq p) {
        //'strfTitle(title 검색어)' 기준으로 혹은 'startId와 endId 사이에서' category에 해당하는 strf_id목록 가져오기
        List<Long> strfIds = dataMapper.selectStrfId(p);
        if (strfIds == null) { //못가져오면 예외처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        //시작전에 secret yml 파일에 docker DB 연결해야함
        //strf_pic 테이블에 INSERT하는 collection
        List<Map<String, Object>> picAndStrfIds = new ArrayList<>(strfIds.size());
        //menu가 있는 상품이 있고 없는 상품이 있음. 없으면 빈배열로.
        List<MenuDto> menus = p.getMenus() == null || p.getMenus().size() == 0 ? new ArrayList<>() : p.getMenus();
        //menu 테이블에 INSERT하는 collection
        List<Map<String, Object>> menuData = new ArrayList<>(strfIds.size() * menus.size());

        // sourcePath: ${file.directory}/pics/${category}/${picFolder}
        String sourcePath = String.format("%s/pics/%s/%s", myFileUtils.getUploadPath(), p.getCategory(), p.getPicFolder());
        // menuPath: ${file.directory}/pics/${category}/${picFolder}/menu
        // menu는 기본적으로 strf 파일 아래에 존재하지만 menu 사진 갯수를 파악하기 위함
        String menuPath = String.format("%s/pics/%s/%s/menu", myFileUtils.getUploadPath(), p.getCategory(), p.getPicFolder());
        int strfCnt;
        int menuCnt;
        try {
            // 경로에 존재하는 디렉토리와 파일의 갯수를 확인하는 메서드.
            // sourceFile은 menu 디렉토리를 포함하므로 -1하여 실제 사진 파일 갯수를 count
            strfCnt = (int) myFileUtils.countFiles(sourcePath) - 1;
            menuCnt = (int) myFileUtils.countFiles(menuPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //req로 들어온 메뉴 리스트의 길이가 menu 디렉토리 안의 사진 갯수와 다르면 예외발생
        if (menuCnt != menus.size()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        for (long strfId : strfIds) {
            String middlePath = String.format("strf/%d", strfId);
            myFileUtils.makeFolders(middlePath);
            // filePath: ${file.directory}/strf/${strfId}
            String filePath = String.format("%s/strf/%d", myFileUtils.getUploadPath(), strfId);
            // sourcePath 아래의 파일을 filePath로 복사
            // 이렇게 파일 만들어서 추후 filezilla로 한꺼번에 올림
            try {
                Path source = Paths.get(sourcePath);
                Path destination = Paths.get(filePath);
                myFileUtils.copyFolder(source, destination);
            } catch (IOException e) {
                //실패시 폴더 삭제 처리
                log.error("파일 저장 실패: {}", filePath, e);
                String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
                myFileUtils.deleteFolder(delFolderPath, true);
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
            }
            // strf_pic insert collection 제작
            for (int i = 0; i < strfCnt; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("strfId", strfId);
                map.put("picName", String.format("%d.png", (i + 1)));
                picAndStrfIds.add(map);
            }
            // menu insert collection 제작
            if (menuCnt != 0) {
                for (int i = 0; i < menus.size(); i++) {
                    Map<String, Object> menuMap = new HashMap<>();
                    MenuDto menu = menus.get(i);
                    menuMap.put("strfId", strfId);
                    menuMap.put("title", menu.getTitle());
                    menuMap.put("price", menu.getPrice());
                    menuMap.put("menuPic", String.format("%d.png", (i + 1)));
                    menuData.add(menuMap);
                }
            }
        }
        //insert
        int result = dataMapper.insStrfPic(picAndStrfIds);
        //menu insert
        if (menuCnt != 0) {
            int menuResult = dataMapper.insMenu(menuData);
            if (menuResult == 0) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
            }
        }
        //예외처리
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //strf사진 삭제
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> delPicToStrf(StrfIdGetReq p) {
        List<Long> strfIds = dataMapper.selectStrfId(p);
        if (strfIds == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        int result = dataMapper.delStrfIdPic(strfIds);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        for (int i = 0; i < strfIds.size(); i++) {
            String deletePath = String.format("%s/strf/%d", myFileUtils.getUploadPath(), strfIds.get(i));
            myFileUtils.deleteFolder(deletePath, true);
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //메뉴 데이터 넣기
    //@Transactional
//    public ResponseEntity<ResponseWrapper<Integer>> insMenu (List<MultipartFile> pics, MenuReq p){
//
//        List<Long> strfIds=dataMapper.selectStrfId(p);
//        if(strfIds==null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));}
//
//        // ${file.directory}/strf/${strfId}/menu/파일명
//        List<Map<String,Object>> menuData =new ArrayList<>(strfIds.size()*p.getMenus().size());
//        List<MenuDto> menus=p.getMenus();
//        for (long strfId:strfIds){
//            String middlePath=String.format("strf/%d/menu",strfId);
//            myFileUtils.makeFolders(middlePath);
//            for(int i=0; i<menus.size(); i++){
//                MenuDto menu=menus.get(i);
//                String savePicName=myFileUtils.makeRandomFileName(pics.get(i));
//                Map<String, Object> map = new HashMap<>();
//                map.put("strfId",strfId);
//                map.put("title",menu.getTitle());
//                map.put("price",menu.getPrice());
//                map.put("menuPic",savePicName);
//                menuData.add(map);
//                String filePath=String.format("%s/%s",middlePath,savePicName);
//                try{
//                    myFileUtils.transferTo(pics.get(i), filePath);
//                }catch(IOException e){
//                    //폴더 삭제 처리
//                    log.error("파일 저장 실패: {}", filePath, e);
//                    String delFolderPath=String.format("%s/%s", myFileUtils.getUploadPath(),middlePath);
//                    myFileUtils.deleteFolder(delFolderPath,true);
//                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                            .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
//                }
//            }
//        }
//        log.info("menuData 3rows:{}",menuData.subList(0,8));
//        int result=dataMapper.insMenu(menuData);
//        if(result==0){
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
//        }
//        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),result));
//    }


    //menu사진 삭제
//    @Transactional
//    public ResponseEntity<ResponseWrapper<Integer>> delMenu (StrfIdGetReq p){
//        List<Long> strfIds= dataMapper.selectStrfId(p);
//        if(strfIds==null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));}
//
//        int result= dataMapper.delMenu(strfIds);
//        if(result==0){
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
//        }
//        for(int i=0; i<strfIds.size(); i++){
//            String deletePath = String.format("%s/strf/%d/menu", myFileUtils.getUploadPath(), strfIds.get(i));
//            myFileUtils.deleteFolder(deletePath, true);
//        }
//        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),result));
//    }

    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> updateInvalidProfilePics() {
        List<UserProfile> users = dataMapper.getAllUsersProfilePics(); // 모든 사용자 profilePic 조회
        if (users.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), 0));
        }

        List<Long> updateUserIds = new ArrayList<>();
        String defaultPicName = "user_profile.png";  // 새로운 기본 프로필 파일명
        String oldPicName = "user.png"; // 기존 잘못된 프로필 파일명
        String defaultPicSourcePath = Paths.get(myFileUtils.getUploadPath(), "common", defaultPicName).toString();
        File sourceFile = new File(defaultPicSourcePath);

        // 기본 프로필 파일이 실제로 존재하는지 확인
        if (!sourceFile.exists()) {
            System.out.println("오류: 기본 프로필 파일이 존재하지 않습니다! 경로: " + sourceFile.getAbsolutePath());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }

        for (UserProfile user : users) {
            String profilePic = user.getProfilePic();

            // user.png를 사용 중이거나 잘못된 프로필인 경우 변경
            if (profilePic == null || !profilePic.contains(".") || profilePic.equalsIgnoreCase(oldPicName)) {
                updateUserIds.add(user.getUserId());

                // 사용자별 프로필 사진 폴더
                String userFolderPath = Paths.get(myFileUtils.getUploadPath(), "user", user.getUserId().toString()).toString();
                File userFolder = new File(userFolderPath);

                // 폴더가 없으면 생성
                if (!userFolder.exists()) {
                    boolean created = userFolder.mkdirs();
                    if (created) {
                        System.out.println("사용자 폴더 생성됨: " + userFolderPath);
                    } else {
                        System.out.println("사용자 폴더 생성 실패! 경로: " + userFolderPath);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
                    }
                }

                // 기존 `user.png` 파일 삭제
                File oldFile = new File(userFolder, oldPicName);
                if (oldFile.exists()) {
                    if (oldFile.delete()) {
                        System.out.println("기존 user.png 삭제 완료: " + oldFile.getAbsolutePath());
                    } else {
                        System.out.println("기존 user.png 삭제 실패! 경로: " + oldFile.getAbsolutePath());
                    }
                }

                // 기본 프로필 적용할 경로
                File destinationFile = new File(userFolder, defaultPicName);

                try {
                    // 기본 프로필 복사 (이미 최신 파일이 아니면 덮어쓰기)
                    if (!destinationFile.exists() || Files.mismatch(sourceFile.toPath(), destinationFile.toPath()) != -1) {
                        Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("기본 프로필 사진 업데이트 완료! (사용자: " + user.getUserId() + ")");
                    } else {
                        System.out.println("이미 최신 기본 프로필 사진이 적용됨: " + destinationFile.getAbsolutePath());
                    }
                } catch (IOException e) {
                    System.out.println("파일 복사 중 오류 발생! 사용자 ID: " + user.getUserId());
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
                }
            }
        }

        // DB에서 user.png → user_profile.png로 변경
        if (!updateUserIds.isEmpty()) {
            int updatedCount = dataMapper.updateProfilePicsToDefault(updateUserIds, defaultPicName);
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), updatedCount));
        }

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), 0));
    }


//    @Transactional
//    public ResultResponse insRoom(String code) {
//
//        if (!code.equals("wooks")) {
//            return ResultResponse.forbidden();
//        }
//        long[] menuIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
//                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
//                41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
//                61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
//                81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100,
//                101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115,
//                116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130,
//                131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145,
//                146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160,
//                161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175,
//                176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190,
//                191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205,
//                206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220,
//                221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235,
//                236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250,
//                251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265,
//                266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280,
//                281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297};
//
//
//        int[] roomNums = {101, 102, 103, 104, 105, 201, 202, 203, 204, 205, 206, 301, 302, 303, 304};
//        for (long id : menuIds) {
//            Menu menu = menuRepository.findById(id).orElse(null);
//            if (menu == null) {
//                return ResultResponse.severError();
//            }
//
//            int recomCapacity = (int) (Math.random() * 10) + 1;
//            int maxCapacity = recomCapacity + 3;
//
//            Parlor parlor = Parlor.builder()
//                    .menu(menu)
//                    .recomCapacity(recomCapacity)
//                    .maxCapacity(maxCapacity)
//                    .surcharge(30000)
//                    .build();
//            parlorRepository.save(parlor);
//            parlorRepository.flush();
//
//            for (int roomNum : roomNums) {
//                Room room = Room.builder()
//                        .menu(menu)
//                        .roomNum(roomNum)
//                        .build();
//                roomRepository.save(room);
//            }
//        }
//        return ResultResponse.success();
//    }

    @Transactional
    public ResultResponse insParlor(String code) {

        if (!code.equals("wooks")) {
            return ResultResponse.forbidden();
        }

        List<Menu> menus = menuRepository.findByCategory(Category.STAY);
        List<Parlor> parlors = parlorRepository.findAll();

        for (Menu menu : menus) {
            boolean flag = false;
            Long menuId = menu.getMenuId();
            for (Parlor parlor : parlors) {
                if(parlor.getMenuId().equals(menuId)) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                String title = menu.getTitle();
                int[] capacity = null;
                if(title.equals("싱글룸")) {
                    capacity = new int[]{2,3,4};
                } else if(title.equals("트윈룸")) {
                    capacity = new int[]{4,5,6};
                } else if(title.equals("패밀리룸")) {
                    capacity = new int[]{7,9,11};
                }
                if(capacity == null) {
                    continue;
                }
                int maxCapacity = capacity[(int)(Math.random()*3)];

                Parlor parlor = Parlor.builder()
                        .menu(menu)
                        .maxCapacity(maxCapacity)
                        .recomCapacity(maxCapacity - 1)
                        .surcharge(30_000)
                        .build();
                parlorRepository.save(parlor);
            }
        }

        return null;
    }

    @Transactional
    public ResultResponse insRoom(String code) {
        if(!code.equals("wooks")) {
            return ResultResponse.forbidden();
        }

        List<Parlor> parlors = parlorRepository.findAllWithFetchJoin();
        List<Room> rooms = roomRepository.findAllRoomsWithDistinctMenu();
        int[] roomNums = new int[]{101,102,103,201,202,301,302};
        for (Parlor parlor : parlors) {
            boolean flag = false;
            Long menuId = parlor.getMenuId();
            for (Room room : rooms) {
                if(room.getMenu().getMenuId().equals(menuId)) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                for (int roomNum : roomNums) {
                    Room room = Room.builder()
                            .menu(parlor.getMenu())
                            .roomNum(roomNum)
                            .build();
                    log.info("room = {}", room);
                roomRepository.save(room);
                }
            }
        }
        return ResultResponse.success();
    }

}
