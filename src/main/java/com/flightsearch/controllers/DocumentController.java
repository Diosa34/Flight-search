package com.flightsearch.controllers;

import com.flightsearch.schemas.document.DocumentBase;
import com.flightsearch.services.DocumentDBService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document")
@Validated
public class DocumentController {
    @Autowired
    DocumentDBService documentDB;

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    @Operation(
            summary = "Создать документ",
            description = "Создает документ и добавляет бенефициаров"
    )
    public DocumentBase create(@RequestBody @Valid DocumentBase document) {
        DocumentBase schema = new DocumentBase();
        schema.fromModel(documentDB.save(document));
        return schema;
    }
}
