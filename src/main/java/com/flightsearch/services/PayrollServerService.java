package com.flightsearch.services;

import com.flightsearch.models.*;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.FileRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.repositories.UserRepository;
import com.flightsearch.schemas.jms.PayrollCreate;
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
     * */
    private UserRepository userRepository;
    private DocumentRepository documentRepository;
    private FileRepository fileRepository;
    private SignRepository signRepository;


    private FileInfo createPayrollFile(User user) {
        return fileRepository.saveFile(
                "payrolls",
                "payroll.txt",
                "Платежная ведомость на имя " + user.getFullName());
    }

    private void createConfirmedSign(User user, Document document) {
        Sign sign = new Sign();
        FileInfo fileInfo = fileRepository.saveFile(
                "signs",
                "sign-" + user.getId() + ".txt",
                String.format("Документ %s подписан пользоваетелем %s",
                        document.getTitle(),
                        user.getFullName())
        );
        sign.setCounterpart(user);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
        sign.setFile(fileInfo);
        sign.setDocument(document);
        signRepository.save(sign);
    }

    public void createPayroll(PayrollCreate schema) {
        User user = userRepository.findById(schema.getUserId()).orElse(null);
        User author = userRepository.findById(schema.getAuthorId()).orElse(null);
        if (user == null || author == null) {
            return;
        }
        Document document = new Document();
        document.setFile(createPayrollFile(user));
        document.setOwner(author);
        document.setDescription("Платежная ведомость");
        document.setTitle("Платежная ведомость");
        document = documentRepository.save(document);
        createConfirmedSign(author, document);
    }
}
