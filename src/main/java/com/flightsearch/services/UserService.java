package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.User;
import com.flightsearch.repositories.UserRepository;
import com.flightsearch.repositories.XMLUserRepository;
import com.flightsearch.schemas.user.UserRead;
import com.flightsearch.schemas.user.UserRegister;
import com.flightsearch.services.mapping.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final UserMapper userMapper;
    final XMLUserRepository xmlRepository;

    public UserRead register(UserRegister schema) {
        User newUser = userMapper.mapUserRegistrationToEntity(schema);
        newUser = userRepository.save(newUser);
        xmlRepository.saveUser(newUser);
        return userMapper.mapEntityToUserRead(newUser);
    }

    public UserRead getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
//        User xmlUser = xmlRepository.getById(id);
        return userMapper.mapEntityToUserRead(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
        xmlRepository.delete(user);
    }
}
