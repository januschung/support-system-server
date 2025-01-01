package com.supportsystem.application.services;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.repositories.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public JwtUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
        log.info("JwtUserDetailsService initialized");

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Looking for user: {}", username);

        AppUser appUser = appUserRepository.findByUsername(username)
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
