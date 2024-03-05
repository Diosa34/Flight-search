package com.flightsearch.repositories;

import com.flightsearch.models.Sign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignRepository extends JpaRepository<Sign, Long> {
}
