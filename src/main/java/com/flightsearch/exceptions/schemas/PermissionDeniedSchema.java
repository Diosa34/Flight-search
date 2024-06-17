package com.flightsearch.exceptions.schemas;

import com.flightsearch.exceptions.PermissionDeniedException;
import lombok.Data;

@Data
public class PermissionDeniedSchema {
    private String cause;
    private String requiredRole;

    public PermissionDeniedSchema(PermissionDeniedException exception) {
        cause = exception.getMessage();
        requiredRole = exception.getRequiredRole();
    }
}
