package com.flightsearch.services;

import com.flightsearch.schemas.jms.PayrollCreate;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile({"prodMain", "devMain"})
@AllArgsConstructor
public class PayrollClientService {
    /**
     * Сервис для отправки сообщений в очередь,
     * для включения необходимо использовать профиль prodMain/devMain.
     */
    private final JmsTemplate jmsTemplate;
    private final SecurityService securityService;

    public void createPayroll(Long id) {
        PayrollCreate schema = new PayrollCreate(id, securityService.getCurrentUser().getId());
        jmsTemplate.convertAndSend("payroll", schema);
    }
}
