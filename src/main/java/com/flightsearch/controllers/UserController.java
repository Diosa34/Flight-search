package com.flightsearch.controllers;

import com.flightsearch.schemas.user.UserRead;
import com.flightsearch.schemas.user.UserRegister;
import com.flightsearch.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Пользователи")
@RestController
@Profile({"prodMain", "devMain"})
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
public class UserController {
    final UserService userService;

    @Operation(summary = "Регистрирует пользователя")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserRead registerUser(@RequestBody @Valid UserRegister schema) {
        return userService.register(schema);
    }

    @Operation(summary = "Список пользователей")
    @GetMapping
    public List<UserRead> getUserList() {
        return userService.getAll();
    }

    @Operation(summary = "Возвращает пользователя")
    @GetMapping("/{id}")
    public UserRead getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Удаляет пользователя")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
