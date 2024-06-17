package com.flightsearch.exceptions.schemas;

import com.flightsearch.exceptions.NotFoundException;
import lombok.Data;

@Data
public class ObjectNotFoundSchema {
    private String cause;
    private String entityName;

    public ObjectNotFoundSchema(NotFoundException exception) {
        this.setCause(exception.getEntityName() + " с идентификатором " + exception.getEntityId() + " не найден");
        this.entityName = exception.getEntityName();
    }
}
