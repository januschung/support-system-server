package com.supportsystem.application.services;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.exceptions.ResourceNotFoundException;
import com.supportsystem.application.repositories.AppUserRepository;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {  // Implement UserDetailsService

    private final AppUserRepository userRepository;
    private final ModelMapper modelMapper;

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
        }).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        AppUser entity = modelMapper.map(userRequestDTO, AppUser.class);
        entity.setCreatedBy(1L);
        userRepository.save(entity);
        return modelMapper.map(entity, UserResponseDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Looking for user: {}", username);

        AppUser appUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Force initialization of lazy fields (e.g., roles), may not need this without using lazy
        appUser.getRoles().size();

        log.info("User found: {}", appUser.getUsername());

        Collection<GrantedAuthority> authorities = appUser.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());

        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
