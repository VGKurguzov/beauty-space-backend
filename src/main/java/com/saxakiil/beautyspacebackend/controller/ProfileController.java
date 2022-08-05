package com.saxakiil.beautyspacebackend.controller;

import com.saxakiil.beautyspacebackend.dto.MessageResponse;
import com.saxakiil.beautyspacebackend.dto.profile.AccountProfileRequest;
import com.saxakiil.beautyspacebackend.dto.profile.AccountProfileResponse;
import com.saxakiil.beautyspacebackend.model.AccountProfile;
import com.saxakiil.beautyspacebackend.model.UserDetailsImpl;
import com.saxakiil.beautyspacebackend.service.AccountProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/accountProfile")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileController {

    private final AccountProfileService accountProfileService;

    @GetMapping("/get")
    @PreAuthorize(value = "hasAnyRole('USER')")
    public ResponseEntity<?> getAccountProfile() {
        UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        final Optional<AccountProfile> accountProfileOptional = accountProfileService.findById(authUser.getId());
        if (accountProfileOptional.isPresent()) {
            return ResponseEntity
                    .ok(AccountProfileResponse.builder()
                            .name(accountProfileOptional.get().getName())
                            .description(accountProfileOptional.get().getDescription())
                            .contact(accountProfileOptional.get().getContact())
                            .workPhotos(accountProfileOptional.get().getWorkPhotos())
                            .masterPhoto(accountProfileOptional.get().getMasterPhoto())
                            .services(accountProfileOptional.get().getServices())
                            .build()
                    );
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Account is not found"));
        }
    }

    @PostMapping("/change")
    @PreAuthorize(value = "hasAnyRole('USER')")
    public ResponseEntity<?> changeAccountProfile(final @Valid @RequestBody AccountProfileRequest accountProfileRequest,
                                                  final BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Required field is not exists"));
        } else {
            UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            accountProfileService.findById(authUser.getId()).ifPresent(accountProfile -> {
                if (accountProfileRequest.getName() != null) {
                    accountProfile.setName(accountProfileRequest.getName());
                }
                if (accountProfileRequest.getDescription() != null) {
                    accountProfile.setDescription(accountProfileRequest.getDescription());
                }
                if (accountProfileRequest.getMasterPhoto() != null) {
                    accountProfile.setMasterPhoto(accountProfileRequest.getMasterPhoto());
                }
                accountProfileService.save(accountProfile);
            });
            return ResponseEntity
                    .ok(new MessageResponse("Name CHANGED"));
        }
    }
}
