package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.CookieUtils;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.notice.NoticeReceiveRepository;
import com.green.project_quadruaple.user.Repository.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.Repository.TemporaryPwRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    private final UserRepository userRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final RoleRepository roleRepository;
    private final TemporaryPwRepository temporaryPwRepository;

    @Value("${spring.mail.username}")
    private static String FROM_ADDRESS;

    @Value("${file.directory}")
    private String fileDirectory;

    // 회원가입
    public int signUp(MultipartFile pic, UserSignUpReq p) {

        // 이메일 중복 체크
        if (userRepository.existsByAuthenticationCode_Email(p.getEmail())) {
            return 0;
        }

        // 닉네임 중복 체크 및 유니크한 닉네임 생성
        if (userRepository.existsByName(p.getName())) {
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
            savedPicName = "user_profile.webp";
            isDefaultPic = true;
        }

        try {
            // 인증 코드가 존재하는지 확인
            AuthenticationCode authCode = authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(p.getEmail())
                    .orElseThrow(() -> new RuntimeException("이메일 인증이 완료되지 않았습니다."));

            //이메일을 authCode에서 가져오도록 수정
            String email = authCode.getEmail();

            String getExt = myFileUtils.getExt(savedPicName);

            User user = new User();
            user.setAuthenticationCode(authCode);
            user.setName(p.getName());
            user.setProfilePic(savedPicName.replace(getExt, ".webp"));
            user.setPassword(hashedPassword);
            user.setBirth(p.getBirth());
            user.setTell(p.getTell());
            user.setProviderType(SignInProviderType.LOCAL);
            user.setVerified(1);

            userRepository.save(user);

            if (user != null) {
                long userId = user.getUserId(); // DB에 삽입 후 userId 값 가져오기
                p.setUserId(userId);

                // Role 설정 추가
                Role role = Role.builder()
                        .id(new RoleId(UserRole.USER, userId))
                        .user(user)
                        .role(UserRole.USER)
                        .grantedAt(LocalDateTime.now())
                        .build();
                roleRepository.save(role);

                // 프로필 사진 저장 경로 설정
                String middlePath = String.format("user/%s", userId);
                myFileUtils.makeFolders(middlePath);
                String filePath = String.format("%s/%s", middlePath, savedPicName);
                Path destination = Paths.get(fileDirectory, filePath); // 절대 경로로 설정

                try {
                    if (isDefaultPic) {
                        // 기본 프로필 사진 복사
                        Path source = Paths.get(fileDirectory, "common", "user_profile.webp");

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
                        myFileUtils.convertAndSaveToWebp(pic, filePath.replaceAll("\\.[^.]+$", ".webp"));
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

    public boolean checkDuplicatedName(String name) {
        boolean isDuplicated = userRepository.existsByName(name);
        if (isDuplicated) {
            return false;
        }
        return true;
    }


    public boolean checkDuplicatedEmail(String email) {
        return !userRepository.existsByAuthenticationCode_Email(email);
    }

    //-------------------------------------------------
    // 로그인
    @Transactional
    public UserSignInRes signIn(UserSignInReq req, HttpServletResponse response) {
        // LOCAL 유저만 swagger 로그인 가능
        User user = userRepository.findByAuthenticationCode_EmailAndProviderType(req.getEmail(), SignInProviderType.LOCAL);
        if (user == null) {
            throw new RuntimeException("아이디를 확인해 주세요.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(req.getPw(), user.getPassword())) {
            throw new RuntimeException("비밀번호를 확인해 주세요.");
        }

        // 이메일 인증 여부 확인
        Optional<AuthenticationCode> authCode = authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(user.getAuthenticationCode().getEmail());
        if (authCode.isEmpty() || user.getVerified() == 0) {
            throw new RuntimeException("이메일 인증이 필요합니다.");
        }

        // 사용자의 역할(Role) 조회
        List<Role> roleEntities = roleRepository.findByUserUserId(user.getUserId());

        // BUSI 역할이 있는지 확인
        boolean hasBusiRole = roleEntities.stream()
                .anyMatch(role -> role.getRole().getName().equals("사업자"));

        if (hasBusiRole) {
            throw new RuntimeException("BUSI 역할을 가진 사용자는 로그인할 수 없습니다.");
        }

        // USER, ADMIN 역할만 필터링
        List<UserRole> roles = roleEntities.stream()
                .map(role -> UserRole.getKeyByName(role.getRole().getName()))
                .filter(userRole -> userRole == UserRole.USER || userRole == UserRole.ADMIN)
                .collect(Collectors.toList());

        // AT, RT
        JwtUser jwtUser = new JwtUser(user.getUserId(), roles);
        String accessToken = jwtTokenProvider.generateToken(jwtUser, Duration.ofHours(6));
        String refreshToken = jwtTokenProvider.generateToken(jwtUser, Duration.ofDays(15));

        // RT를 쿠키에 담는다.
        // refreshToken은 쿠키에 담는다.
        int maxAge = 1_296_000; // 15 * 24 * 60 * 60 > 15일의 초(second) 값
        cookieUtils.setCookie(response, "refreshToken", refreshToken, maxAge, "/api/user/access-token");

        return UserSignInRes.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .name(user.getName())
                .roles(roles)
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

            // JPA로 사용자 정보 조회
            User user = userRepository.findById(signedUserId)
                    .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND)); // 사용자 없을 경우 예외 처리

            // authenticatedId를 이용해 AuthenticationCode에서 이메일 조회
            AuthenticationCode authenticationCode = authenticationCodeRepository
                    .findByAuthenticatedId(user.getAuthenticationCode().getAuthenticatedId()) // authenticatedId로 이메일 조회
                    .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND)); // 인증 코드가 없으면 예외 처리

            // DTO로 변환하여 반환
            return UserInfoDto.builder()
                    .signedUserId(signedUserId)
                    .name(user.getName())
                    .email(authenticationCode.getEmail())
                    .tell(user.getTell())
                    .birth(user.getBirth())
                    .profilePic(user.getProfilePic())
                    .providerType(user.getProviderType())
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

        // 사용자 정보 조회
        User user = userRepository.findById(signedUserId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND)); // 사용자 없을 경우 예외 처리

        // 이메일 인증 코드 조회
        AuthenticationCode authenticationCode = authenticationCodeRepository
                .findByAuthenticatedId(user.getAuthenticationCode().getAuthenticatedId())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND)); // 인증 코드가 없으면 예외 처리


        String targetDir = "user/" + req.getSignedUserId();
        myFileUtils.makeFolders(targetDir);

        String deletePath = String.format("%s/user/%s", myFileUtils.getUploadPath(), req.getSignedUserId());

        if (profilePic != null && !profilePic.isEmpty()) {
            String savedFileName = myFileUtils.makeRandomFileName(profilePic);
            String getExt = myFileUtils.getExt(savedFileName);
            myFileUtils.deleteFolder(deletePath, false);

            String filePath = String.format("%s/%s", targetDir, savedFileName);
            try {
                myFileUtils.convertAndSaveToWebp(profilePic, filePath.replaceAll("\\.[^.]+$", ".webp"));
                user.setProfilePic(savedFileName.replace(getExt, ".webp"));
            } catch (IOException e) {
                throw new RuntimeException("프로필 사진 저장에 실패했습니다.", e);
            }
        }

        // 전화번호(tell) 및 생일(birth) 업데이트
        if (req.getTell() != null && !req.getTell().equals(user.getTell())) {
            user.setTell(req.getTell()); // 전화번호 업데이트
        }

        if (req.getBirth() != null && !req.getBirth().equals(user.getBirth())) {
            user.setBirth(req.getBirth()); // 생일 업데이트
        }

        // 이름 업데이트
        user.setName(req.getName());

        // 사용자 정보 저장
        userRepository.save(user); // 엔티티 저장(수정)

        return UserUpdateRes.builder()
                .signedUserId(signedUserId)
                .profilePic(user.getProfilePic())
                .name(user.getName())
                .tell(user.getTell())   // 수정된 전화번호 반환
                .birth(user.getBirth()) // 수정된 생일 반환
                .build();
    }

    //-------------------------------------------------
    // 계정 비밀번호 변경
    public void changePassword(ChangePasswordReq req) {
        long signedUserId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(signedUserId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(req.getPw(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        if (req.getNewPw().equals(req.getPw())) {
            throw new IllegalArgumentException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        String hashedPassword = passwordEncoder.encode(req.getNewPw());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    //-------------------------------------------------
    // 임시 비밀번호
    @Transactional
    public int temporaryPw(TemporaryPwDto temporaryPwDto) {
        User user = userRepository.findByAuthenticationCode_Email(temporaryPwDto.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        temporaryPwDto.setUserId(user.getUserId());

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
        String tmpPasswordOriginal = new String(passwordArray);
        String hashedPassword = passwordEncoder.encode(tmpPasswordOriginal);

        // TemporaryPw가 이미 존재하는지 확인
        Optional<TemporaryPw> existingTemporaryPw = temporaryPwRepository.findByUserId(user.getUserId());

        TemporaryPw temporaryPw;
        if (existingTemporaryPw.isPresent()) {
            // 기존 임시 비밀번호가 있으면 업데이트
            temporaryPw = existingTemporaryPw.get();
            temporaryPw.setTemporaryPassword(hashedPassword); // 임시 비밀번호만 업데이트
            temporaryPwRepository.save(temporaryPw);
        } else {
            // 임시 비밀번호가 없으면 새로 생성
            temporaryPw = new TemporaryPw();
            temporaryPw.setUser(user);
            temporaryPw.setTemporaryPassword(hashedPassword);
            temporaryPwRepository.save(temporaryPw);
        }

        user.setPassword(hashedPassword);
        userRepository.save(user);

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

        return 1;
    }

    public boolean checkTempPassword(String email) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // User의 userId로 임시 비밀번호 조회
        Optional<TemporaryPw> optionalTempPw = temporaryPwRepository.findByUserId(user.getUserId());

        // 임시 비밀번호가 없으면 false 반환
        if (optionalTempPw.isEmpty()) {
            return false;
        }

        TemporaryPw temporaryPw = optionalTempPw.get();

        // 비밀번호와 임시 비밀번호 비교
        String pw = user.getPassword(); // 원본 비밀번호
        String tpPw = temporaryPw.getTemporaryPassword(); // 임시 비밀번호

        // 비밀번호가 같으면 true 반환
        return pw != null && pw.equals(tpPw);
    }
}