package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.CookieUtils;
import com.green.project_quadruaple.common.config.constant.JwtConst;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.user.exception.CustomException;
import com.green.project_quadruaple.user.exception.UserErrorCode;
import com.green.project_quadruaple.user.mail.MailService;
import com.green.project_quadruaple.user.model.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SignatureException;
import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MyFileUtils myFileUtils;
    private final TokenProvider jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private static String FROM_ADDRESS;

    @Value("${file.directory}")
    private String fileDirectory;

    // 회원가입 및 이메일 인증 메일 발송
    public int signUp(MultipartFile pic, UserSignUpReq p) {
        String email = p.getEmail();

        // 이메일 인증 여부
        if (!checkEmail(email)) {
            return 0;
        }

        // 이메일 중복 체크
        DuplicateEmailResult duplicateEmailResult = userMapper.getEmailDuplicateInfo(p);
        if (duplicateEmailResult.getEmailCount() > 0) {
            return 0;
        }

        // 비밀번호 해싱
        String hashedPassword = passwordEncoder.encode(p.getPw());
        p.setPw(hashedPassword);

        String savedPicName;
        boolean isDefaultPic = false;

        if (pic != null && !pic.isEmpty()) {
            savedPicName = myFileUtils.makeRandomFileName(pic);
        } else {
            // 프로필 사진이 없으면 기본 사진 사용
            savedPicName = "user_profile.png";
            isDefaultPic = true;
        }
        p.setProfilePic(savedPicName); // DB 저장 전에 profilePic 설정

        try {
            int result = userMapper.insUser(p);
            if (result > 0) {
                long userId = p.getUserId(); // DB에 삽입 후 userId 값 가져오기
                p.setUserId(userId);
                userMapper.insUserRole(p);

                // 프로필 사진 저장 경로 설정
                String middlePath = String.format("user/%s", userId);
                myFileUtils.makeFolders(middlePath);
                String filePath = String.format("%s/%s", middlePath, savedPicName);
                Path destination = Paths.get(fileDirectory, filePath); // 절대 경로로 설정

                try {
                    if (isDefaultPic) {
                        // 기본 프로필 사진 복사
                        Path source = Paths.get(fileDirectory, "common", "user_profile.png");

                        // 디버깅용 로그 추가
                        System.out.println("Source Path: " + source.toAbsolutePath());
                        System.out.println("Destination Path: " + destination.toAbsolutePath());

                        // 원본 파일 존재 여부 확인
                        if (!Files.exists(source)) {
                            System.out.println("기본 프로필 사진이 존재하지 않습니다!");
                        } else {
                            System.out.println("기본 프로필 사진이 존재합니다.");
                        }

                        // 대상 폴더 생성 (존재하지 않으면 생성)
                        Files.createDirectories(destination.getParent());

                        // 기본 사진 복사
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("기본 프로필 사진 복사 완료!");
                    } else {
                        myFileUtils.transferTo(pic, filePath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            return 0;
        }

        return 1;
    }



    public boolean checkDuplicatedEmail(String email) {
        boolean isDuplicated = userMapper.isEmailDuplicated(email);
        if (isDuplicated) {
            return false;
        }
        return true;
    }

    //-------------------------------------------------
    // 로그인
    @Transactional
    public UserSignInRes signIn(UserSignInReq req, HttpServletResponse response) {
        UserSelOne userSelOne = userMapper.selUserByEmail(req).orElseThrow(() -> {
            throw new RuntimeException("아이디를 확인해 주세요.");
        });

        if(!passwordEncoder.matches(req.getPw(), userSelOne.getPw())) {
            throw new RuntimeException("비밀번호를 확인해 주세요.");
        }

        List<UserRole> roles = new ArrayList<>(2);
        roles.add(UserRole.USER);
        roles.add(UserRole.ADMIN);
        roles.add(UserRole.BUIS);
        userSelOne.setRoles(roles);

        // AT, RT
        JwtUser jwtUser = new JwtUser(userSelOne.getUserId(), userSelOne.getRoles());
        String accessToken = jwtTokenProvider.generateToken(jwtUser, Duration.ofHours(6));
        String refreshToken = jwtTokenProvider.generateToken(jwtUser, Duration.ofDays(15));

        // RT를 쿠키에 담는다.
        // refreshToken은 쿠키에 담는다.
        int maxAge = 1_296_000; // 15 * 24 * 60 * 60 > 15일의 초(second) 값
        cookieUtils.setCookie(response, "refreshToken", refreshToken, maxAge);

        return UserSignInRes.builder()
                .accessToken(accessToken)
                .userId(userSelOne.getUserId())
                .name(userSelOne.getName())
                .build();
    }

    public String getAccessToken (HttpServletRequest req) {
        Cookie cookie = cookieUtils.getCookie(req,"refreshToken");
        String refreshToken = cookie.getValue();
        log.info("refreshToken: {}", refreshToken);

        JwtUser jwtUser = jwtTokenProvider.getJwtUserFromToken(refreshToken);
        String accessToken = jwtTokenProvider.generateToken(jwtUser, Duration.ofHours(8));

        return accessToken;
    }

    private boolean checkEmail(String email) {
        // 인증된 이메일이 아닐때, 인증 만료되었을때
        return MailService.mailChecked.getOrDefault(email, false);
    }


    //-------------------------------------------------
    // 프로필 및 계정 조회
    public UserInfoDto infoUser() {
        try {
            // 토큰에서 사용자 ID 가져오기
            long signedUserId = authenticationFacade.getSignedUserId();

            // 사용자 정보 조회
            UserInfo userInfo = userMapper.selUserInfo(signedUserId);
            return UserInfoDto.builder()
                    .signedUserId(signedUserId)
                    .name(userInfo.getName())
                    .email(userInfo.getEmail())
                    .profilePic(userInfo.getProfilePic())
                    .build();
        } catch (ExpiredJwtException e) {
            // 토큰 만료 에러 처리
            throw new CustomException(UserErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            // 유효하지 않은 토큰 에러 처리
            throw new CustomException(UserErrorCode.INVALID_TOKEN);
        }
    }

    //-------------------------------------------------
    // 프로필 및 계정 수정
    public UserUpdateRes patchUser(MultipartFile profilePic, UserUpdateReq req) {
        long signedUserId = authenticationFacade.getSignedUserId();
        req.setSignedUserId(signedUserId);

        String targetDir = "user/" + req.getSignedUserId();
        myFileUtils.makeFolders(targetDir);

        String deletePath = String.format("%s/user/%s", myFileUtils.getUploadPath(), req.getSignedUserId());

        if (profilePic != null && !profilePic.isEmpty()) {
            String savedFileName = myFileUtils.makeRandomFileName(profilePic);
            myFileUtils.deleteFolder(deletePath, false);

            String filePath = String.format("%s/%s", targetDir, savedFileName);
            try {
                myFileUtils.transferTo(profilePic, filePath);
                req.setProfilePic(savedFileName);
            } catch (IOException e) {
                throw new RuntimeException("프로필 사진 저장에 실패했습니다.", e);
            }
        } else {
            myFileUtils.deleteFolder(deletePath, false);
            req.setProfilePic(null);
        }

        int result = userMapper.updUser(req);
        if (result == 0) {
            throw new RuntimeException("사용자 정보를 업데이트하는 데 실패했습니다.");
        }

        return UserUpdateRes.builder()
                .signedUserId(signedUserId)
                .profilePic(req.getProfilePic())
                .build();
    }

    //-------------------------------------------------
    // 계정 비밀번호 변경
    public void changePassword(ChangePasswordReq req) {
        long signedUserId = authenticationFacade.getSignedUserId();

        ChangePasswordRes checkPassword = userMapper.checkPassword(signedUserId, req.getPw());

        if (checkPassword == null || !passwordEncoder.matches(req.getPw(), checkPassword.getPw())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        if (req.getNewPw().equals(req.getPw())) {
            throw new IllegalArgumentException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        String hashedPassword = passwordEncoder.encode(req.getNewPw());
        userMapper.changePassword(signedUserId, hashedPassword);
    }

    //-------------------------------------------------
    // 임시 비밀번호
    public int temporaryPw(TemporaryPwDto temporaryPwDto) {
        long userId = userMapper.checkUserId(temporaryPwDto.getEmail());
        if (userId == 0) {
            throw new RuntimeException("해당 이메일에 등록된 사용자 정보가 없습니다.");
        }
        temporaryPwDto.setUserId(userId);

        char[] upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] numbers = "0123456789".toCharArray();
        char[] specialCharacters = "!@#$%^&*".toCharArray();
        char[] allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*".toCharArray();

        StringBuilder tmpPasswordBuilder = new StringBuilder();
        Random random = new Random();

        // 각 조건을 만족하도록 하나씩 추가
        tmpPasswordBuilder.append(upperCaseLetters[random.nextInt(upperCaseLetters.length)]);
        tmpPasswordBuilder.append(numbers[random.nextInt(numbers.length)]);
        tmpPasswordBuilder.append(specialCharacters[random.nextInt(specialCharacters.length)]);

        // 나머지 5개 문자는 무작위로 추가
        for (int i = 0; i < 5; i++) {
            tmpPasswordBuilder.append(allCharacters[random.nextInt(allCharacters.length)]);
        }

        // 문자 배열로 변환하여 섞음
        char[] passwordArray = tmpPasswordBuilder.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        // 최종 임시 비밀번호 생성
        String tmpPassword = new String(passwordArray);
        temporaryPwDto.setTpPw(tmpPassword);

        String tmpPasswordOriginal = temporaryPwDto.getTpPw();
        String hashedPassword = passwordEncoder.encode(tmpPasswordOriginal);
        temporaryPwDto.setTpPw(hashedPassword);

        int result = userMapper.temporaryPw(temporaryPwDto);
        int updResult = userMapper.updTemporaryPw(temporaryPwDto);
        int changeResult = userMapper.changePwByTemporaryPw(temporaryPwDto);

        if(updResult == 1) {
            MimeMessage message = javaMailSender.createMimeMessage();

            try {
                message.setFrom(FROM_ADDRESS);
                message.setRecipients(MimeMessage.RecipientType.TO, String.valueOf(temporaryPwDto.getEmail()));
                message.setSubject("임시 비밀번호 안내입니다.");
                String body = "<!DOCTYPE html>" +
                        "<html lang=\"ko\">" +
                        "<head>" +
                        "  <meta charset=\"UTF-8\" />" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                        "  <title>임시 비밀번호 안내</title>" +
                        "</head>" +
                        "<body style=\"margin: 0; padding: 0; font-family: 'Arial', sans-serif; background-color: #f9f9f9;\">" +
                        "  <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width: 600px; background-color: #ffffff; border-collapse: collapse;\">" +
                        "    <tr>" +
                        "      <td align=\"center\" style=\"padding: 20px; border-bottom: 3px solid #0dd1fd;\">" +
                        "        <h1 style=\"font-size: 24px; color: #000000; margin: 0;\">" +
                        "          임시 비밀번호 안내" +
                        "        </h1>" +
                        "      </td>" +
                        "    </tr>" +
                        "    <tr>" +
                        "      <td align=\"center\" style=\"padding: 20px; color: #616161; font-size: 14px; line-height: 1.6;\">" +
                        "        <p>안녕하세요. QUADRUPLE 임시 비밀번호가 생성되었습니다.</p>" +
                        "        <p>아래의 임시 비밀번호로 로그인해 주세요.</p>" +
                        "      </td>" +
                        "    </tr>" +
                        "    <tr>" +
                        "      <td align=\"center\" style=\"padding: 20px;\">" +
                        "        <div style=\"display: inline-block; background-color: rgba(148, 221, 255, 0.47); color: #02aed5; font-size: 28px; font-weight: bold; padding: 15px 20px; border-radius: 8px;\">" +
                        "          " + tmpPasswordOriginal +  // 임시 비밀번호 삽입
                        "        </div>" +
                        "      </td>" +
                        "    </tr>" +
                        "    <tr>" +
                        "      <td align=\"center\" style=\"padding: 20px; color: #616161; font-size: 12px; line-height: 1.4;\">" +
                        "        <p>• 위 내용을 요청하지 않았는데 본 메일을 받으셨다면 고객 센터에 문의해 주세요.</p>" +
                        "      </td>" +
                        "    </tr>" +
                        "    <tr>" +
                        "      <td align=\"center\" style=\"padding: 20px; font-size: 10px; color: #9e9e9e; line-height: 1.4;\">" +
                        "        <p>본 메일은 QUADRUPLE에서 발송한 메일이며 발신전용입니다. 관련 문의 사항은 고객센터로 연락주시기 바랍니다.</p>" +
                        "        <p>© 2024 QUADRUPLE. All rights reserved.</p>" +
                        "      </td>" +
                        "    </tr>" +
                        "  </table>" +
                        "</body>" +
                        "</html>";
                message.setText(body,"UTF-8", "html");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            javaMailSender.send(message);
        }

        return updResult;
    }

    public boolean checkTempPassword(String email) {
        Map<String, String> pwData = userMapper.getPwAndTempPw(email);

        if (pwData == null) {
            return false; // 데이터가 없는 경우 false 반환
        }

        String pw = pwData.get("pw");
        String tpPw = pwData.get("tp_pw");

        return pw != null && pw.equals(tpPw); // 같으면 true 반환
    }

}