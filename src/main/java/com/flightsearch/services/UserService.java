package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.User;
import com.flightsearch.repositories.UserRepository;
import com.flightsearch.repositories.XMLUserRepository;
import com.flightsearch.schemas.user.UserRead;
import com.flightsearch.schemas.user.UserRegister;
import com.flightsearch.services.mapping.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile({"prodMain", "devMain"})
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final XMLUserRepository xmlUserRepository;

    public UserRead register(UserRegister schema) {
        User newUser = userMapper.mapUserRegistrationToEntity(schema);
        newUser = userRepository.save(newUser);
        xmlUserRepository.save(newUser);
        return userMapper.mapEntityToUserRead(newUser);
    }

    public UserRead getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "User")
        );
//        User xmlUser = xmlUserRepository.getById(id);
        return userMapper.mapEntityToUserRead(user);
    }

    public List<UserRead> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::mapEntityToUserRead)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "User")
        );
        userRepository.delete(user);
        xmlUserRepository.delete(user);
    }
}
