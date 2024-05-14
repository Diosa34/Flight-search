package com.flightsearch.quartz;

import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.models.User;
import com.flightsearch.schemas.document.DocumentRead;
import com.flightsearch.schemas.document.SignRead;
import com.flightsearch.schemas.user.UserRead;
import com.flightsearch.services.DocumentService;
import com.flightsearch.services.SecurityService;
import com.flightsearch.services.SignService;
import com.flightsearch.services.UserService;
import com.flightsearch.services.mapping.DocumentMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Component
public class CheckSignDeadlineJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private SignService signService;

    public void execute(JobExecutionContext context) {
        logger.info("Started execution of CheckSignDeadlineJob with context = {}", context);

        List<UserRead> allUsers = userService.getAll();

        for (UserRead currentUser: allUsers) {
            Set<SignRead> userSigns = signService.getSignsByCounterpartId(currentUser.getId());

            for (SignRead sign: userSigns) {
                SignStatus signStatus = sign.getSignStatus();
                Document doc = sign.getDocument();

                long delta = Math.abs(doc.getDeadline().getTime() - new Timestamp(System.currentTimeMillis()).getTime()) / 86400000;

                logger.info("Для пользователя с адресом почты {} осталось {} до дедлайна (в днях)", currentUser.getEmail(), delta);
                if (delta < 3 && signStatus == SignStatus.ON_HOLD) {
                    signService.setStatus(sign.getId(), SignStatus.THREE_DAYS_LEFT);
                }
                if (delta < 0 && signStatus == SignStatus.THREE_DAYS_LEFT) {
                    signService.setStatus(sign.getId(), SignStatus.MISSED_DEADLINE);
                }
            }
        }


        logger.info("Finished execution of CheckSignDeadlineJob");
    }
}
