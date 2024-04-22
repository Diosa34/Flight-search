package com.flightsearch.exceptions.repositories;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileRepositoryException extends Error {
    final private String message;
    final private String fullPath;
}
