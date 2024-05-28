package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Document;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.document.*;
import com.flightsearch.services.mapping.DocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"prodMain", "devMain"})
@RequiredArgsConstructor
public class DocumentService {
    final DocumentRepository docRepository;
    final SignRepository signRepository;
    final DocumentMapper docMapper;
    final SecurityService securityService;

    public Set<DocumentRead> getAll() {
        return docRepository.findAll().stream()
                .map(docMapper::mapEntityToDocumentRead)
                .collect(Collectors.toSet());
    }

    @Transactional
    public DocumentRead create(DocumentCreate schema) {
        Document newDoc = docMapper.mapDocumentCreateToEntity(schema);
        newDoc.setOwner(securityService.getCurrentUser());
        docRepository.save(newDoc);
        signRepository.saveAll(newDoc.getSigns());
        return docMapper.mapEntityToDocumentRead(newDoc);
    }

    @Transactional
    public DocumentRead update(Long id, DocumentUpdate schema) {
        Document doc = docRepository.getReferenceById(id);
        securityService.userRequired(doc.getOwner());
        docMapper.mapAndUpdateEntity(schema, doc);
        signRepository.saveAll(doc.getSigns());
        return docMapper.mapEntityToDocumentRead(doc);
    }

    public DocumentRead updateMeta(Long id, DocumentMetaUpdate schema) {
        Document doc = docRepository.getReferenceById(id);
        securityService.userRequired(doc.getOwner());
        doc.setTitle(schema.getTitle());
        doc.setDescription(schema.getDescription());
        doc = docRepository.save(doc);
        return docMapper.mapEntityToDocumentRead(doc);
    }

    public DocumentRead getById(Long id) {
        Document doc = docRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Document")
        );
        return docMapper.mapEntityToDocumentRead(
                doc
        );
    }

    public void delete(Long documentId) {
        Document doc = docRepository.findById(documentId).orElseThrow(
                () -> new NotFoundException(documentId, "Document")
        );
        securityService.userRequired(doc.getOwner());
        docRepository.delete(doc);
    }
}
