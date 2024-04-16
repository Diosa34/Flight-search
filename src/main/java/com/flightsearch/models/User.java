package com.flightsearch.models;

import ch.qos.logback.classic.encoder.JsonEncoder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "counterpart")
    private List<Sign> signs = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Document> documents = new ArrayList<>();

    public void setPassword(String password) {
        this.passwordHash = new BCryptPasswordEncoder().encode(password);
    }
}