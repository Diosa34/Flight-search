package com.flightsearch.services.delegates;

import com.flightsearch.config.properties.SchedulerProperties;
import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.services.MailService;
import com.flightsearch.utils.datetime.TimestampDelta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationDelegate implements JavaDelegate {
    SignRepository signRepository;
    MailService mailService;
    SchedulerProperties schedulerProperties;

    private String getEmail(DelegateExecution delegateExecution, String userId) {
        User user = delegateExecution.getProcessEngineServices().getIdentityService()
                .createUserQuery().userId(userId).singleResult();
        return user != null ? user.getEmail() : null;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        for (Sign sign : signRepository.findAllBySignStatus(SignStatus.ON_HOLD)) {
            Document doc = sign.getDocument();
            TimestampDelta timeDelta = new TimestampDelta(doc.getDeadline());
            String email = getEmail(delegateExecution, sign.getUserId());

            log.info("Для пользователя {} с адресом почты {} осталось {} до дедлайна (в днях)",
                    sign.getUserId(), email, timeDelta.days());
            if (email == null) {
                continue;
            }
            if (timeDelta.days() <= 0) {
                sign.setSignStatus(SignStatus.MISSED_DEADLINE);
                signRepository.save(sign);
                mailService.sendSimpleEmail(
                        email,
                        "Истёк срок подписания",
                        String.format("Cрок подписания документа «%s» истёк.", doc.getTitle())
                );
            } else if (timeDelta.days() <= schedulerProperties.getSecondNotification()) {
                mailService.sendSimpleEmail(
                        email,
                        "Истекает срок подписания",
                        String.format("Времени остается совсем немного. До окончания срока подписания документа «%s» осталось менее %s дней.", doc.getTitle(), schedulerProperties.getSecondNotification())
                );
            } else if (timeDelta.days() <= schedulerProperties.getFirstNotification()) {
                mailService.sendSimpleEmail(
                        email,
                        "Истекает срок подписания",
                        String.format("До окончания срока подписания документа «%s» осталось менее %s дней.", doc.getTitle(), schedulerProperties.getFirstNotification())
                );
            }
        }
    }
}
