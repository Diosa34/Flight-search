package com.flightsearch.repositories;

import com.flightsearch.models.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {
    Optional<FileInfo> findById(UUID id);
}
