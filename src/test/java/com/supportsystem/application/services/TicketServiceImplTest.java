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
public class TicketServiceImplTest {

	private static Ticket ticket1 = new Ticket();
	private static Ticket ticket2 = new Ticket();

	@BeforeAll
	public static void init() {
		ticket1.setId(1L);
		ticket1.setCreatedBy(1L);
		ticket1.setModifiedBy(1L);
		ticket1.setDescription("whatever");
		ticket1.setStatus(Status.Ticket.NEW);
		ticket1.setAssigneeId(1L);
		ticket1.setClientId(1L);
		ticket1.setResolution(Status.Resolution.UNRESOLVED);
		ticket1.setCreatedOn(new Date());
		ticket1.setLastModified(new Date());

		ticket2.setId(2L);
		ticket2.setCreatedBy(2L);
		ticket2.setModifiedBy(2L);
		ticket2.setDescription("whatever");
		ticket2.setStatus(Status.Ticket.NEW);
		ticket2.setAssigneeId(2L);
		ticket2.setClientId(2L);
		ticket2.setResolution(Status.Resolution.RESOLVED);
		ticket2.setCreatedOn(new Date());
		ticket2.setLastModified(new Date());
	}

	@Mock
	@Autowired
	private TicketRepository ticketRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private TicketServiceImpl ticketService;

	@Test
	public void testGetAllTickets() {
		List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
		when(ticketRepository.findAll()).thenReturn(tickets);

		List<TicketResponseDTO> responseDTOs = ticketService.getAllTickets();
		assertEquals(tickets.size(), responseDTOs.size());
	}

	@Test
	public void testGetTicketByIdExist() {
		when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
		when(modelMapper.map(ticket1, TicketResponseDTO.class)).thenReturn(new TicketResponseDTO());

		TicketResponseDTO responseDTO = ticketService.getTicketById(1L);
		assertEquals(TicketResponseDTO.class, responseDTO.getClass());
	}

	@Test
	public void testGetTicketByIdNotExist() {
		when(ticketRepository.findById(999L)).thenReturn(Optional.empty());
		assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById(999L));
	}

	@Test
	public void testSave() {
		TicketResponseDTO responseDTO = new TicketResponseDTO();
		TicketRequestDTO requestDTO = mock(TicketRequestDTO.class);

		when(modelMapper.map(requestDTO, Ticket.class)).thenReturn(ticket1);
		when(modelMapper.map(ticket1, TicketResponseDTO.class)).thenReturn(responseDTO);

		TicketResponseDTO savedDTO = ticketService.save(requestDTO);

		assertEquals(responseDTO, savedDTO);
		verify(modelMapper).map(requestDTO, Ticket.class);
		verify(modelMapper).map(ticket1, TicketResponseDTO.class);
		verify(ticketRepository).save(ticket1);
	}
}
