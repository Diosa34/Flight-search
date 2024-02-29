package com.flightsearch.controllers;

import com.flightsearch.models.User;
import com.flightsearch.schemas.user.AuthUser;
import com.flightsearch.schemas.user.BaseUser;
import com.flightsearch.schemas.user.CreateUser;
import com.flightsearch.services.UserDBService;
import com.flightsearch.schemas.user.OutUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@Validated
public class PersonController {
    @Autowired
    private UserDBService userDB;

    @GetMapping
    public List<User> findAll() {
        return userDB.findAll();
    }

    @GetMapping("/{id}")
    public OutUser findById(@PathVariable Long id) {
        OutUser schema = new OutUser();
        schema.fromModel(userDB.findById(id));
        return schema;
    }

    // create a person
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public OutUser create(@RequestBody @Valid CreateUser user) {
        OutUser schema = new OutUser();
        schema.fromModel(userDB.save(user));
        return schema;
    }

    @PostMapping
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Проверяет, что пользователь есть в базе данных"
    )
    public void authenticate(@RequestBody @Valid AuthUser user) {
        OutUser schema = new OutUser();
        schema.fromModel(userDB.findByLogin(user.getLogin()));
    }

    // update a user
    @PutMapping("/{id}")
    public OutUser update(@PathVariable Long id, @RequestBody @Valid BaseUser userData) {
        User user = userDB.findById(id);
        userData.updateModel(user);
        OutUser schema = new OutUser();
        schema.fromModel(userDB.save(userData));
        return schema;
    }

    // delete a user
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userDB.deleteById(id);
    }
}

