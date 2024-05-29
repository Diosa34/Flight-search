package com.flightsearch.schemas.document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DocumentBase {
    @Schema(example = "Результаты проверки.")
    @NotBlank
    @Size(max = 50)
    private String title;

    @Schema(example = "Очень важные результаты проверки.")
    @NotBlank
    @Size(max = 512)
    private String description;

    @NotNull
    private UUID fileId;
}
