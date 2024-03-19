package com.flightsearch.controllers;

import com.flightsearch.schemas.sign.SignCreate;
import com.flightsearch.schemas.sign.SignOut;
import com.flightsearch.services.SignDBService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign")
@Validated
public class SignController {
    @Autowired
    private SignDBService signDB;

    @GetMapping("/{id}")
    public SignOut findById(@PathVariable Long id) {
        SignOut schema = new SignOut();
        schema.fromModel(signDB.findById(id));
        return schema;
    }

    // добавление нового контрагента к документу
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    @Operation(
            summary = "Добавление контрагента",
            description = "Позволяет добавить ещё одну сторону к договору, тот которой ожидается подписание"
    )
    public SignOut create(@RequestBody @Valid SignCreate sign) {
        SignOut schema = new SignOut();
        schema.fromModel(signDB.save(sign));
        return schema;
    }

    // delete a sign
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        signDB.deleteById(id);
    }
}
