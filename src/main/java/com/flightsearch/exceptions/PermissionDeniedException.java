package com.flightsearch.exceptions;

import lombok.Getter;

@Getter
public class PermissionDeniedException extends Error {
    private final String requiredRole;
    private final String message;

    public PermissionDeniedException(String requiredRole) {
        this.message = "Отказано в доступе";
        this.requiredRole = requiredRole;
    }

    public PermissionDeniedException(String message, String requiredRole) {
        this.message = message;
        this.requiredRole = requiredRole;
    }
}
