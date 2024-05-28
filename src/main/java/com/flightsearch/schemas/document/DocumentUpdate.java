package com.flightsearch.schemas.document;

import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DocumentUpdate extends DocumentBase {
    @Size(min = 1)
    Set<SignCreate> signs;
}
