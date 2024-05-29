package com.flightsearch.quartz;

import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.services.MailService;
import com.flightsearch.services.SignService;
import com.flightsearch.utils.datetime.TimestampDelta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile({"prodMain", "devMain"})
@AllArgsConstructor
public class CheckSignDeadlineJob implements Job {
    private final SignService signService;
    private final SignRepository signRepository;
    private final MailService mailService;

    @Transactional
    public void execute(JobExecutionContext context) {
        log.info("Started execution of CheckSignDeadlineJob with context = {}", context);

        for (Sign sign : signRepository.findAllBySignStatus(SignStatus.ON_HOLD)) {
            Document doc = sign.getDocument();
            TimestampDelta timeDelta = new TimestampDelta(doc.getDeadline());

            log.info("Для пользователя с адресом почты {} осталось {} до дедлайна (в днях)", sign.getCounterpart().getEmail(), timeDelta.days());
            if (timeDelta.days() <= 0) {
                signService.setStatus(sign.getId(), SignStatus.MISSED_DEADLINE);
                mailService.sendSimpleEmail(
                        sign.getCounterpart(),
                        "Истёк срок подписания",
                        String.format("Cрок подписания документа «%s» истёк.", doc.getTitle())
                );
            } else if (timeDelta.days() <= 3) {
                mailService.sendSimpleEmail(
                        sign.getCounterpart(),
                        "Истекает срок подписания",
                        String.format("До окончания срока подписания документа «%s» осталось менее 3 дней.", doc.getTitle())
                );
            }
        }

        log.info("Finished execution of CheckSignDeadlineJob");
    }
}
