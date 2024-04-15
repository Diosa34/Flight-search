package com.flightsearch.schemas.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentRead extends DocumentBase {
    @Schema(example = "1")
    private Long id;

    Set<SignRead> signs;

    private Timestamp creationDate;
    private Boolean isSigned;
}
