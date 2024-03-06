package com.flightsearch.schemas.document;

import com.flightsearch.models.Document;
import com.flightsearch.schemas.ModelSchema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class DocumentBase implements ModelSchema<Document> {
    @Length(max = 50)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long ownerId;

    @Override
    public Document toModel() {
        Document doc = new Document();
        doc.setOwnerId(this.ownerId);
        doc.setTitle(this.title);
        doc.setContent(this.content);
        return doc;
    }

    @Override
    public void fromModel(Document document) {
        this.title = document.getTitle();
        this.content = document.getContent();
        this.ownerId = document.getOwnerId();
    }

    @Override
    public void updateModel(Document document) {
        document.setTitle(this.title);
        document.setContent(this.content);
        document.setOwnerId(this.ownerId);
    }
}
