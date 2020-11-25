package com.supportsystem.application.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supportsystem.application.domains.User;
import com.supportsystem.application.dtos.UserDTO;
import com.supportsystem.application.services.UserService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/users")
public class UserController {

	protected Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	@Description(value = "returns all users")
	public @ResponseBody ResponseEntity<List<UserDTO>> getAllUsers() {
		logger.info("get all users");
		List<UserDTO> response = userService.getAllUsers();
		logger.info(userService.getAllUsers().toString());
		return new ResponseEntity<List<UserDTO>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@Description(value = "returns a user by Id")
	public @ResponseBody ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		logger.info("get a user by Id");
		UserDTO response = userService.getUserById(id);
		return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
	}
}
