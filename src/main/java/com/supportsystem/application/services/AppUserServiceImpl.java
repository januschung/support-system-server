package com.supportsystem.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.exceptions.UserNotFoundException;
import com.supportsystem.application.repositories.AppUserRepository;
import com.supportsystem.application.response.dtos.UserDTO;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository userRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(user -> {
			return modelMapper.map(user, UserDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserById(Long id) {
		return userRepository.findById(id).map(user -> {
			return modelMapper.map(user, UserDTO.class);
		}).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Override
	public UserDTO save(com.supportsystem.application.request.dtos.UserDTO userDTO) {
		AppUser entity = modelMapper.map(userDTO, AppUser.class);
		entity.setCreatedBy(-1L);
		userRepository.save(entity);
		return modelMapper.map(entity, UserDTO.class);
	}

}
