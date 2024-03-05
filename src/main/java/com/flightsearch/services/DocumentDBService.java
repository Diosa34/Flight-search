package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Document;
import com.flightsearch.models.User;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.schemas.ModelSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Document> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        return user.get();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
