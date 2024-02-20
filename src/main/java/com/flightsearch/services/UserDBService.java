package com.flightsearch.services;

import com.flightsearch.models.User;
import com.flightsearch.repositories.UserRepository;
import com.flightsearch.tdo.TDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDBService {

    @Autowired
    private UserRepository repository;

    public User save(TDO<User> userTDO) {
        return repository.save(userTDO.toModel());
    }

    public User save(User user) {
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
