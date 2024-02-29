package com.flightsearch.schemas.user;

import com.flightsearch.models.User;
import com.flightsearch.schemas.BaseSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Schema(description = "Схема регистрации")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser extends BaseUser {
    @NotBlank
    @Size(max = 30)
    private String password;

    @Override
    public User.UserBuilder createAndFillModelBuilder() {
        return super.createAndFillModelBuilder().passwordHash(password);
    }
}
