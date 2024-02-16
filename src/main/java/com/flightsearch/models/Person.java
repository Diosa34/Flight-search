package com.flightsearch.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "person")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String surname;
    private String patronymic;
    private Gender gender;
    private Date dateOfBirth;
    private String country;
    private DocumentType documentType;
    private String documentNumber;
    private String telephone;
    private String email;
    private String login;
    private String passwordHash;
}

enum Gender {
    MALE,
    FEMALE
}

enum DocumentType {
    PASSPORT,
    FOREIGN_PASSPORT,
    MILITARY_TICKET
}