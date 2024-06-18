package com.flightsearch.controllers;

import com.flightsearch.repositories.FileRepository;
import com.flightsearch.services.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile({"prodMain", "devMain"})
@RequestMapping("/document")
@Validated
@AllArgsConstructor
public class DocumentController {
    private final DocumentService docService;

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
}
