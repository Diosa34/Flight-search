package com.flightsearch.security;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.User;
import com.flightsearch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class JAASAuthorityGranter implements AuthorityGranter {

    private final UserRepository userRepository;
    @Override
    public Set<String> grant(Principal principal) {
        User user = userRepository
                .findByLogin(principal.getName())
                .orElseThrow(() -> new NotFoundException(principal.getName(), "User"));
        return Collections.singleton(user.getRole().name());
    }
}
