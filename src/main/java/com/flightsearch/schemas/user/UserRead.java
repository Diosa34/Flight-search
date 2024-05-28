package com.flightsearch.schemas.user;

import com.flightsearch.models.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UserRead extends UserBase {
    private Long id;
    private Role role;
}
