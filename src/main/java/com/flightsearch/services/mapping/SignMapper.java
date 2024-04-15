package com.flightsearch.services.mapping;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Sign;
import com.flightsearch.repositories.UserRepository;
import com.flightsearch.schemas.document.SignBase;
import com.flightsearch.schemas.document.SignCreate;
import com.flightsearch.schemas.document.SignRead;
import org.springframework.stereotype.Service;

@Service
public class SignMapper {
    private final UserRepository userRepository;

    public SignMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected Sign mapSignBaseToEntity(SignBase schema, Sign entity) {
        entity.setCounterpart(
                userRepository.findById(schema.getCounterpartId()).orElseThrow(NotFoundException::new)
        );
        return entity;
    }

    protected Sign mapSignBaseToEntity(SignBase schema) {
        return mapSignBaseToEntity(schema, new Sign());
    }

    public Sign mapSignCreateToEntity(SignCreate schema) {
        return mapSignBaseToEntity(schema);
    }

    public SignRead mapEntityToSignRead(Sign entity) {
        SignRead schema = new SignRead();
        schema.setId(
                entity.getId());
        schema.setCounterpartId(
                entity.getCounterpart().getId());
        schema.setIsCounterpartSigned(
                entity.getIsCounterpartSigned());
        schema.setSubmitTime(
                entity.getSubmitTime());
        return schema;
    }
}
