package com.flightsearch.services.mapping;

import com.flightsearch.models.User;
import com.flightsearch.schemas.user.UserBase;
import com.flightsearch.schemas.user.UserRead;
import com.flightsearch.schemas.user.UserRegister;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    protected User mapUserBaseToEntity(UserBase schema, User entity) {
        entity.setName(schema.getName());
        entity.setSurname(schema.getSurname());
        entity.setPatronymic(schema.getPatronymic());
        entity.setGender(schema.getGender());
        entity.setTelephone(schema.getTelephone());
        entity.setEmail(schema.getEmail());
        entity.setLogin(schema.getLogin());
        return entity;
    }

    protected User mapUserBaseToEntity(UserBase schema) {
        return mapUserBaseToEntity(schema, new User());
    }

    public User mapUserRegistrationToEntity(UserRegister schema) {
        User entity = mapUserBaseToEntity(schema);
        entity.setPassword(schema.getPassword());
        return entity;
    }

    public UserRead mapEntityToUserRead(User entity) {
        if (entity == null) return null;
        UserRead schema = new UserRead();
        schema.setId(entity.getId());
        schema.setName(entity.getName());
        schema.setSurname(entity.getSurname());
        schema.setPatronymic(entity.getPatronymic());
        schema.setGender(entity.getGender());
        schema.setRole(entity.getRole());
        schema.setTelephone(entity.getTelephone());
        schema.setEmail(entity.getEmail());
        schema.setLogin(entity.getLogin());
        return schema;
    }
}
