package com.flightsearch.schemas.document;

import com.flightsearch.schemas.sign.SignCreate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentCreate extends DocumentBase {
    private Set<SignCreate> signs;

}
