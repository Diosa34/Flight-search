package com.flightsearch.services.delegates;

import com.flightsearch.schemas.jms.PayrollCreate;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class PayrollDelegate implements JavaDelegate {
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> recipientIds = Arrays.asList(
                ((String) delegateExecution.getVariable("recipientIds")).split(",")
        );
        recipientIds.forEach(recipientId -> {
            PayrollCreate schema = new PayrollCreate(recipientId, getCurrentUser(delegateExecution));
            jmsTemplate.convertAndSend("payroll", schema);
        });
    }

    private String getCurrentUser(DelegateExecution delegateExecution) {
        return delegateExecution.getProcessEngineServices()
                .getIdentityService().getCurrentAuthentication().getUserId();
    }
}