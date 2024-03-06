package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sign")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Sign {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long procId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", referencedColumnName = "docId", insertable = false, updatable = false)
    private Document document;

    @Column(name = "doc_id")
    private Long documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counterpart_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User counterpart;

    @Column(name = "counterpart_id")
    private Long counterpartId;

    @Column(nullable = false)
    private Boolean isCounterpartSigned = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp counterpartSignedTimestamp;
}
