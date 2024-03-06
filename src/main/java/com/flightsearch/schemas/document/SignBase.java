package com.flightsearch.schemas.document;

import com.flightsearch.models.Sign;
import com.flightsearch.schemas.ModelSchema;
import jakarta.validation.constraints.NotNull;

public class SignBase implements ModelSchema<Sign> {
    @NotNull
    private Long counterpartId;

    @NotNull
    private Long documentId;


    @Override
    public Sign toModel() {
        Sign sign = new Sign();
        sign.setDocumentId(this.documentId);
        sign.setCounterpartId(this.counterpartId);
        return sign;
    }

    @Override
    public void fromModel(Sign sign) {
        this.documentId = sign.getDocumentId();
        this.counterpartId = sign.getCounterpartId();
    }

    @Override
    public void updateModel(Sign sign) {
        sign.setDocumentId(this.documentId);
        sign.setCounterpartId(this.counterpartId);
    }
}
