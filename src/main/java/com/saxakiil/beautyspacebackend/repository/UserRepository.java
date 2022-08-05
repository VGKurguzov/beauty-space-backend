package com.saxakiil.beautyspacebackend.repository;

import com.saxakiil.beautyspacebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);

    Optional<User> findByUsername(final String username);

}
