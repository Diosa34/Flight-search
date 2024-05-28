package com.flightsearch.schemas.document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignBase {
    @Schema(description = "Заинтересованная сторона", example = "1")
    @NotNull
    @Positive
    private Long counterpartId;
}
