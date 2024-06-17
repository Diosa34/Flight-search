package com.flightsearch.schemas.document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SignBase {
    @NotNull
    @Positive
    private Long counterpartId;
}
