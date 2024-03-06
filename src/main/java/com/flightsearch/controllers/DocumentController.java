package com.flightsearch.controllers;

import com.flightsearch.models.Document;
import com.flightsearch.schemas.document.DocumentBase;
import com.flightsearch.schemas.document.DocumentCreate;
import com.flightsearch.schemas.document.DocumentOut;
import com.flightsearch.services.DocumentDBService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/document")
@Validated
public class DocumentController {
    @Autowired
    private DocumentDBService documentDB;

    @GetMapping
    public List<Document> findAll() {
        return documentDB.findAll();
    }

    @GetMapping("/{id}")
    public DocumentOut findById(@PathVariable Long id) {
        DocumentOut schema = new DocumentOut();
        schema.fromModel(documentDB.findById(id));
        return schema;
    }

    @GetMapping("/")
    public DocumentOut findByTitle(@RequestParam String title) {
        DocumentOut schema = new DocumentOut();
        schema.fromModel(documentDB.findByTitle(title));
        return schema;
    }

    @GetMapping("/")
    public DocumentOut findByOwnerId(@RequestParam Long id) {
        DocumentOut schema = new DocumentOut();
        schema.fromModel(documentDB.findByOwnerId(id));
        return schema;
    }

    @GetMapping("/")
    public DocumentOut findByIsSigned(@RequestParam Boolean isSigned) {
        DocumentOut schema = new DocumentOut();
        schema.fromModel(documentDB.findByIsSigned(isSigned));
        return schema;
    }

    @GetMapping("/")
    public DocumentOut findByCreationDate(@RequestParam Timestamp creationDate) {
        DocumentOut schema = new DocumentOut();
        schema.fromModel(documentDB.findByCreationDate(creationDate));
        return schema;
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    @Operation(
            summary = "Создание документа"
    )
    public DocumentOut create(@RequestBody @Valid DocumentCreate document) {
        DocumentOut schema = new DocumentOut();
        schema.fromModel(documentDB.save(document));
        return schema;
    }

    @PutMapping("/{id}")
    public DocumentOut update(@PathVariable Long id, @RequestBody @Valid DocumentBase documentData) {
        Document document = documentDB.findById(id);
        documentData.updateModel(document);
        DocumentOut schema = new DocumentOut();
        schema.fromModel(documentDB.save(documentData));
        return schema;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        documentDB.deleteById(id);
    }
}
