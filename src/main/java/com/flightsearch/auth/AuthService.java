//package com.flightsearch.auth;
//
//import com.flightsearch.exceptions.NotFoundException;
//import com.flightsearch.models.User;
//import com.flightsearch.services.UserDBService;
//import io.jsonwebtoken.Claims;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UserDBService userService;
//    private final JwtProvider jwtProvider;
//
//    public JwtResponse getAccessToken(@NonNull JwtRequest authRequest) {
//        final User user = userService.findByLogin(authRequest.getLogin())
//                .orElseThrow(() -> new NotFoundException());
//        if (user.getPassword().equals(authRequest.getPassword())) {
//            final String accessToken = jwtProvider.generateAccessToken(user);
//            return new JwtResponse(accessToken);
//        } else {
//            throw new NotFoundException();
//        }
//    }
//
//    public boolean validateAccessToken(@NonNull String accessToken) {
//        return jwtProvider.validateToken(accessToken);
//    }
//
//    public Claims getAccessClaims(@NonNull String token) {
//        return jwtProvider.getClaims(token);
//    }
//}
