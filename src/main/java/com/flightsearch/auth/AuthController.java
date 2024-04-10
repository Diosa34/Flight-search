//package com.flightsearch.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService authService;
//
//    @PostMapping("login")
//    public ResponseEntity<JwtResponse> generateAccessToken(@RequestBody JwtRequest authRequest) {
//        final JwtResponse token = authService.getAccessToken(authRequest);
//        return ResponseEntity.ok(token);
//    }
//}