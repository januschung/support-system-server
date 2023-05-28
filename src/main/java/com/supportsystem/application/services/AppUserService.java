package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.response.dtos.UserDTO;

public interface AppUserService {
	
	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long id);

	UserDTO save(com.supportsystem.application.request.dtos.UserDTO userDTO);

}
