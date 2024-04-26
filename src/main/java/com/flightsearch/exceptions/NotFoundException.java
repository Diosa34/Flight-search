package com.flightsearch.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс ошибки, возникающей при отсутствии записи в базе данных.
 * */
@AllArgsConstructor
@Getter
public class NotFoundException extends Error {
    private Object entityId;
    private String entityName;
}
