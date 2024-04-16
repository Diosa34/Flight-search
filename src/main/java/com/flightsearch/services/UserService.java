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

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserRead> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::mapEntityToUserRead)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
        xmlRepository.delete(user);
    }
}
