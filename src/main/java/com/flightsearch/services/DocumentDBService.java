package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Document;
import com.flightsearch.models.User;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.schemas.ModelSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentDBService {
    @Autowired
    DocumentRepository repository;

    public Document save(ModelSchema<Document> docSchema) {
        return repository.save(docSchema.toModel());
    }

    public Document save(Document document) {
        return repository.save(document);
    }

    public List<Document> findAll() {
        return repository.findAll();
    }

    public Document findById(Long id) {
        Optional<Document> document = repository.findById(id);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByTitle(String title) {
        Optional<Document> document = repository.findByTitle(title);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByOwnerId(Long ownerId) {
        Optional<Document> document = repository.findByOwnerId(ownerId);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByIsSigned(Boolean isSigned) {
        Optional<Document> document = repository.findByIsSigned(isSigned);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public Document findByCreationDate(Timestamp creationDate) {
        Optional<Document> document = repository.findByCreationDate(creationDate);
        if (document.isEmpty()) {
            throw new NotFoundException();
        }
        return document.get();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
