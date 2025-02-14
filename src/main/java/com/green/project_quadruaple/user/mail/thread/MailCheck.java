package com.green.project_quadruaple.user.mail.thread;

import com.green.project_quadruaple.user.mail.MailService;

public class MailCheck implements Runnable {

    private final String email;

    public MailCheck(String email) {
        this.email = email;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(600_000);
            MailService.mailChecked.remove(email);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
