package com.supportsystem.application.services;

import java.util.List;
import java.util.stream.Collectors;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.repositories.TicketRepository;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.exceptions.UserNotFoundException;
import com.supportsystem.application.repositories.AppUserRepository;
import com.supportsystem.application.response.dtos.UserResponseDTO;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private TicketRepository ticketRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll().stream().map(user -> {
			return modelMapper.map(user, UserResponseDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		return userRepository.findById(id).map(user -> {
			return modelMapper.map(user, UserResponseDTO.class);
		}).orElseThrow(() -> new UserNotFoundException(id));
	}

//	public UserResponseDTO getUserWithTickets(Long userId) {
//		AppUser appUser = userRepository.findById(userId)
//			.orElseThrow(() -> new UserNotFoundException(userId));
//
//		List<Ticket> tickets = ticketRepository.findByAppUser(userId);
//		UserResponseDTO appUserDTO = modelMapper.map(appUser, UserResponseDTO.class);
//		appUserDTO.setTickets(tickets);
//
//		return appUserDTO;
//	}

	@Override
	public UserResponseDTO save(UserRequestDTO userRequestDTO) {
		AppUser entity = modelMapper.map(userRequestDTO, AppUser.class);
		entity.setCreatedBy(-1L);
		userRepository.save(entity);
		return modelMapper.map(entity, UserResponseDTO.class);
	}

}
