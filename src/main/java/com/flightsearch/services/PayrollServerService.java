package com.flightsearch.services;

import com.flightsearch.models.*;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.jms.PayrollCreate;
import com.flightsearch.services.generators.PayrollFileGeneratorService;
import com.flightsearch.services.generators.SignFileGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Profile({"devPayroll", "prodPayroll"})
@AllArgsConstructor
public class PayrollServerService {
    /**
     * Сервис обработки сообщений из очереди,
     * для включения необходимо использовать профиль devPayroll/prodPayroll.
     */
    private final PayrollFileGeneratorService payrollFileGeneratorService;
    private final SignFileGeneratorService signFileGeneratorService;
    private final DocumentRepository documentRepository;
    private final SignRepository signRepository;

    private void createConfirmedSign(String userId, Document document) {
        Sign sign = new Sign();
        sign.setUserId(userId);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
        sign.setDocument(document);
        FileInfo signFile = signFileGeneratorService.generateSignFile(sign);
        sign.setFile(signFile);
        signRepository.save(sign);
    }

    public void createPayroll(PayrollCreate schema) {
        String userId = schema.getUserId();
        String authorId = schema.getAuthorId();
        if (userId == null || authorId == null) {
            return;
        }
        Document document = new Document();
        FileInfo payrollFile = payrollFileGeneratorService.generatePayrollFile(userId);
        document.setFile(payrollFile);
        document.setOwnerId(authorId);
        document.setDescription("Платежная ведомость");
        document.setTitle("Платежная ведомость");
        document = documentRepository.save(document);
        createConfirmedSign(authorId, document);
    }
}