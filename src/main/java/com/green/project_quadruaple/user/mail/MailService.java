package com.green.project_quadruaple.user.mail;

import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.user.AuthenticationCodeRepository;
import com.green.project_quadruaple.user.UserRepository;
import com.green.project_quadruaple.user.mail.thread.AuthCode;
import com.green.project_quadruaple.user.mail.thread.MailCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private final JavaMailSender mailSender;

    private final UserRepository userRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;

    public static Map<String, String> codes = new HashMap<>();
    public static Map<String, Boolean> mailChecked = new HashMap<>();


    public ResultResponse send(String email) {
        // 인증코드 생성
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append((int)(Math.random() * 10));
        }
        String codeStr = code.toString();

        // 생성된 코드 DB에 저장
        AuthenticationCode authCode = AuthenticationCode.builder()
                .email(email)
                .codeNum(codeStr)
                .grantedAt(LocalDateTime.now())
                .build();
        authenticationCodeRepository.save(authCode);

        MailService.codes.put(email, codeStr);
        // 인증코드 유지 시간 설정
        new Thread(new AuthCode(email)).start();

        try {
            // HTML 이메일 본문 생성
            String body = """
            <!DOCTYPE html>
            <html lang=\"ko\">
            <head>
              <meta charset=\"UTF-8\" />
              <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />
              <title>이메일 인증 코드</title>
            </head>
            <body style=\"margin: 0; padding: 0; font-family: 'Arial', sans-serif; background-color: #f9f9f9;\">
            <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"max-width: 600px; background-color: #ffffff; border-collapse: collapse;\">
              <tr>
                <td align=\"center\" style=\"padding: 20px; border-bottom: 3px solid #0dd1fd;\">
                  <h1 style=\"font-size: 24px; color: #000000; margin: 0;\">
                    이메일 <span style=\"color: #02aed5;\">인증 코드</span> 안내
                  </h1>
                </td>
              </tr>
              <tr>
                <td align=\"center\" style=\"padding: 20px; color: #616161; font-size: 14px; line-height: 1.6;\">
                  <p>안녕하세요. QUADRUPLE 이메일 인증을 위한 메일입니다.</p>
                  <p>아래 인증 코드를 입력창에 입력하고 이메일 인증을 완료해 주세요.</p>
                </td>
              </tr>
              <tr>
                <td align=\"center\" style=\"padding: 20px;\">
                  <div style=\"display: inline-block; background-color: rgba(148, 221, 255, 0.47); color: #02aed5; font-size: 28px; font-weight: bold; padding: 15px 20px; border-radius: 8px;\">
                    """ + codes.get(email) + """
                  </div>
                </td>
              </tr>
              <tr>
                <td align=\"center\" style=\"padding: 20px; color: #616161; font-size: 12px; line-height: 1.4;\">
                  <p>• 위 내용을 요청하지 않았는데 본 메일을 받으셨다면 고객 센터에 문의해 주세요.</p>
                </td>
              </tr>
              <tr>
                <td align=\"center\" style=\"padding: 20px; font-size: 10px; color: #9e9e9e; line-height: 1.4;\">
                  <p>본 메일은 QUADRUPLE에서 발송한 메일이며 발신전용입니다. 관련 문의 사항은 고객센터로 연락주시기 바랍니다.</p>
                  <p>© 2024 QUADRUPLE. All rights reserved.</p>
                </td>
              </tr>
            </table>
            </body>
            </html>
            """;

            MailHandler mailHandler = new MailHandler(mailSender);
            mailHandler.setFrom("quadrupleart@gmail.com", "quadruple");
            mailHandler.setTo(email);
            mailHandler.setSubject("quadruple 인증 코드");
            mailHandler.setText(body, true);  // HTML 형식으로 전송
            mailHandler.send();

        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.severError();
        }
        return ResultResponse.success();
    }

    public ResultResponse check(GetEmailAndCodeReq req) {
        String email = req.getEmail();
        String code = req.getCode();

        /// 이메일과 인증 코드로 AuthenticationCode 조회
        boolean isValidCode = authenticationCodeRepository.existsByEmailAndCodeNum(email, code);

        if (!isValidCode) {
            return new ResultResponse("FAIL");
        }

        mailChecked.put(email, true);
        new Thread(new MailCheck(email)).start();

        return ResultResponse.success();
    }
}
