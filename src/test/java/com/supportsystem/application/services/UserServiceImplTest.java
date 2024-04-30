package com.supportsystem.application.services;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.exceptions.UserNotFoundException;
import com.supportsystem.application.repositories.AppUserRepository;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.UserResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	private static AppUser appUser1 = new AppUser();
	private static AppUser appUser2 = new AppUser();
	
	@BeforeAll
	public static void init() {
		appUser1.setId(1L);
		appUser1.setCreatedBy(1L);
		appUser1.setModifiedBy(1L);
		appUser1.setEmail("whatever");
		appUser1.setFirstName("foo");
		appUser1.setLastName("bar");
		appUser1.setEnabledFl(true);
		appUser1.setPhone("8008889999");
		appUser1.setUsername("foobar");
		appUser1.setCreatedOn(new Date());
		appUser1.setLastModified(new Date());

		appUser2.setId(2L);
		appUser2.setCreatedBy(1L);
		appUser2.setModifiedBy(1L);
		appUser2.setEmail("whatever");
		appUser2.setFirstName("baz");
		appUser2.setLastName("bar");
		appUser2.setEnabledFl(true);
		appUser2.setPhone("8008880000");
		appUser2.setUsername("bazbar");
		appUser2.setCreatedOn(new Date());
		appUser2.setLastModified(new Date());
	}

	@Mock
	@Autowired
	private AppUserRepository AppUserRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private AppUserServiceImpl AppUserService;

	@Test
	public void testGetAllUsers() {
		List<AppUser> AppUsers = Arrays.asList(appUser1, appUser2);
		when(AppUserRepository.findAll()).thenReturn(AppUsers);

		List<UserResponseDTO> responseDTOs = AppUserService.getAllUsers();
		assertEquals(AppUsers.size(), responseDTOs.size());
	}

	@Test
	public void testGetUserByIdExist() {
		when(AppUserRepository.findById(1L)).thenReturn(Optional.of(appUser1));
		when(modelMapper.map(appUser1, UserResponseDTO.class)).thenReturn(new UserResponseDTO());

		UserResponseDTO responseDTO = AppUserService.getUserById(1L);
		assertEquals(UserResponseDTO.class, responseDTO.getClass());
	}

	@Test
	public void testGetUserByIdNotExist() {
		when(AppUserRepository.findById(999L)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> AppUserService.getUserById(999L));
	}

	@Test
	public void testSave() {
		UserResponseDTO responseDTO = new UserResponseDTO();
		UserRequestDTO requestDTO = mock(UserRequestDTO.class);

		when(modelMapper.map(requestDTO, AppUser.class)).thenReturn(appUser1);
		when(modelMapper.map(appUser1, UserResponseDTO.class)).thenReturn(responseDTO);

		UserResponseDTO savedDTO = AppUserService.save(requestDTO);

		assertEquals(responseDTO, savedDTO);
		verify(modelMapper).map(requestDTO, AppUser.class);
		verify(modelMapper).map(appUser1, UserResponseDTO.class);
		verify(AppUserRepository).save(appUser1);
	}
}
