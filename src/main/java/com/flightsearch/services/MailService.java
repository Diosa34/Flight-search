package com.flightsearch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile({"prodMain", "devMain"})
public class MailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleEmail(String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("superalex.osa@yandex.ru");
        simpleMailMessage.setTo("superalex.osa@yandex.ru");
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }
}