package com.flightsearch.schemas.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class DocumentBase {
    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 512)
    private String description;

    @NotNull
    private UUID fileId;

    private Timestamp deadline;
}
