package com.flightsearch.schemas.user;

import com.flightsearch.models.Gender;
import com.flightsearch.models.User;
import com.flightsearch.schemas.BaseSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class BaseUser implements BaseSchema<User, User.UserBuilder> {
    @Schema(description = "Имя", example = "Иван")
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    @Schema(description = "Фамилия", example = "Иванов")
    private String surname;

    @Size(min = 3, max = 30)
    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;

    @Schema(description = "Пол", example = "MALE")
    private Gender gender;

    @Schema(description = "Дата рождения")
    private Date dateOfBirth;

    @NotBlank
    @Size(max = 30)
    private String telephone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 30)
    private String login;

    @Override
    public void fillFromModel(User model) {
        this.name = model.getName();
        this.surname = model.getSurname();
        this.patronymic = model.getPatronymic();
        this.gender = model.getGender();
        this.dateOfBirth = model.getDateOfBirth();
        this.telephone = model.getTelephone();
        this.email = model.getEmail();
        this.login = model.getLogin();
    }

    @Override
    public void updateModel(User user) {
        this
    }

    @Override
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public User.UserBuilder createAndFillModelBuilder() {
        return User.builder()
                .name(name)
                .surname(surname)
                .patronymic(patronymic)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .telephone(telephone)
                .email(email)
                .login(login);
    }

    @Override
    public User toModel() {
        return this.createAndFillModelBuilder().build();
    }
}
