package com.flightsearch.controllers;

import com.flightsearch.models.User;
import com.flightsearch.services.UserDBService;
import com.flightsearch.tdo.user.CreateUserTDO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
    public Optional<User> findById(@PathVariable Long id) {
        return userDB.findById(id);
    }

    // create a person
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public User create(@RequestBody @Valid CreateUserTDO user) {
        return userDB.save(user);
    }

    // update a user
    @PutMapping
    public User update(@RequestBody User user) {
        return userDB.save(user);
    }

    // delete a user
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userDB.deleteById(id);
    }
}

