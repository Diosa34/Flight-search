package com.flightsearch.schemas.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegister extends UserBase {
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}
