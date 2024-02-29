package com.flightsearch.schemas.user;

import lombok.Data;

@Data
public class AuthUser {
    private String login;
    private String password;
}
