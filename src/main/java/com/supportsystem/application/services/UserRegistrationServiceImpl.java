package com.supportsystem.application.services;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.domains.Role;
import com.supportsystem.application.repositories.AppUserRepository;
import com.supportsystem.application.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AppUser registerUser(String username, String password, String email) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new IllegalStateException("Default role not found.");
        }

        AppUser user = AppUser.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .email(email)
            .roles(Collections.singleton(userRole))
            .enabled(true)
            .build();

        return userRepository.save(user);
    }
}
