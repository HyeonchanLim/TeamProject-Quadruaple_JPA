package com.green.project_quadruaple.user.mail;

import com.green.project_quadruaple.common.model.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("mail")
@RequiredArgsConstructor
@Tag(name = "메일 인증 코드 발송, 확인")
public class MailController {

    private final MailService mailService;

    @GetMapping
    @Operation(summary = "인증 메일 발송")
    public ResultResponse sendMail(@RequestParam @Valid String email) {
        log.info("Send mail to " + email);
        return mailService.send(email);
    }

    @PostMapping
    @Operation(summary = "인증 코드 확인")
    public ResultResponse checkMail(@Valid @RequestBody GetEmailAndCodeReq req) {
        return mailService.check(req);
    }
}
