package com.flightsearch.repositories;

import com.flightsearch.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
