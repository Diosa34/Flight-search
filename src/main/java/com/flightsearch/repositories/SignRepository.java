package com.flightsearch.repositories;

import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SignRepository extends JpaRepository<Sign, Long> {
    Optional<Sign> findById(Long id);

    List<Sign> findAllBySignStatus(SignStatus signStatus);
}
