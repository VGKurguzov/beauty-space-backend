package com.saxakiil.beautyspacebackend.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saxakiil.beautyspacebackend.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

    private String token;
    @JsonIgnore
    private Long id;
    private String username;
    private String email;
    private Role role;

}
