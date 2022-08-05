package com.saxakiil.beautyspacebackend.controller;

import com.saxakiil.beautyspacebackend.config.jwt.JwtUtils;
import com.saxakiil.beautyspacebackend.dto.MessageResponse;
import com.saxakiil.beautyspacebackend.dto.auth.JwtResponse;
import com.saxakiil.beautyspacebackend.dto.auth.LoginRequest;
import com.saxakiil.beautyspacebackend.dto.auth.SignupRequest;
import com.saxakiil.beautyspacebackend.model.AccountProfile;
import com.saxakiil.beautyspacebackend.model.Role;
import com.saxakiil.beautyspacebackend.model.User;
import com.saxakiil.beautyspacebackend.service.RoleService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

import static com.saxakiil.beautyspacebackend.util.EnumRole.ROLE_USER;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(final @Valid @RequestBody SignupRequest signupRequest,
                                          final BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Required field is not exists"));
        }
        final boolean uniqueAttrIsFree = Stream.of(userService.findByUsername(signupRequest.getUsername()),
                        userService.findByEmail(signupRequest.getEmail()))
                .noneMatch(Optional::isPresent);
        if (uniqueAttrIsFree) {
            final Optional<Role> userRole = roleService.findByName(ROLE_USER);
            if (userRole.isEmpty()) {
                return ResponseEntity
                        .internalServerError()
                        .body(new MessageResponse("Error: Unknown role"));
            } else {
                final User user = User.builder()
                        .username(signupRequest.getUsername())
                        .email(signupRequest.getEmail())
                        .password(passwordEncoder.encode(signupRequest.getPassword()))
                        .role(userRole.get())
                        .accountProfile(AccountProfile.builder()
                                .name(signupRequest.getAccountProfileRequest().getName())
                                .description(signupRequest.getAccountProfileRequest().getDescription())
                                .masterPhoto(signupRequest.getAccountProfileRequest().getMasterPhoto())
                                .build())
                        .build();

                userService.save(user);
                return ResponseEntity
                        .ok(new MessageResponse("User CREATED"));
            }
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unique field is exists"));
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(final @Valid @ModelAttribute("login") LoginRequest loginRequest) {
        final Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userOptional.get().getUsername(),
                            loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String jwt = jwtUtils.generateJwtToken(authentication);
            final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            final Role role = userService.findById(userDetails.getId()).get().getRole();
            return ResponseEntity.ok(JwtResponse.builder()
                    .token(jwt)
                    .id(userDetails.getId())
                    .username(userDetails.getUsername())
                    .role(role)
                    .email(userDetails.getEmail())
                    .build());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User is not found"));
        }
    }

    @PostMapping("/activateAccount")
    @PreAuthorize(value = "hasAnyRole('USER')")
    public ResponseEntity<?> activateAccount() {
        UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (authUser.isActivated()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Account already activated"));
        } else {
            userService.activateById(authUser.getId());
            return ResponseEntity
                    .ok(new MessageResponse("Account ACTIVATED"));
        }
    }
}
