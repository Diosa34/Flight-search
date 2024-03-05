package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Sign;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.ModelSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignDBService {
    @Autowired
    SignRepository repository;

    public Sign save(ModelSchema<Sign> signSchema) {
        return repository.save(signSchema.toModel());
    }

    // добавление нового контрагента
    public Sign save(Sign sign) {
        return repository.save(sign);
    }

    public Sign findById(Long id) {
        Optional<Sign> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        return user.get();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
