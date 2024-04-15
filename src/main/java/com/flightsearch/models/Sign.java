package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "counterpart_id") // todo: после настройки идентификации добавить nullable = false
    private User counterpart;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;


    @Column(nullable = false)
    private Boolean isCounterpartSigned = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp submitTime;
}
