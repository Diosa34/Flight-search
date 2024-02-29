package com.flightsearch.schemas.user;

import com.flightsearch.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutUser extends BaseUser {
    @NotNull
    @Positive
    public Long id;

    @Override
    public void fromModel(User model) {
        super.fromModel(model);
        this.id = model.getId();
    }

    @Override
    public void updateModel(User model) {
        super.updateModel(model);
        model.setId(this.id);
    }
}
