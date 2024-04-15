package com.flightsearch.services.mapping;

import com.flightsearch.models.Document;
import com.flightsearch.schemas.document.DocumentBase;
import com.flightsearch.schemas.document.DocumentCreate;
import com.flightsearch.schemas.document.DocumentRead;
import com.flightsearch.schemas.document.DocumentUpdate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DocumentMapper {
    private final SignMapper signMapper;

    public DocumentMapper(SignMapper signMapper) {
        this.signMapper = signMapper;
    }

    protected Document mapDocumentBaseToEntity(DocumentBase schema, Document entity) {
        entity.setTitle(schema.getTitle());
        entity.setDescription(schema.getDescription());
        entity.setKey(schema.getKey());
        return entity;
    }

    protected Document mapDocumentBaseToEntity(DocumentBase schema) {
        return mapDocumentBaseToEntity(schema, new Document());
    }

    public Document mapDocumentCreateToEntity(DocumentCreate schema) {
        Document entity = mapDocumentBaseToEntity(schema);
        entity.setSigns(
                schema.getSigns().stream()
                        .map(signMapper::mapSignCreateToEntity)
                        .collect(Collectors.toSet())
        );
        return entity;
    }

    public void mapAndUpdateEntity(DocumentUpdate schema, Document entity) {
        entity.setTitle(schema.getTitle());
        entity.setDescription(schema.getDescription());
        entity.setKey(schema.getKey());
        entity.setSigns(
                schema.getSigns().stream()
                        .map(signMapper::mapSignCreateToEntity)
                        .collect(Collectors.toSet())
        );
    }

    public DocumentRead mapEntityToDocumentRead(Document entity) {
        DocumentRead schema = new DocumentRead();
        schema.setId(entity.getId());
        schema.setSigns(
                entity.getSigns().stream()
                        .map(signMapper::mapEntityToSignRead)
                        .collect(Collectors.toSet())
        );
        schema.setTitle(entity.getTitle());
        schema.setDescription(entity.getDescription());
        schema.setKey(entity.getKey());
        schema.setCreationDate(entity.getCreationDate());
        schema.setIsSigned(entity.getIsSigned());
        return schema;
    }
}
