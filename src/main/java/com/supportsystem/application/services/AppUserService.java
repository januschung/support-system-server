package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserService {
	
	List<UserResponseDTO> getAllUsers();

	UserResponseDTO getUserById(Long id);

	UserResponseDTO save(UserRequestDTO userRequestDTO);

    UserDetails loadUserByUsername(String username);
}
