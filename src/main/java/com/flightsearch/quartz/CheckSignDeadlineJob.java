package com.flightsearch.quartz;

import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.user.UserRead;
import com.flightsearch.services.MailService;
import com.flightsearch.services.SignService;
import com.flightsearch.services.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Component
@Profile({"prodMain", "devMain"})
public class CheckSignDeadlineJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final SignService signService;
    private final SignRepository signRepository;
    private final MailService mailService;

    public CheckSignDeadlineJob(UserService userService, SignService signService, MailService mailService, SignRepository signRepository) {
        this.userService = userService;
        this.signService = signService;
        this.mailService = mailService;
        this.signRepository = signRepository;
    }

    @Transactional
    public void execute(JobExecutionContext context) {
        logger.info("Started execution of CheckSignDeadlineJob with context = {}", context);

        List<UserRead> allUsers = userService.getAll();

        for (UserRead currentUser : allUsers) {
            List<Sign> userSigns = signRepository.findAllByCounterpartId(currentUser.getId());

            for (Sign sign : userSigns) {
                SignStatus signStatus = sign.getSignStatus();
                Document doc = sign.getDocument();

                long deadlineTime = doc.getDeadline().getTime();
                long nowTime = new Timestamp(System.currentTimeMillis()).getTime();
                long delta = (deadlineTime - nowTime) / 86400000;

                logger.info("Для пользователя с адресом почты {} осталось {} до дедлайна (в днях)", currentUser.getEmail(), delta);
                if (delta <= 0 && signStatus != SignStatus.MISSED_DEADLINE) {
                    signService.setStatus(sign.getId(), SignStatus.MISSED_DEADLINE);
                    mailService.sendSimpleEmail(String.format("Cрок подписания документа «%s» истёк.", doc.getTitle()));
                } else if (delta < 3 && signStatus == SignStatus.ON_HOLD) {
                    signService.setStatus(sign.getId(), SignStatus.THREE_DAYS_LEFT);
                    mailService.sendSimpleEmail(String.format("До окончания срока подписания документа «%s» осталось менее 3 дней.", doc.getTitle()));
                } else if (signStatus == SignStatus.MISSED_DEADLINE) {
                    mailService.sendSimpleEmail(String.format("Cрок подписания документа «%s» истёк.", doc.getTitle()));
                }
            }
        }

        logger.info("Finished execution of CheckSignDeadlineJob");
    }
}
