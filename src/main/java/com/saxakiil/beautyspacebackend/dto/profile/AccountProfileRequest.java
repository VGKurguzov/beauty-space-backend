package com.saxakiil.beautyspacebackend.dto.profile;

import com.saxakiil.beautyspacebackend.model.Photo;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class AccountProfileRequest {
    @NotNull
    private String name;

    private String description;

    private Photo masterPhoto;
}
