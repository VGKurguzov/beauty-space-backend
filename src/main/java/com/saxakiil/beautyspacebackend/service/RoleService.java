package com.saxakiil.beautyspacebackend.service;

import com.saxakiil.beautyspacebackend.model.Role;
import com.saxakiil.beautyspacebackend.repository.RoleRepository;
import com.saxakiil.beautyspacebackend.util.EnumRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByName(final EnumRole role) {
        return roleRepository.findByName(role);
    }
}
