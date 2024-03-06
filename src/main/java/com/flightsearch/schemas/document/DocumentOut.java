package com.flightsearch.schemas.document;

import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentOut extends DocumentBase {
    private Timestamp creationDate;
    private Long ownerId;
    private Set<SignOut> signs;
    private Boolean isSigned;

    @Override
    public void fromModel(Document document) {
        super.fromModel(document);
        this.creationDate = document.getCreationDate();
        this.ownerId = document.getOwnerId();
        this.isSigned = document.getIsSigned();
        this.signs = document.getSign().stream().map((sign) -> {
            SignOut signOut = new SignOut();
            signOut.fromModel(sign);
            return signOut;
        }).collect(Collectors.toSet());
    }

    @Override
    public void updateModel(Document document) {
        super.updateModel(document);
    }
}
