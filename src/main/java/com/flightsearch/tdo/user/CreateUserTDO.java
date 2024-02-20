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
    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
    @NotEmpty
    @Size(min = 3, max = 30)
    @Schema(description = "Фамилия", example = "Иванов")
    private String surname;
    @Null
    @Size(min = 3, max = 30)
    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;
    @NotNull
    @Schema(description = "Пол", example = "MALE")
    private Gender gender;
    @NotNull
    @Schema(description = "Дата рождения")
    private Date dateOfBirth;
    @NotEmpty
    @Size(min = 3, max = 255)
    private String country;
    @NotNull
    @Schema(description = "Тип документа")
    private DocumentType documentType;
    @NotEmpty
    private String documentNumber;
    @NotEmpty
    @Size(max = 30)
    private String telephone;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 3, max = 30)
    private String login;
    @NotEmpty
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
                .login(login).build();
    }
}
