package com.flightsearch.schemas.jms;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PayrollCreate implements Serializable {
    private Long userId; // id получателя
    private Long authorId; // id создателя документа
}
