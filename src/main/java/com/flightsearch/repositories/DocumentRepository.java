package com.flightsearch.repositories;

import com.flightsearch.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByTitle(String title);

    Optional<Document> findByOwnerId(Long ownerId);

    Optional<Document> findByIsSigned(Boolean isSigned);

    Optional<Document> findByCreationDate(Timestamp creationDate);
}
