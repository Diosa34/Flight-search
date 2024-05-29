package com.flightsearch.services;

import com.flightsearch.models.*;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final PayrollFileGeneratorService payrollFileGeneratorService;
    private final SignFileGeneratorService signFileGeneratorService;
    private final DocumentRepository documentRepository;
    private final SignRepository signRepository;

    private void createConfirmedSign(User user, Document document) {
        Sign sign = new Sign();
        sign.setCounterpart(user);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
        sign.setDocument(document);
        FileInfo signFile = signFileGeneratorService.generateSignFile(sign);
        sign.setFile(signFile);
        signRepository.save(sign);
    }

    public void createPayroll(PayrollCreate schema) {
        User user = userRepository.findById(schema.getUserId()).orElse(null);
        User author = userRepository.findById(schema.getAuthorId()).orElse(null);
        if (user == null || author == null) {
            return;
        }
        Document document = new Document();
        FileInfo payrollFile = payrollFileGeneratorService.generatePayrollFile(user);
        document.setFile(payrollFile);
        document.setOwner(author);
        document.setDescription("Платежная ведомость");
        document.setTitle("Платежная ведомость");
        document = documentRepository.save(document);
        createConfirmedSign(author, document);
    }
}
