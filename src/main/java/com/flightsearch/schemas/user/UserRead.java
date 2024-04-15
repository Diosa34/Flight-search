package com.flightsearch.schemas.user;

import com.flightsearch.models.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRead extends UserBase {
    private Long id;
    private Role role;
}
