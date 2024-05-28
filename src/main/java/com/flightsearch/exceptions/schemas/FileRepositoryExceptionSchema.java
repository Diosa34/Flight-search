package com.flightsearch.exceptions.schemas;

import com.flightsearch.exceptions.repositories.FileRepositoryException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRepositoryExceptionSchema {
    @Schema(example = "Не удалось сохранить файл на сервере")
    private String cause;
    @Schema(example = "/application-repositories/files/dir/xxx.txt")
    private String fullPath;

    public FileRepositoryExceptionSchema(FileRepositoryException fileRepositoryException) {
        cause = fileRepositoryException.getMessage();
        fullPath = fileRepositoryException.getFullPath();
    }
}
