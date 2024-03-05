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
    private Long proc_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", referencedColumnName = "doc_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counterpart_id", referencedColumnName = "id")
    private User counterpart;

    @Column(nullable = false)
    private Boolean is_counterpart_signed;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp counterpart_signed_timestamp;
}
