package com.flightsearch.schemas.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentMetaUpdate {
    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 512)
    private String description;
}
