package com.flightsearch.services.delegates;

import com.flightsearch.models.FileInfo;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.SignSchema;
import com.flightsearch.services.generators.SignFileGeneratorService;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class SignDelegate implements JavaDelegate {
    SignRepository signRepository;
    SignFileGeneratorService signFileGeneratorService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if ((boolean) delegateExecution.getVariable("answer")) {
            confirm(delegateExecution);
        } else {
            reject(delegateExecution);
        }
    }

    private void confirm(DelegateExecution delegateExecution) {
        SignSchema schema = (SignSchema) delegateExecution.getVariable("sign");
        Sign sign = signRepository.findById(schema.getSignId()).orElseThrow(
                () -> new BpmnError("NotFound"));
        if (sign.getSignStatus() == SignStatus.MISSED_DEADLINE) {
            throw new BpmnError("DeadlineError");
        }
        FileInfo signFile = signFileGeneratorService.generateSignFile(sign);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
        sign.setFile(signFile);
        signRepository.save(sign);
    }

    private void reject(DelegateExecution delegateExecution) {
        SignSchema schema = (SignSchema) delegateExecution.getVariable("sign");
        Sign sign = signRepository.findById(schema.getSignId()).orElseThrow(
                () -> new BpmnError("NotFound"));
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.REJECTED);
        sign.setFile(null);
        signRepository.save(sign);
    }
}
