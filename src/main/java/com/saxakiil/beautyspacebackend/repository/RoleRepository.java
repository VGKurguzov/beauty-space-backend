package com.saxakiil.beautyspacebackend.repository;

import com.saxakiil.beautyspacebackend.model.Role;
import com.saxakiil.beautyspacebackend.util.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRole name);
}
