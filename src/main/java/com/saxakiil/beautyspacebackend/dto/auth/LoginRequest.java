package com.saxakiil.beautyspacebackend.dto.auth;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;
}
