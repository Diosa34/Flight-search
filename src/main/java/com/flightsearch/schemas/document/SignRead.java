package com.flightsearch.schemas.document;

import com.flightsearch.models.SignStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignRead extends SignBase {
    private Long id;
    private SignStatus signStatus;
    private Timestamp submitTime;
    private UUID fileId;
}
