package com.flightsearch.exceptions.schemas;

import com.flightsearch.exceptions.NotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ObjectNotFoundSchema extends BaseResponse {
    private String entityName;
    public ObjectNotFoundSchema(NotFoundException exception) {
        this.setCause(exception.getEntityName() + " с идентификатором " + exception.getEntityId() + " не найден");
        this.entityName = exception.getEntityName();
    }
}
