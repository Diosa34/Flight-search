package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.User;
import com.flightsearch.repositories.UserRepository;
import com.flightsearch.schemas.user.UserRead;
import com.flightsearch.schemas.user.UserRegister;
import com.flightsearch.services.mapping.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository userRepository;
    final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserRead register(UserRegister schema) {
        User newUser = userMapper.mapUserRegistrationToEntity(schema);
        newUser = userRepository.save(newUser);
        return userMapper.mapEntityToUserRead(newUser);
    }

    public UserRead getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return userMapper.mapEntityToUserRead(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
    }
}
