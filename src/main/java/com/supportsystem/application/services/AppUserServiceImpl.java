package com.supportsystem.application.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.supportsystem.application.domains.Role;
import com.supportsystem.application.repositories.RoleRepository;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.exceptions.UserNotFoundException;
import com.supportsystem.application.repositories.AppUserRepository;
import com.supportsystem.application.response.dtos.UserResponseDTO;


@Service
public class AppUserServiceImpl implements AppUserService {  // Implement UserDetailsService

    @Autowired
    private AppUserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

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

    @Override
    @Transactional
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            return modelMapper.map(user, UserResponseDTO.class);
        }).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id).map(user -> {
            return modelMapper.map(user, UserResponseDTO.class);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        AppUser entity = modelMapper.map(userRequestDTO, AppUser.class);
        entity.setCreatedBy(1L);
        userRepository.save(entity);
        return modelMapper.map(entity, UserResponseDTO.class);
    }

}
