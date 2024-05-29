package com.flightsearch.exceptions.schemas;

import com.flightsearch.exceptions.PermissionDeniedException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDeniedSchema {
    @Schema(example = "Отказано в доступе")
    private String cause;
    @Schema(example = "ADMIN")
    private String requiredRole;

    public PermissionDeniedSchema(PermissionDeniedException exception) {
        cause = exception.getMessage();
        requiredRole = exception.getRequiredRole();
    }
}
