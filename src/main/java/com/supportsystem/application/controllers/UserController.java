package com.supportsystem.application.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supportsystem.application.response.dtos.UserDTO;
import com.supportsystem.application.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


	@Autowired
	private UserService userService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Description(value = "returns all users")
	public @ResponseBody ResponseEntity<List<UserDTO>> getAllUsers() {
		log.info("get all users");
		List<UserDTO> response = userService.getAllUsers();
		log.info(userService.getAllUsers().toString());
		return new ResponseEntity<List<UserDTO>>(response, HttpStatus.OK);
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Description(value = "returns a user by Id")
	public @ResponseBody ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		log.info("get a user by Id");
		UserDTO response = userService.getUserById(id);
		return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
	}

	@PostMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@Description(value = "save a new user")
	public @ResponseBody ResponseEntity<UserDTO> saveUser(
			@RequestBody com.supportsystem.application.request.dtos.UserDTO userDTO) {
//        log.info("save a new user", value("body", userDTO));
	    log.info("save a new user", userDTO);
		UserDTO response = userService.save(userDTO);
		return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
	}

}
