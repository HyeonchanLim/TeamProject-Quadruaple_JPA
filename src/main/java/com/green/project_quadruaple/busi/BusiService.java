package com.green.project_quadruaple.busi;

import com.green.project_quadruaple.busi.model.BusiPostReq;
import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.CookieUtils;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.RoleId;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.user.Repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.Repository.TemporaryPwRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import com.green.project_quadruaple.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusiService {
    private final RoleRepository roleRepository;
    private final BusiRepository busiRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyFileUtils myFileUtils;
    private final TokenProvider jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final JavaMailSender javaMailSender;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final TemporaryPwRepository temporaryPwRepository;

    @Value("${spring.mail.username}")
    private static String FROM_ADDRESS;

    @Value("${file.directory}")
    private String fileDirectory;

    // 회원가입
//    public int busiSignUp(MultipartFile pic, BusiPostReq p) {
//
//        // 이메일 중복 체크
//        if (userRepository.existsByAuthenticationCode_Email(p.getEmail())) {
//            return 0;
//        }
//
//        // 닉네임 중복 체크 및 유니크한 닉네임 생성
//        if (userRepository.existsByName(p.getName())) {
//            return 0;
//        }
////        if (roleRepository.existsById(p.getBusiNum())){
////            return 0;
////        }
//
//        // 비밀번호 해싱
//        String hashedPassword = passwordEncoder.encode(p.getPw());
//        p.setPw(hashedPassword);
//
//        String savedPicName;
//        boolean isDefaultPic = false;
//
//        if (pic != null && !pic.isEmpty()) {
//            savedPicName = myFileUtils.makeRandomFileName(pic);
//        } else {
//            // 프로필 사진이 없으면 기본 사진 사용
//            savedPicName = "user_profile.png";
//            isDefaultPic = true;
//        }
//
//        try {
//            // 인증 코드가 존재하는지 확인
//            AuthenticationCode authCode = authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(p.getEmail())
//                    .orElseThrow(() -> new RuntimeException("이메일 인증이 완료되지 않았습니다."));
//
//            //이메일을 authCode에서 가져오도록 수정
//            String email = authCode.getEmail();
//
//            User user = new User();
//            user.setAuthenticationCode(authCode);
//            user.setName(p.getName());
//            user.setProfilePic(savedPicName);
//            user.setPassword(hashedPassword);
//            user.setBirth(p.getBirth());
//            user.setTell(p.getTell());
//            user.setProviderType(SignInProviderType.LOCAL);
//            user.setVerified(1);
//
//            userRepository.save(user);
//
//            if (user != null) {
//                long userId = user.getUserId(); // DB에 삽입 후 userId 값 가져오기
//                p.setUserId(userId);
//
//                // Role 설정 추가
//                Role role = Role.builder()
//                        .id(new RoleId(UserRole.BUSI.getValue(), userId))
//                        .user(user)
//                        .role(UserRole.BUSI)
//                        .grantedAt(LocalDateTime.now())
//                        .build();
//                roleRepository.save(role);
//
//                // 프로필 사진 저장 경로 설정
//                String middlePath = String.format("user/%s", userId);
//                myFileUtils.makeFolders(middlePath);
//                String filePath = String.format("%s/%s", middlePath, savedPicName);
//                Path destination = Paths.get(fileDirectory, filePath); // 절대 경로로 설정
//
//                try {
//                    if (isDefaultPic) {
//                        // 기본 프로필 사진 복사
//                        Path source = Paths.get(fileDirectory, "common", "user_profile.png");
//
//                        // 디버깅용 로그 추가
//                        System.out.println("Source Path: " + source.toAbsolutePath());
//                        System.out.println("Destination Path: " + destination.toAbsolutePath());
//
//                        // 원본 파일 존재 여부 확인
//                        if (!Files.exists(source)) {
//                            System.out.println("기본 프로필 사진이 존재하지 않습니다!");
//                        } else {
//                            System.out.println("기본 프로필 사진이 존재합니다.");
//                        }
//
//                        // 대상 폴더 생성 (존재하지 않으면 생성)
//                        Files.createDirectories(destination.getParent());
//
//                        // 기본 사진 복사
//                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
//                        System.out.println("기본 프로필 사진 복사 완료!");
//                    } else {
//                        myFileUtils.transferTo(pic, filePath);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            return 0;
//        }
//
//        return 1;
//    }

}
