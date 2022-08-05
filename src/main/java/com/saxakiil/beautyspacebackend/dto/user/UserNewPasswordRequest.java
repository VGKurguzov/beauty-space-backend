package com.saxakiil.beautyspacebackend.dto.user;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserNewPasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
