package com.saxakiil.beautyspacebackend.dto.profile;

import com.saxakiil.beautyspacebackend.model.Contact;
import com.saxakiil.beautyspacebackend.model.Photo;
import com.saxakiil.beautyspacebackend.model.Service;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AccountProfileResponse {
    private String name;
    private String description;
    private Photo masterPhoto;
    private Contact contact;
    private Set<Photo> workPhotos;
    private Set<Service> services;
}
