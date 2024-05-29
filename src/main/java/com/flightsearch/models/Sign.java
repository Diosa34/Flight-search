package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "counterpart_id", nullable = false)
    private User counterpart;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileInfo file = null;


    @Column(nullable = false)
    private SignStatus signStatus = SignStatus.ON_HOLD;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp submitTime;
}
