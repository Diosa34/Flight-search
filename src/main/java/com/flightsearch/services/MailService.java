package com.flightsearch.services;

import com.flightsearch.config.properties.MailProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile({"prodMain", "devMain"})
@AllArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    public String getAddress(String email) {
        if (mailProperties.getRedirectTo() != null && !mailProperties.getRedirectTo().isBlank()) {
            return mailProperties.getRedirectTo();
        } else {
            return email;
        }
    }

    public void sendSimpleEmail(String to, String title, String message) {
        if (!mailProperties.getDisable()) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("doc.management24@gmail.com");
            simpleMailMessage.setTo(getAddress(to));
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(message);
            emailSender.send(simpleMailMessage);
        }
        if (mailProperties.getLog()) {
            log.info("Сформировано письмо: \n\tTo: {}\n\tSubject: {}\n\tBody: {}", getAddress(to), title, message);
        }
    }
}