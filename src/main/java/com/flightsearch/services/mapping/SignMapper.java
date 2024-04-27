package com.flightsearch.services.mapping;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Sign;
import com.flightsearch.repositories.UserRepository;
import com.flightsearch.schemas.document.SignBase;
import com.flightsearch.schemas.document.SignCreate;
import com.flightsearch.schemas.document.SignRead;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignMapper {
    private final UserRepository userRepository;

    protected Sign mapSignBaseToEntity(SignBase schema, Sign entity) {
        entity.setCounterpart(
                userRepository.findById(schema.getCounterpartId()).orElseThrow(
                        () -> new NotFoundException(schema.getCounterpartId(), "User")
                )
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
        schema.setSignStatus(
                entity.getSignStatus());
        schema.setSubmitTime(
                entity.getSubmitTime());
        if (entity.getFile() != null) {
            schema.setFileId(
                    entity.getFile().getId());
        }
        return schema;
    }
}
