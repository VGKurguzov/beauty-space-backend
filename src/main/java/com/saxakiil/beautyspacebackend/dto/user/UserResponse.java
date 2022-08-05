package com.saxakiil.beautyspacebackend.dto.user;

import com.saxakiil.beautyspacebackend.dto.profile.AccountProfileResponse;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserResponse {
    @NotNull
    private String username;
    @NotNull
    private AccountProfileResponse accountProfile;
    @NotNull
    private boolean isActivated;
}
