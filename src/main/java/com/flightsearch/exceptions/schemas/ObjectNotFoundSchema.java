package com.flightsearch.exceptions.schemas;

import com.flightsearch.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectNotFoundSchema {
    @Schema(example = "<EntityName> с идентификатором <EntityID> не найден")
    private String cause;
    @Schema(example = "EntityName")
    private String entityName;

    public ObjectNotFoundSchema(NotFoundException exception) {
        this.setCause(exception.getEntityName() + " с идентификатором " + exception.getEntityId() + " не найден");
        this.entityName = exception.getEntityName();
    }
}
