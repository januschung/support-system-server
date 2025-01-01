package com.supportsystem.application.controllers;

import com.supportsystem.application.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        log.info("Attempting login for username: {}", username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

            User user = (User) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(user.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } catch (Exception e) {
            log.error("Authentication failed for username: {}", username, e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");

        }
    }

}
