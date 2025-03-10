package com.green.project_quadruaple.busi;

import com.green.project_quadruaple.busi.model.BusiPostReq;
import com.green.project_quadruaple.busi.model.BusiUserInfoDto;
import com.green.project_quadruaple.busi.model.BusiUserSignIn;
import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.CookieUtils;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.config.security.SignInProviderType;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.strf.BusinessNumRepository;
import com.green.project_quadruaple.strf.StrfRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusiService {
    private final RoleRepository roleRepository;
    private final BusiRepository busiRepository;
    private final BusinessNumRepository businessNumRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyFileUtils myFileUtils;
    private final TokenProvider jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final JavaMailSender javaMailSender;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final TemporaryPwRepository temporaryPwRepository;
    private final StrfRepository strfRepository;

    @Value("${spring.mail.username}")
    private static String FROM_ADDRESS;

    @Value("${file.directory}")
    private String fileDirectory;

//     회원가입
    public int busiSignUp(MultipartFile pic, BusiPostReq p) {
        if (userRepository.existsByAuthenticationCode_Email(p.getEmail())) {
            return 0;
        }

        if (userRepository.existsByName(p.getName())) {
            return 0;
        }
        if (busiRepository.existsById(p.getBusiNum())){
            return 0;
        }
        String hashedPassword = passwordEncoder.encode(p.getPw());
        p.setPw(hashedPassword);

        String savedPicName;
        boolean isDefaultPic = false;

        if (pic != null && !pic.isEmpty()) {
            savedPicName = myFileUtils.makeRandomFileName(pic);
        } else {
            savedPicName = "user_profile.png";
            isDefaultPic = true;
        }
        try {
            AuthenticationCode authCode = authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(p.getEmail())
                    .orElseThrow(() -> new RuntimeException("이메일 인증이 완료되지 않았습니다."));

            String email = authCode.getEmail();

            BusinessNum businessNum = new BusinessNum();

            User user = new User();
            user.setAuthenticationCode(authCode);
            user.setName(p.getName());
            user.setProfilePic(savedPicName);
            user.setPassword(hashedPassword);
            user.setBirth(p.getBirth());
            user.setTell(p.getTell());
            user.setProviderType(SignInProviderType.LOCAL);
            user.setVerified(1);

            businessNum.setBusiNum(p.getBusiNum());
            businessNum.setUser(user);

            userRepository.save(user);

            busiRepository.save(businessNum);

            if (user != null) {
                long userId = user.getUserId();
                p.setUserId(userId);

                Role role = Role.builder()
                        .id(new RoleId(UserRole.BUSI.getValue(), userId))
                        .user(user)
                        .role(UserRole.BUSI)
                        .grantedAt(LocalDateTime.now())
                        .build();
                roleRepository.save(role);

                String middlePath = String.format("user/%s", userId);
                myFileUtils.makeFolders(middlePath);
                String filePath = String.format("%s/%s", middlePath, savedPicName);
                Path destination = Paths.get(fileDirectory, filePath);

                try {
                    if (isDefaultPic) {
                        Path source = Paths.get(fileDirectory, "common", "user_profile.png");

                        System.out.println("Source Path: " + source.toAbsolutePath());
                        System.out.println("Destination Path: " + destination.toAbsolutePath());

                        if (!Files.exists(source)) {
                            System.out.println("기본 프로필 사진이 존재하지 않습니다!");
                        } else {
                            System.out.println("기본 프로필 사진이 존재합니다.");
                        }

                        Files.createDirectories(destination.getParent());

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

    public BusiUserInfoDto BusiUserInfo() {
        try {
            long signedUserId = authenticationFacade.getSignedUserId();

            User user = userRepository.findById(signedUserId)
                    .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
            String businessNum = businessNumRepository.findBusinessNumByUserId(user.getUserId());

            StayTourRestaurFest strf = strfRepository.findByCategory(businessNum).orElseThrow( () -> new RuntimeException("businessNum not found"));

            String categoryValue = (strf.getCategory() != null) ? strf.getCategory().name() : null;

            AuthenticationCode authenticationCode = authenticationCodeRepository
                    .findByAuthenticatedId(user.getAuthenticationCode().getAuthenticatedId()) // authenticatedId로 이메일 조회
                    .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND)); // 인증 코드가 없으면 예외 처리

            return BusiUserInfoDto.builder()
                    .signedUserId(signedUserId)
                    .name(user.getName())
                    .email(authenticationCode.getEmail())
                    .tell(user.getTell())
                    .birth(user.getBirth())
                    .profilePic(user.getProfilePic())
                    .providerType(user.getProviderType())
                    .busiNum(businessNum)
                    .category(categoryValue)
                    .build();
        } catch (ExpiredJwtException e) {
            throw new CustomException(UserErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new CustomException(UserErrorCode.INVALID_TOKEN);
        }
    }

    @Transactional
    public UserSignInRes signIn(BusiUserSignIn req, HttpServletResponse response) {
        User user = userRepository.findByAuthenticationCode_EmailAndProviderType(req.getEmail(), SignInProviderType.LOCAL);

        if (user == null) {
            throw new RuntimeException("아이디를 확인해 주세요.");
        }
        if (!passwordEncoder.matches(req.getPw(), user.getPassword())) {
            throw new RuntimeException("비밀번호를 확인해 주세요.");
        }
        Optional<AuthenticationCode> authCode = authenticationCodeRepository.findFirstByEmailOrderByGrantedAtDesc(user.getAuthenticationCode().getEmail());
        if (authCode.isEmpty() || user.getVerified() == 0) {
            throw new RuntimeException("이메일 인증이 필요합니다.");
        }

        // 사용자 역할 조회
        List<Role> roleEntities = roleRepository.findByUserUserId(user.getUserId());
        List<UserRole> roles = roleEntities.stream()
                .map(role -> UserRole.getKeyByName(role.getRole().getName()))
                .collect(Collectors.toList());

        // StayTourRestaurFest에서 userId로 사업자 정보 조회
        StayTourRestaurFest strf = strfRepository.findByUserId(user.getUserId()).orElse(null);

        // JWT 토큰 생성
        JwtUser jwtUser = new JwtUser(user.getUserId(), roles);
        String accessToken = jwtTokenProvider.generateToken(jwtUser, Duration.ofHours(6));
        String refreshToken = jwtTokenProvider.generateToken(jwtUser, Duration.ofDays(15));

        // RefreshToken 쿠키 설정
        int maxAge = 1_296_000;
        cookieUtils.setCookie(response, "refreshToken", refreshToken, maxAge, "/api/user/access-token");

        // UserSignInRes에 사업자 정보 포함
        return UserSignInRes.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .name(user.getName())
                .roles(roles)
                .busiNum(strf != null ? strf.getBusiNum().getBusiNum(): null)  // 사업자 번호
                .strfId(strf != null ? strf.getStrfId() : null)  // strf_id
                .title(strf != null ? strf.getTitle() : null)  // title
                .category(strf != null ? strf.getCategory().getName() : null) // category
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
        return MailService.mailChecked.getOrDefault(email, false);
    }

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

        tmpPasswordBuilder.append(upperCaseLetters[random.nextInt(upperCaseLetters.length)]);
        tmpPasswordBuilder.append(numbers[random.nextInt(numbers.length)]);
        tmpPasswordBuilder.append(specialCharacters[random.nextInt(specialCharacters.length)]);

        for (int i = 0; i < 5; i++) {
            tmpPasswordBuilder.append(allCharacters[random.nextInt(allCharacters.length)]);
        }

        char[] passwordArray = tmpPasswordBuilder.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        String tmpPasswordOriginal = new String(passwordArray);
        String hashedPassword = passwordEncoder.encode(tmpPasswordOriginal);

        Optional<TemporaryPw> existingTemporaryPw = temporaryPwRepository.findByUserId(user.getUserId());

        TemporaryPw temporaryPw;
        if (existingTemporaryPw.isPresent()) {
            temporaryPw = existingTemporaryPw.get();
            temporaryPw.setTemporaryPassword(hashedPassword);
            temporaryPwRepository.save(temporaryPw);
        } else {
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        Optional<TemporaryPw> optionalTempPw = temporaryPwRepository.findByUserId(user.getUserId());

        if (optionalTempPw.isEmpty()) {
            return false;
        }

        TemporaryPw temporaryPw = optionalTempPw.get();

        String pw = user.getPassword();
        String tpPw = temporaryPw.getTemporaryPassword();

        return pw != null && pw.equals(tpPw);
    }
}
