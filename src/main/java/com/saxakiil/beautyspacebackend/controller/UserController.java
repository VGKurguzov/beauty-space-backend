package com.saxakiil.beautyspacebackend.controller;

import com.saxakiil.beautyspacebackend.config.jwt.JwtUtils;
import com.saxakiil.beautyspacebackend.dto.MessageResponse;
import com.saxakiil.beautyspacebackend.dto.profile.AccountProfileResponse;
import com.saxakiil.beautyspacebackend.dto.user.UserNewEmailRequest;
import com.saxakiil.beautyspacebackend.dto.user.UserNewPasswordRequest;
import com.saxakiil.beautyspacebackend.dto.user.UserNewUsernameRequest;
import com.saxakiil.beautyspacebackend.dto.user.UserResponse;
import com.saxakiil.beautyspacebackend.model.UserDetailsImpl;
import com.saxakiil.beautyspacebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @GetMapping("/get")
    @PreAuthorize(value = "hasAnyRole('USER')")
    public ResponseEntity<?> getUsername() {
        UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity
                .ok(UserResponse.builder()
                        .username(authUser.getUsername())
                        .accountProfile(AccountProfileResponse.builder()
                                .name(authUser.getAccountProfile().getName())
                                .description(authUser.getAccountProfile().getDescription())
                                .masterPhoto(authUser.getAccountProfile().getMasterPhoto())
                                .contact(authUser.getAccountProfile().getContact())
                                .workPhotos(authUser.getAccountProfile().getWorkPhotos())
                                .services(authUser.getAccountProfile().getServices())
                                .build())
                        .isActivated(authUser.isActivated())
                        .build());
    }

    @PostMapping(value = "/changeEmail", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('USER')")
    public ResponseEntity<?> changeEmail(@RequestBody @NotNull UserNewEmailRequest userNewEmailRequest) {
        UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser.getUsername(),
                        userNewEmailRequest.getPassword())).isAuthenticated()) {
            if (userNewEmailRequest.getNewEmail().equals(userNewEmailRequest.getConfirmNewEmail())) {
                userService.changeEmailById(authUser.getId(), userNewEmailRequest.getNewEmail());
                return ResponseEntity
                        .ok(new MessageResponse("Email CHANGED"));
            }
            return ResponseEntity
                    .badRequest()
                    .body("Error: Emails not equals");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Wrong password");
        }
    }

    @PostMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('USER')")
    public ResponseEntity<?> changePassword(@RequestBody @NotNull UserNewPasswordRequest userNewPasswordRequest) {
        UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (passwordEncoder.encode(userNewPasswordRequest.getOldPassword()).equals(authUser.getPassword())) {
            if (userNewPasswordRequest.getNewPassword().equals(userNewPasswordRequest.getConfirmNewPassword())) {
                userService.changePasswordById(authUser.getId(), passwordEncoder
                        .encode(userNewPasswordRequest.getNewPassword()));
                return ResponseEntity
                        .ok(new MessageResponse("Password CHANGED"));
            }
            return ResponseEntity
                    .badRequest()
                    .body("Error: Passwords not equals");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Wrong password");
        }
    }

    @DeleteMapping("/deleteAccount")
    @PreAuthorize(value = "hasAnyRole('USER')")
    public ResponseEntity<?> deleteAccount(@RequestBody @NotNull String password,
                                           @RequestBody @NotNull String confirmPassword) {
        UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (passwordEncoder.encode(password).equals(authUser.getPassword())) {
            if (password.equals(confirmPassword)) {
                userService.deleteById(authUser.getId());
                return ResponseEntity
                        .ok(new MessageResponse("Account DELETED"));
            }
            return ResponseEntity
                    .badRequest()
                    .body("Error: Passwords not equals");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Wrong password");
        }
    }
}
