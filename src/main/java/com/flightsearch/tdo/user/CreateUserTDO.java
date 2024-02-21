package com.flightsearch.tdo.user;

import com.flightsearch.models.DocumentType;
import com.flightsearch.models.Gender;
import com.flightsearch.models.User;
import com.flightsearch.tdo.TDO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Schema(description = "Схема регистрации")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserTDO implements TDO<User> {
    @Schema(description = "Имя", example = "Иван")
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    @Schema(description = "Фамилия", example = "Иванов")
    private String surname;

    @Null
    @Size(min = 3, max = 30)
    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;

    @Null
    @Schema(description = "Пол", example = "MALE")
    private Gender gender;

    @Null
    @Schema(description = "Дата рождения")
    private Date dateOfBirth;

    @Null
    @Size(min = 3, max = 255)
    private String country;

    @Null
    @Schema(description = "Тип документа")
    private DocumentType documentType;

    @Null
    @Size(min = 1, max = 30)
    private String documentNumber;

    @NotBlank
    @Size(max = 30)
    private String telephone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 30)
    private String login;

    @NotBlank
    @Size(max = 30)
    private String password;

    public User toModel() {
        return User.builder()
                .name(name)
                .surname(surname)
                .patronymic(patronymic)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .country(country)
                .documentType(documentType)
                .telephone(telephone)
                .email(email)
                .login(login)
                .passwordHash(password).build();
    }
}
