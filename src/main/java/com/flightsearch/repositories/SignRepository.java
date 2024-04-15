package com.flightsearch.repositories;

import com.flightsearch.models.Sign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignRepository extends JpaRepository<Sign, Long> {
    Optional<Sign> findById(Long id);
}
