package com.green.project_quadruaple.user.mail.thread;

import com.green.project_quadruaple.user.mail.MailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthCode implements Runnable {
    private final String email;

    public AuthCode(String email) {
        this.email = email;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(180_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MailService.codes.remove(email);
    }
}