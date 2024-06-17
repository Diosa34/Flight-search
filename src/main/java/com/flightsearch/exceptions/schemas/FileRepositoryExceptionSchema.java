package com.flightsearch.exceptions.schemas;

import com.flightsearch.exceptions.repositories.FileRepositoryException;
import lombok.Data;

@Data
public class FileRepositoryExceptionSchema {
    private String cause;
    private String fullPath;

    public FileRepositoryExceptionSchema(FileRepositoryException fileRepositoryException) {
        cause = fileRepositoryException.getMessage();
        fullPath = fileRepositoryException.getFullPath();
    }
}
