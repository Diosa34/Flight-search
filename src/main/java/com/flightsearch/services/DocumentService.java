package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Document;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.document.*;
import com.flightsearch.services.mapping.DocumentMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    final DocumentRepository docRepository;

    final SignRepository signRepository;

    final DocumentMapper docMapper;

    public DocumentService(DocumentRepository docRepository, DocumentMapper docMapper, SignRepository signRepository) {
        this.docRepository = docRepository;
        this.signRepository = signRepository;
        this.docMapper = docMapper;
    }


    public Set<DocumentRead> getAll() {
        return docRepository.findAll().stream()
                .map(docMapper::mapEntityToDocumentRead)
                .collect(Collectors.toSet());
    }

    public DocumentRead create(DocumentCreate schema) {
        Document newDoc = docMapper.mapDocumentCreateToEntity(schema);
        docRepository.save(newDoc);
        signRepository.saveAll(newDoc.getSigns());
        return docMapper.mapEntityToDocumentRead(newDoc);
    }

    public DocumentRead update(Long id, DocumentUpdate schema) {
        Document doc = docRepository.getReferenceById(id);
        docMapper.mapAndUpdateEntity(schema, doc);
        signRepository.saveAll(doc.getSigns());
        return docMapper.mapEntityToDocumentRead(doc);
    }

    public DocumentRead updateMeta(Long id, DocumentMetaUpdate schema) {
        Document doc = docRepository.getReferenceById(id);
        doc.setTitle(schema.getTitle());
        doc.setDescription(schema.getDescription());
        doc = docRepository.save(doc);
        return docMapper.mapEntityToDocumentRead(doc);
    }

    public DocumentRead getById(Long id) {
        Document doc = docRepository.findById(id).orElseThrow(NotFoundException::new);
        return docMapper.mapEntityToDocumentRead(
                doc
        );
    }

    public void delete(Long documentId) {
        Document doc = docRepository.findById(documentId).orElseThrow(NotFoundException::new);
        docRepository.delete(doc);
    }
}
