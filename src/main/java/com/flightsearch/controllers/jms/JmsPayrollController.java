package com.flightsearch.controllers.jms;

import com.flightsearch.schemas.jms.PayrollCreate;
import com.flightsearch.services.PayrollServerService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Profile({"devPayroll", "prodPayroll"})
@AllArgsConstructor
public class JmsPayrollController {
    private PayrollServerService payrollService;

    @JmsListener(destination = "payroll")
    public void createPayroll(PayrollCreate schema) {
        System.out.println("Получена запись payroll от " + schema.getAuthorId() + " для " + schema.getUserId());
        payrollService.createPayroll(schema);
    }
}
