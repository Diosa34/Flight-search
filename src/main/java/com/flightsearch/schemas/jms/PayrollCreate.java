package com.flightsearch.schemas.jms;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PayrollCreate implements Serializable {
    private String userId; // id получателя
    private String authorId; // id создателя документа
}