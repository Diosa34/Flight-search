package com.flightsearch.schemas.sign;

import com.flightsearch.models.Sign;
import com.flightsearch.schemas.ModelSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class SignBase implements ModelSchema<Sign> {
    @Schema(description = "ИД подписываемого документа")
    @NotNull
    private Long documentId;

    @Schema(description = "ИД подписывающей стороны. Подписывающая сторона может являться владельцем.")
    @NotNull
    private Long counterpartId;

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
