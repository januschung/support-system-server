package com.supportsystem.application.controllers;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.UserResponseDTO;
import com.supportsystem.application.services.AppUserService;
import com.supportsystem.application.services.UserRegistrationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AppUserController {

    private final AppUserService userService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    @Description(value = "register a new user")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        // Extract necessary fields from userRequestDTO
        String username = userRequestDTO.getUsername();
        String password = userRequestDTO.getPassword();
        String email = userRequestDTO.getEmail();

        // Call the service layer to register the user
        AppUser registeredUser = userRegistrationService.registerUser(username, password, email);

        // Map the AppUser to UserResponseDTO for response
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(registeredUser.getId());
        userResponseDTO.setUsername(registeredUser.getUsername());
        userResponseDTO.setEmail(registeredUser.getEmail());

        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Description(value = "returns all users")
    public @ResponseBody ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("get all users");
        List<UserResponseDTO> response = userService.getAllUsers();
        log.info(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Description(value = "returns a user by Id")
    public @ResponseBody ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        log.info("get a user by Id");
        UserResponseDTO response = userService.getUserById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("isAuthenticated()")
    @Description(value = "save a new user")
    public @ResponseBody ResponseEntity<UserResponseDTO> saveUser(
        @RequestBody UserRequestDTO userRequestDTO) {
        log.info("save a new user: {}", userRequestDTO);
        UserResponseDTO response = userService.save(userRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
