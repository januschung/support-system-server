package com.supportsystem.application.services;

import com.supportsystem.application.domains.AppUser;
import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.exceptions.UserNotFoundException;
import com.supportsystem.application.repositories.AppUserRepository;
import com.supportsystem.application.repositories.TicketRepository;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.UserResponseDTO;
import com.supportsystem.application.shared.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	private static AppUser appUser1;
	private static AppUser appUser2;
	private static Ticket ticket1;
	private static Ticket ticket2;

	@BeforeAll
	public static void init() {
		appUser1 = AppUser.builder()
			.id(1L)
			.createdBy(1L)
			.modifiedBy(1L)
			.email("whatever")
			.firstName("foo")
			.lastName("bar")
			.enableFl(true)
			.phone("8008889999")
			.username("foobar")
			.createdOn(new Date())
			.lastModified(new Date())
			.build();

		appUser2 = AppUser.builder()
			.id(2L)
			.createdBy(1L)
			.modifiedBy(1L)
			.email("whatever")
			.firstName("baz")
			.lastName("bar")
			.enableFl(true)
			.phone("8008880000")
			.username("bazbar")
			.createdOn(new Date())
			.lastModified(new Date())
			.build();

		ticket1 = Ticket.builder()
			.id(1L)
			.createdBy(1L)
			.modifiedBy(1L)
			.description("whatever 1")
			.status(Status.Ticket.NEW)
			.assigneeId(1L)
			.clientId(1L)
			.resolution(Status.Resolution.UNRESOLVED)
			.createdOn(new Date())
			.lastModified(new Date())
			.build();

		ticket2 = Ticket.builder()
			.id(2L)
			.createdBy(1L)
			.modifiedBy(1L)
			.description("whatever 2")
			.status(Status.Ticket.NEW)
			.assigneeId(1L)
			.clientId(2L)
			.resolution(Status.Resolution.UNRESOLVED)
			.createdOn(new Date())
			.lastModified(new Date())
			.build();
	}

	@Mock
	@Autowired
	private AppUserRepository appUserRepository;

	@Mock
	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ModelMapper modelMapper;

	@InjectMocks
	private AppUserServiceImpl appUserService;

	@Test
	public void testGetAllUsers() {
		List<AppUser> appUsers = asList(appUser1, appUser2);
		when(appUserRepository.findAll()).thenReturn(appUsers);

		List<UserResponseDTO> responseDTOs = appUserService.getAllUsers();

		assertEquals(2, responseDTOs.size());
		assertEquals(appUsers.size(), responseDTOs.size());
	}

	@Test
	public void testGetUserByIdExist() {
		when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser1));

		UserResponseDTO responseDTO = appUserService.getUserById(1L);
		assertEquals(UserResponseDTO.class, responseDTO.getClass());
		assertEquals(appUser1.getId(), responseDTO.getId());
		assertEquals(appUser1.getEmail(), responseDTO.getEmail());
		assertEquals(appUser1.getPhone(), responseDTO.getPhone());
		assertEquals(appUser1.getFirstName(), responseDTO.getFirstName());
		assertEquals(appUser1.getLastName(), responseDTO.getLastName());
		assertEquals(appUser1.getUsername(), responseDTO.getUsername());
		assertEquals(appUser1.getTickets(), responseDTO.getTickets());
		assertEquals(appUser1.getLastLogin(), responseDTO.getLastLogin());
	}

	@Test
	public void testGetUserByIdNotExist() {
		when(appUserRepository.findById(999L)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> appUserService.getUserById(999L));
	}

	@Test
	public void testSave() {
		UserResponseDTO responseDTO = new UserResponseDTO();
		UserRequestDTO requestDTO = mock(UserRequestDTO.class);

		UserResponseDTO savedDTO = appUserService.save(requestDTO);

		assertEquals(responseDTO, savedDTO);
	}

	@Test
	public void testGetUserWithTickets() {
		appUser1.setTickets(asList(ticket1, ticket2));

		when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser1));

		UserResponseDTO userDTO = appUserService.getUserById(1L);

		assertNotNull(userDTO);
		assertEquals(2, userDTO.getTickets().size());
		assertEquals("whatever 1", userDTO.getTickets().get(0).getDescription());
		assertEquals("whatever 2", userDTO.getTickets().get(1).getDescription());
	}
}
