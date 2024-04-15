package com.flightsearch.schemas.document;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentCreate extends DocumentBase {
    @Size(min = 1)
    Set<SignCreate> signs;
}
