package com.flightsearch.schemas.user;

import com.flightsearch.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Schema(description = "Схема регистрации")
@Data
public class CreateUser extends BaseUser {
    @NotBlank
    @Size(max = 30)
    private String password;

    @Override
    public void updateModel(User model) {
        super.updateModel(model);
        model.setPassword(this.password);
    }
}
