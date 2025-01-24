package com.supportsystem.application.services;

import com.supportsystem.application.domains.Role;
import com.supportsystem.application.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(String roleName) {
        if (roleRepository.findByName(roleName) != null) {
            throw new IllegalArgumentException("Role already exists.");
        }
        Role role = Role.builder()
            .name(roleName)
            .build();
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return Optional.ofNullable(roleRepository.findByName(roleName))
            .orElseThrow(() -> new IllegalArgumentException("Role not found."));
    }
}
