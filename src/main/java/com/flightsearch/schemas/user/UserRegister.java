package com.flightsearch.schemas.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegister extends UserBase {
    @Schema(example = "super-secret")
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}
