package com.flightsearch.schemas.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UserRegister extends UserBase {
    @Schema(example = "super-secret")
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}
