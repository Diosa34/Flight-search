package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.exceptions.PermissionDeniedException;
import com.flightsearch.models.User;
import com.flightsearch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository
                .findByLogin(auth.getName())
                .orElseThrow(
                        () -> new NotFoundException(auth.getName(), "User")
                );
    }

    public void userRequired(User user) {
        if (!getCurrentUser().getId().equals(user.getId())) {
            throw new PermissionDeniedException("USER");
        }
    }
}
