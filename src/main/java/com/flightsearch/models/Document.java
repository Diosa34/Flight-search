package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Set<Sign> signs;


    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String description;

    @Column(length = 256, nullable = false)
    private String key;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private Boolean isSigned = false;
}
