package com.flightsearch.controllers;

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
    public OutSign findById(@PathVariable Long id) {
        OutSign schema = new OutSign();
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
    public OutSign create(@RequestBody @Valid CreateSign sign) {
        OutSign schema = new OutSign();
        schema.fromModel(signDB.save(sign));
        return schema;
    }

    // delete a sign
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        signDB.deleteById(id);
    }
}
