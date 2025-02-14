package com.green.project_quadruaple.user.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MailHandler {
    private final JavaMailSender sender;
    private final MimeMessage message;
    private final MimeMessageHelper messageHelper;

    // 생성자
    public MailHandler(JavaMailSender jSender) throws MessagingException {
        this.sender = jSender;
        message = jSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    // 송신자 이메일 추가
    public void setFrom(String fromAddress, String name) throws MessagingException, UnsupportedEncodingException {
        messageHelper.setFrom(fromAddress, name);
    }

    // 수신자 이메일 추가
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    // 제목 추가
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    // 메일 내용 추가
    public void setText(String text, boolean useHtml) throws MessagingException {
        messageHelper.setText(text, useHtml);
    }

    // 첨부 파일 추가
    public void setAttach(String displayFileName, String pathToAttachment) throws MessagingException, IOException {
        File file = new File(ResourceUtils.getFile(pathToAttachment).getAbsolutePath());
        FileSystemResource fsr = new FileSystemResource(file);

        messageHelper.addAttachment(displayFileName, fsr);
    }

    // 이메일 발송
    public void send() throws Exception {
        try {
            sender.send(message);
        }catch(Exception e) {
            throw new Exception(e);
        }
    }
}
