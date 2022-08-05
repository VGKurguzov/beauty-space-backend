package com.saxakiil.beautyspacebackend.dto.user;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserNewEmailRequest {
    private String password;
    private String newEmail;
    private String confirmNewEmail;
}
