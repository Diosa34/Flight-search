package com.flightsearch.schemas.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignRead extends SignBase {
    @Schema(example = "1")
    private Long id;
    private Boolean isCounterpartSigned;
    private Timestamp submitTime;
}