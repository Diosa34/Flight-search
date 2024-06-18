package com.flightsearch.services.generators;

import com.flightsearch.models.Document;
import com.flightsearch.models.FileInfo;
import com.flightsearch.models.Sign;
import com.flightsearch.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignFileGeneratorService {
    private final FileRepository fileRepository;

    private String generateSignFileContent(Sign sign, Document doc) {
        return String.format("Документ %s подписан пользоваетелем %s",
                doc.getTitle(),
                sign.getUserId()
        );
    }

    public FileInfo generateSignFile(Sign sign, Document doc) {
        return fileRepository.saveFile(
                "signs",
                "sign-" + sign.getUserId() + ".txt",
                generateSignFileContent(sign, doc)
        );
    }

    public FileInfo generateSignFile(Sign sign) {
        if (sign.getDocument() == null) {
            throw new NullPointerException("Sign.document is null. Give document entity or saved sign entity.");
        }
        return generateSignFile(sign, sign.getDocument());
    }
}
