package com.flightsearch.schemas.user;

import com.flightsearch.models.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserBase {
    @Schema(example = "Иван")
    @NotBlank
    @Size(max = 30)
    protected String name;

    @Schema(example = "Иванов")
    @NotBlank
    @Size(max = 30)
    protected String surname;

    @Schema(example = "Иванович")
    @Size(max = 30)
    protected String patronymic;

    protected Gender gender;

    @Schema(example = "+79220912121")
    @NotBlank
    @Size(max = 30)
    protected String telephone;

    @Schema(example = "sample@example.com")
    @NotBlank
    @Email
    @Size(max = 30)
    protected String email;

    @Schema(example = "super_login")
    @NotBlank
    @Size(max = 30)
    protected String login;
}
