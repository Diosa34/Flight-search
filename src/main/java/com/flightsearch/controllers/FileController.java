package com.flightsearch.controllers;

import com.flightsearch.repositories.FileRepository;
import com.flightsearch.schemas.file_info.FileInfoCreate;
import com.flightsearch.schemas.file_info.FileInfoRead;
import com.flightsearch.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@RestController
@Profile({"prodMain", "devMain"})
@RequestMapping("/files")
@Validated
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/upload/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileInfoRead saveFile(
            @RequestParam("file") MultipartFile file, FileInfoCreate schema) {
        return fileService.saveFile(file, schema);
    }

    @GetMapping
    public List<FileInfoRead> getFiles() {
        return fileService.getFileInfos();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable UUID id) {
        FileRepository.FileResource fileResource = fileService.loadFile(id);
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
