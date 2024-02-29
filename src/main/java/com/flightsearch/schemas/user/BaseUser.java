package com.flightsearch.schemas.user;

import com.flightsearch.models.Gender;
import com.flightsearch.models.User;
import com.flightsearch.schemas.ModelSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class BaseUser implements ModelSchema<User> {
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
    public void fromModel(User model) {
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
    public void updateModel(User model) {
        model.setName(this.name);
        model.setSurname(this.surname);
        model.setPatronymic(this.patronymic);
        model.setGender(this.gender);
        model.setDateOfBirth(this.dateOfBirth);
        model.setTelephone(this.telephone);
        model.setEmail(this.email);
        model.setLogin(this.login);
    }

    @Override
    public User toModel() {
        User newUser = new User();
        this.updateModel(newUser);
        return newUser;
    }
}
