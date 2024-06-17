package com.flightsearch.schemas.user;

import com.flightsearch.models.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserBase {
    @NotBlank
    @Size(max = 30)
    protected String name;

    @NotBlank
    @Size(max = 30)
    protected String surname;

    @Size(max = 30)
    protected String patronymic;

    protected Gender gender;

    @NotBlank
    @Size(max = 30)
    protected String telephone;

    @NotBlank
    @Email
    @Size(max = 30)
    protected String email;

    @NotBlank
    @Size(max = 30)
    protected String login;
}
