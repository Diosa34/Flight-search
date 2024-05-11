package com.flightsearch.controllers;

import com.flightsearch.repositories.FileRepository;
import com.flightsearch.schemas.document.*;
import com.flightsearch.services.DocumentService;
import com.flightsearch.services.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(
    name = "Документооборот",
    description = "В данном разделе находятся описание методов для работы с документами и подписями."
)
@RestController
@RequestMapping("/document")
@Validated
public class DocumentController {
    private final DocumentService docService;
    private final SignService signService;

    public DocumentController(SignService signService, DocumentService docService) {
        this.signService = signService;
        this.docService = docService;
    }

    @Operation(
            summary = "Создать документ",
            description = "Создает документ и добавляет объекты неподтвержденных подписей."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DocumentRead createDocument(@RequestBody @Valid DocumentCreate schema) {
        return docService.create(schema);
    }

    @Operation(
            summary = "Список документов",
            description = "Возвращает список всех документов."
    )
    @GetMapping
    public Set<DocumentRead> getAll() {
        return docService.getAll();
    }

    @Operation(
            summary = "Возвращает документ",
            description = "Возвращает информацию о документе и подписях."
    )
    @GetMapping("/{id}")
    public DocumentRead getDocument(@PathVariable Long id) {
        return docService.getById(id);
    }

    @Operation(
            summary = "Обновить документ",
            description = "Обновляет документ и сбрасывает подтвержденные подписи, " +
                    "то есть документ становится не подписанным."
    )
    @PutMapping("/{id}")
    public DocumentRead updateDocument(@PathVariable Long id, @RequestBody @Valid DocumentUpdate schema) {
        return docService.update(id, schema);
    }

    @Operation(
            summary = "Обновить название документа",
            description = "Обновляет справочные данные документа без сброса подтверждения подписей."
    )
    @PatchMapping("/{id}")
    public DocumentRead updateDocumentMeta(@PathVariable Long id, @RequestBody @Valid DocumentMetaUpdate schema) {
        return docService.updateMeta(id, schema);
    }

    @Operation(
            summary = "Удалить документ",
            description = "Удаляет документ и связанные с ним подписи."
    )
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id) {
        docService.delete(id);
    }

    @Operation
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        FileRepository.FileResource fileResource = docService.getDocumentsFileResource(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileResource.getFilename() + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileResource.getLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileResource.getResource());
    }

    @Operation(
            summary = "Подтверждает подпись",
            description = "Подписывает документ от имени пользователя."
    )
    @PostMapping("/sign/{signId}/confirm")
    public SignRead confirmSign(@PathVariable Long signId) {
        return signService.confirm(signId);
    }

    @Operation(
            summary = "Отменяет подпись",
            description = "Отменяет подпись документа от имени пользователя."
    )
    @DeleteMapping("/sign/{signId}/reject")
    public SignRead rejectSign(@PathVariable Long signId) {
        return signService.reject(signId);
    }

    @Operation(
            summary = "Удаляет подпись",
            description = "Удаляет объект подписи и лишает возможности подписания документа для пользователя, связанного с этим объектом."
    )
    @DeleteMapping("/sign/{signId}")
    public void deleteSign(@PathVariable Long signId) {
        signService.delete(signId);
    }
}
