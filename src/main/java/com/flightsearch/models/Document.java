package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "document", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Sign> signs;

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileInfo file;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());
}
