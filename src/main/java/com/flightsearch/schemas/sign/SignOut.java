package com.flightsearch.schemas.sign;

import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import com.flightsearch.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Timestamp;

public class SignOut extends SignBase{
    @NotNull
    @Positive
    private Long signId;

    @Schema(description = "Информация о документе")
    @NotNull
    private Document document;

    @Schema(description = "Информация о подписывающей стороне")
    @NotNull
    private User counterpart;

    @Schema(description = "Подписан ли документ соответствующей стороной")
    @NotNull
    private Boolean isCounterpartSigned;

    @Schema(description = "Время подписания документа соответствующей стороной")
    private Timestamp counterpartSignedTimestamp;

    @Override
    public void fromModel(Sign model) {
        super.fromModel(model);
        this.signId = model.getSignId();
        this.document = model.getDocument();
        this.counterpart = model.getCounterpart();
        this.isCounterpartSigned = model.getIsCounterpartSigned();
        this.counterpartSignedTimestamp = model.getCounterpartSignedTimestamp();
    }

    @Override
    public void updateModel(Sign model) {
        super.updateModel(model);
        model.setSignId(this.signId);
        model.setDocument(this.document);
        model.setCounterpart(this.counterpart);
        model.setIsCounterpartSigned(this.isCounterpartSigned);
        model.setCounterpartSignedTimestamp(this.counterpartSignedTimestamp);
    }
}
