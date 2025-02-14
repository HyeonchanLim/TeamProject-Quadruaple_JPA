package com.green.project_quadruaple.datamanager;


import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.datamanager.model.*;
import com.green.project_quadruaple.review.ReviewMapper;
import com.green.project_quadruaple.review.ReviewService;
import com.green.project_quadruaple.review.model.ReviewPicDto;
import com.green.project_quadruaple.review.model.ReviewPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> insReviewAndPics(StrfReviewGetReq p) {
        // 리뷰 넣을 strfId list 가져오기
        List<Long> strfIds = dataMapper.selectReviewStrfId(p);
        if (strfIds==null || strfIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        String sourcePath=String.format("%s/reviewsample/%s/%s",myFileUtils.getUploadPath(), p.getCategory(), p.getPicFolder());
        int reviewPicCnt;

        try{
            //review사진 파일 갯수 확인
            reviewPicCnt=(int) myFileUtils.countFiles(sourcePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //reviewId를 저장할 리스트
        List<Long> reviewIds=new ArrayList<>();
        //여기서 insert 먼저 실행
        for(long strfId : strfIds){
            ReviewPostReq req=new ReviewPostReq();
            req.setContent(p.getContent());
            req.setRating(p.getRating());
            req.setStrfId(strfId);
            reviewMapper.postRating(req,p.getUserId());
            reviewIds.add(req.getReviewId());
        }
        int reviewInserted=0;

        for(long reviewId:reviewIds){
            String middlePath = String.format("reviewId/%d",reviewId);
            myFileUtils.makeFolders(middlePath);

            String filePath = String.format("%s/reviewId/%d",myFileUtils.getUploadPath(),reviewId);
            try{
                Path source = Paths.get(sourcePath);
                Path destination = Paths.get(filePath);
                myFileUtils.copyFolder(source,destination);
            } catch (IOException e){
                String delFolderPath = String.format("%s/%s", myFileUtils.getUploadPath(), middlePath);
                myFileUtils.deleteFolder(delFolderPath, true);
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
            }
            ReviewPicDto dto = new ReviewPicDto();
            dto.setReviewId(reviewId);
            dto.setPics(new ArrayList<>());
            for(int i=1;i<=reviewPicCnt; i++){
                dto.getPics().add(String.format("%d.png",i));
            }
            reviewInserted+=reviewMapper.postReviewPic(dto);
        }

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), reviewInserted));
    }


    //strf 사진, 메뉴 등록
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> insPicAndMenuToStrf(StrfIdGetReq p) {
        //'strfTitle(title 검색어)' 기준으로 혹은 'startId와 endId 사이에서' category에 해당하는 strf_id목록 가져오기
        List<Long> strfIds= dataMapper.selectStrfId(p);
        if(strfIds==null){ //못가져오면 예외처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        //시작전에 secret yml 파일에 docker DB 연결해야함
        //strf_pic 테이블에 INSERT하는 collection
        List<Map<String, Object>> picAndStrfIds = new ArrayList<>(strfIds.size());
        //menu가 있는 상품이 있고 없는 상품이 있음. 없으면 빈배열로.
        List<MenuDto> menus = p.getMenus() == null || p.getMenus().size() == 0 ? new ArrayList<>() : p.getMenus();
        //menu 테이블에 INSERT하는 collection
        List<Map<String,Object>> menuData = new ArrayList<>(strfIds.size()*menus.size());

        // sourcePath: ${file.directory}/pics/${category}/${picFolder}
        String sourcePath=String.format("%s/pics/%s/%s",myFileUtils.getUploadPath(), p.getCategory(), p.getPicFolder());
        // menuPath: ${file.directory}/pics/${category}/${picFolder}/menu
        // menu는 기본적으로 strf 파일 아래에 존재하지만 menu 사진 갯수를 파악하기 위함
        String menuPath=String.format("%s/pics/%s/%s/menu",myFileUtils.getUploadPath(), p.getCategory(), p.getPicFolder());
        int strfCnt;
        int menuCnt;
        try {
            // 경로에 존재하는 디렉토리와 파일의 갯수를 확인하는 메서드.
            // sourceFile은 menu 디렉토리를 포함하므로 -1하여 실제 사진 파일 갯수를 count
             strfCnt=(int) myFileUtils.countFiles(sourcePath) - 1;
             menuCnt=(int) myFileUtils.countFiles(menuPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //req로 들어온 메뉴 리스트의 길이가 menu 디렉토리 안의 사진 갯수와 다르면 예외발생
        if(menuCnt!=menus.size()){
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
        int result= dataMapper.insStrfPic(picAndStrfIds);
        //menu insert
        if (menuCnt != 0) { int menuResult= dataMapper.insMenu(menuData);
            if(menuResult==0){
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
            }
        }
        //예외처리
        if(result==0){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),result));
    }

    //strf사진 삭제
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> delPicToStrf (StrfIdGetReq p){
        List<Long> strfIds= dataMapper.selectStrfId(p);
        if(strfIds==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));}

        int result= dataMapper.delStrfIdPic(strfIds);
        if(result==0){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        for(int i=0; i<strfIds.size(); i++){
            String deletePath = String.format("%s/strf/%d", myFileUtils.getUploadPath(), strfIds.get(i));
            myFileUtils.deleteFolder(deletePath, true);
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),result));
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
}
