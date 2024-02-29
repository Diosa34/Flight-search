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

    public OutUser(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setGender(user.getGender());
        this.setLogin(user.getLogin());
        this.setName(user.getName());
        this.setSurname(user.getSurname());
        this.setDateOfBirth(user.getDateOfBirth());
        this.setTelephone(user.getTelephone());
        this.setPatronymic(user.getPatronymic());
    }

    @Override
    public void fillFromModel(User model) {
        this.id = model.getId();
    }

    @Override
    public User.UserBuilder createAndFillModelBuilder() {
        return super.createAndFillModelBuilder().id(id);
    }
}
