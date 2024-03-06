package com.flightsearch.schemas.document;

import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentCreate extends DocumentBase {
    private Set<SignCreate> signs;

}
