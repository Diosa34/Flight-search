package com.flightsearch.services.mapping;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Document;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.FileInfoRepository;
import com.flightsearch.schemas.document.DocumentBase;
import com.flightsearch.schemas.document.DocumentCreate;
import com.flightsearch.schemas.document.DocumentRead;
import com.flightsearch.schemas.document.DocumentUpdate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocumentMapper {
    private final SignMapper signMapper;
    private final UserMapper userMapper;
    private final FileInfoRepository fileInfoRepository;

    protected Document mapDocumentBaseToEntity(DocumentBase schema, Document entity) {
        entity.setTitle(schema.getTitle());
        entity.setDescription(schema.getDescription());
        entity.setFile(
                fileInfoRepository.findById(schema.getFileId()).orElseThrow(
                        () -> new NotFoundException(schema.getFileId(), "FileInfo")
                )
        );
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
                        .collect(Collectors.toList())
        );
        entity.getSigns().forEach(sign -> sign.setDocument(entity));
        return entity;
    }

    public void mapAndUpdateEntity(DocumentUpdate schema, Document entity) {
        entity.setTitle(schema.getTitle());
        entity.setDescription(schema.getDescription());
        entity.setFile(
                fileInfoRepository.findById(schema.getFileId()).orElseThrow(
                        () -> new NotFoundException(schema.getFileId(), "FileInfo")
                )
        );
        entity.setSigns(
                schema.getSigns().stream()
                        .map(signMapper::mapSignCreateToEntity)
                        .collect(Collectors.toList())
        );
        entity.getSigns().forEach(sign -> sign.setDocument(entity));
    }

    public DocumentRead mapEntityToDocumentRead(Document entity) {
        DocumentRead schema = new DocumentRead();
        schema.setId(entity.getId());
        schema.setOwner(
                userMapper.mapEntityToUserRead(entity.getOwner())
        );
        schema.setSigns(
                entity.getSigns().stream()
                        .map(signMapper::mapEntityToSignRead)
                        .collect(Collectors.toSet())
        );
        schema.setTitle(entity.getTitle());
        schema.setDescription(entity.getDescription());
        schema.setFileId(entity.getFile().getId());
        schema.setCreationDate(entity.getCreationDate());
        schema.setIsSigned(entity.getSigns().stream()
                .map(Sign::getSignStatus)
                .map(signStatus -> signStatus == SignStatus.CONFIRMED)
                .reduce(Boolean::logicalAnd).orElse(false));
        return schema;
    }
}
