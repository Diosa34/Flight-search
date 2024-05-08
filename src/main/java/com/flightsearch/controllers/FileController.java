package com.flightsearch.controllers;

import com.flightsearch.schemas.file_info.FileInfoCreate;
import com.flightsearch.schemas.file_info.FileInfoRead;
import com.flightsearch.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "files")
@RestController
@RequestMapping("/files")
@Validated
@AllArgsConstructor
public class FileController {
    final private FileService fileService;

    @Operation(summary = "Загрузить файл")
    @PostMapping(value = "/upload/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileInfoRead saveFile(
            @RequestParam("file") MultipartFile file, @ParameterObject FileInfoCreate schema) {
        return fileService.saveFile(file, schema);
    }

    @Operation(summary = "Список файлов")
    @GetMapping
    public List<FileInfoRead> getFiles() {
        return fileService.getFileInfos();
    }
}
