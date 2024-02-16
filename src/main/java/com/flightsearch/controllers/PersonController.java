package com.flightsearch.controllers;

import com.flightsearch.models.Person;
import com.flightsearch.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    PersonController (final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable Long id) {
        return personRepository.findById(id);
    }

    // create a person
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Person create(@RequestBody Person person) {
        return personRepository.save(person);
    }

    // update a person
    @PutMapping
    public Person update(@RequestBody Person person) {
        return personRepository.save(person);
    }

    // delete a person
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
}

