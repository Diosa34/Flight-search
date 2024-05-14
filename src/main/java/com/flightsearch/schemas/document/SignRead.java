package com.flightsearch.schemas.document;

import com.flightsearch.models.Document;
import com.flightsearch.models.SignStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignRead extends SignBase {
    @Schema(example = "1")
    private Long id;
    private Document document;
    private SignStatus signStatus;
    private Timestamp submitTime;
    private UUID fileId;
}
