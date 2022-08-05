package com.saxakiil.beautyspacebackend.service;

import com.saxakiil.beautyspacebackend.model.User;
import com.saxakiil.beautyspacebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void save(final User user) {
        userRepository.save(user);
    }

    @Transactional
    public void changeUsernameById(final Long id, final String newUsername) {
        userRepository.findById(id).ifPresent(user -> {
            user.setUsername(newUsername);
            userRepository.save(user);
        });
    }

    @Transactional
    public void changeEmailById(final Long id, final String newEmail) {
        userRepository.findById(id).ifPresent(user -> {
            user.setEmail(newEmail);
            userRepository.save(user);
        });
    }

    @Transactional
    public void changePasswordById(final Long id, final String newEncodePassword) {
        userRepository.findById(id).ifPresent(user -> {
            user.setPassword(newEncodePassword);
            userRepository.save(user);
        });
    }

    @Transactional
    public Optional<User> findById(final Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> findByUsername(final String user) {
        return userRepository.findByUsername(user);
    }

    @Transactional
    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void activateById(final Long id) {
        userRepository.findById(id)
                .ifPresent(user -> user.setActivated(Boolean.TRUE));
    }

    @Transactional
    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

}
