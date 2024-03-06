package com.flightsearch.schemas.document;

import com.flightsearch.models.Sign;
import com.flightsearch.schemas.ModelSchema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignBase implements ModelSchema<Sign> {
    @NotNull
    private Long counterpartId;

    @Override
    public Sign toModel() {
        Sign sign = new Sign();
        sign.setCounterpartId(this.counterpartId);
        return sign;
    }

    @Override
    public void fromModel(Sign sign) {
        this.counterpartId = sign.getCounterpartId();
    }

    @Override
    public void updateModel(Sign sign) {
        sign.setCounterpartId(this.counterpartId);
    }
}
