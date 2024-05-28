package com.flightsearch.services;

import com.flightsearch.schemas.jms.PayrollCreate;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PayrollService {
    private JmsTemplate jmsTemplate;
    private SecurityService securityService;

    public void createPayroll(Long id) {
        PayrollCreate schema = new PayrollCreate(id, securityService.getCurrentUser().getId());
        jmsTemplate.convertAndSend("payroll", schema);
    }
}
