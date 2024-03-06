package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.ModelSchema;
import com.flightsearch.schemas.document.DocumentCreate;
import com.flightsearch.schemas.document.SignCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentDBService {
    @Autowired
    DocumentRepository docRepo;

    @Autowired
    SignRepository signRepo;


    public Document create(DocumentCreate schema) {
        Document newDoc = docRepo.save(schema.toModel());
        Long currDocId = newDoc.getDocId();
        for (SignCreate signSchema : schema.getSigns()) {
            Sign sign = signSchema.toModel();
            sign.setDocumentId(currDocId);
            signRepo.save(sign);
        }
        return newDoc;
    }

    public Document save(ModelSchema<Document> docSchema) {
        return docRepo.save(docSchema.toModel());
    }

    public Document save(Document document) {
        return docRepo.save(document);
    }

    public List<Document> findAll() {
        return docRepo.findAll();
    }

    public Document findById(Long id) {
        Optional<Document> document = docRepo.findById(id);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByTitle(String title) {
        Optional<Document> document = docRepo.findByTitle(title);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByOwnerId(Long ownerId) {
        Optional<Document> document = docRepo.findByOwnerId(ownerId);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByIsSigned(Boolean isSigned) {
        Optional<Document> document = docRepo.findByIsSigned(isSigned);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByCreationDate(Timestamp creationDate) {
        Optional<Document> document = docRepo.findByCreationDate(creationDate);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public void deleteById(Long id) {
        docRepo.deleteById(id);
    }
}
