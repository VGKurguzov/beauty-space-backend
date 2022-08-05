package com.saxakiil.beautyspacebackend.dto.auth;

import com.saxakiil.beautyspacebackend.dto.profile.AccountProfileRequest;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private AccountProfileRequest accountProfileRequest;
}
