package com.flightsearch.schemas.document;

import com.flightsearch.models.Document;
import com.flightsearch.schemas.ModelSchema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class DocumentBase implements ModelSchema<Document> {
    @Length(max = 50)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Override
    public Document toModel() {
        Document doc = new Document();
        doc.setTitle(this.title);
        doc.setContent(this.content);
        return doc;
    }

    @Override
    public void fromModel(Document document) {
        this.title = document.getTitle();
        this.content = document.getContent();
    }

    @Override
    public void updateModel(Document document) {
        document.setTitle(this.title);
        document.setContent(this.content);
    }
}
