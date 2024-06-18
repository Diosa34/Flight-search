package com.flightsearch.controllers.jms;

import com.flightsearch.schemas.jms.PayrollCreate;
import com.flightsearch.services.PayrollServerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"devPayroll", "prodPayroll"})
@AllArgsConstructor
public class JmsPayrollController {
    private PayrollServerService payrollService;

    @JmsListener(destination = "payroll")
    public void createPayroll(PayrollCreate schema) {
        log.info("Получен запрос на создание payroll от user({}) для user({})",
                schema.getAuthorId(), schema.getUserId());
        payrollService.createPayroll(schema);
    }
}