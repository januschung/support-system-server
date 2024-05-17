package com.supportsystem.application.controllers;

import java.util.List;

import com.supportsystem.application.request.dtos.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supportsystem.application.response.dtos.UserResponseDTO;
import com.supportsystem.application.services.AppUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class AppUserController {


    @Autowired
    private AppUserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Description(value = "returns all users")
    public @ResponseBody ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("get all users");
        List<UserResponseDTO> response = userService.getAllUsers();
        log.info(userService.getAllUsers().toString());
        return new ResponseEntity<List<UserResponseDTO>>(response, HttpStatus.OK);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Description(value = "returns a user by Id")
    public @ResponseBody ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        log.info("get a user by Id");
        UserResponseDTO response = userService.getUserById(id);
        return new ResponseEntity<UserResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Description(value = "save a new user")
    public @ResponseBody ResponseEntity<UserResponseDTO> saveUser(
        @RequestBody UserRequestDTO userRequestDTO) {
        log.info("save a new user", userRequestDTO);
        UserResponseDTO response = userService.save(userRequestDTO);
        return new ResponseEntity<UserResponseDTO>(response, HttpStatus.OK);
    }

}
