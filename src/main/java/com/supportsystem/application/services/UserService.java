package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.dtos.UserDTO;

public interface UserService {
	
	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long id);

}
