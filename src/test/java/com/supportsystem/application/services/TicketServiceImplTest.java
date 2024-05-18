package com.supportsystem.application.services;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.repositories.TicketRepository;
import com.supportsystem.application.request.dtos.TicketRequestDTO;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.shared.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

	private static Ticket ticket1;
	private static Ticket ticket2;
	private static TicketRequestDTO requestDTO;

	@BeforeAll
	public static void init() {
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

		requestDTO = TicketRequestDTO.builder()
			.description("whatever")
			.resolution(Status.Resolution.UNRESOLVED)
			.build();
	}

	@Mock
	@Autowired
	private TicketRepository ticketRepository;

	@InjectMocks
	private TicketServiceImpl ticketService;

	@Test
	public void testGetAllTickets() {
		List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
		when(ticketRepository.findAll()).thenReturn(tickets);

		List<TicketResponseDTO> responseDTOs = ticketService.getAllTickets();

		assertEquals(2, responseDTOs.size());
		assertEquals(tickets.size(), responseDTOs.size());
	}

	@Test
	public void testGetTicketByIdExist() {
		when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));

		TicketResponseDTO responseDTO = ticketService.getTicketById(1L);
		assertEquals(TicketResponseDTO.class, responseDTO.getClass());
		assertEquals(ticket1.getId(), responseDTO.getId());
		assertEquals(ticket1.getAssigneeId(), responseDTO.getAssigneeId());
		assertEquals(ticket1.getClientId(), responseDTO.getClientId());
		assertEquals(ticket1.getCreatedBy(), responseDTO.getCreatedBy());
		assertEquals(ticket1.getCreatedOn(), responseDTO.getCreatedOn());
		assertEquals(ticket1.getDescription(), responseDTO.getDescription());
		assertEquals(ticket1.getLastModified(), responseDTO.getLastModified());
		assertEquals(ticket1.getModifiedBy(), responseDTO.getModifiedBy());
		assertEquals(ticket1.getResolution(), responseDTO.getResolution());
	}

	@Test
	public void testGetTicketByIdNotExist() {
		when(ticketRepository.findById(999L)).thenReturn(Optional.empty());
		assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById(999L));
	}

	@Test
	public void testSave() {
		TicketResponseDTO savedDTO = ticketService.save(requestDTO);
		assertEquals(requestDTO.getDescription(), savedDTO.getDescription());
		assertEquals(requestDTO.getResolution(), savedDTO.getResolution());
	}
}
