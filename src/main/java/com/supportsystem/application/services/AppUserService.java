package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.UserResponseDTO;

public interface AppUserService {
	
	List<UserResponseDTO> getAllUsers();

	UserResponseDTO getUserById(Long id);

	UserResponseDTO save(UserRequestDTO userRequestDTO);

    AppUser registerUser(String username, String password, String email);
}
