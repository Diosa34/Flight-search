package com.flightsearch.services;

import com.flightsearch.exceptions.PermissionDeniedException;
import com.flightsearch.models.User;
import com.flightsearch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"prodMain", "devMain"})
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        return new User();
    }

    public void userRequired(User user) {
        if (!getCurrentUser().getId().equals(user.getId())) {
            throw new PermissionDeniedException("USER");
        }
    }
}
