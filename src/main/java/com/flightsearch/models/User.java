package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "person")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String surname;

    @Column(length = 30)
    private String patronymic;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date dateOfBirth;

    @Column(length = 30, nullable = false, unique = true)
    private String telephone;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String passwordHash;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<Document> documents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Sign> sign_requests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "counterpart")
    private List<Sign> sign_responses;

    public void setPassword(String password) {
        this.passwordHash = password;
    }
}