package com.flightsearch.services;

import com.flightsearch.config.properties.MailProperties;
import com.flightsearch.models.User;
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

    public String getAddress(User user) {
        if (mailProperties.getRedirectTo() != null && !mailProperties.getRedirectTo().isBlank()) {
            return mailProperties.getRedirectTo();
        } else {
            return user.getEmail();
        }
    }

    public void sendSimpleEmail(User user, String title, String message) {
        if (!mailProperties.getDisable()) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("doc.management24@gmail.com");
            simpleMailMessage.setTo(getAddress(user));
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(message);
            emailSender.send(simpleMailMessage);
        }
        if (mailProperties.getLog()) {
            log.info("Сформировано письмо: \n\tTo: {}\n\tSubject: {}\n\tBody: {}", getAddress(user), title, message);
        }
    }
}