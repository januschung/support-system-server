package com.supportsystem.application.services;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.exceptions.ResourceNotFoundException;
import com.supportsystem.application.repositories.TicketRepository;
import com.supportsystem.application.request.dtos.TicketRequestDTO;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.shared.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private TicketRepository ticketRepository;

    private TicketServiceImpl ticketService;

    @BeforeEach
    public void setUp() {
        // Manually initialize the ModelMapper here
        ticketService = new TicketServiceImpl(ticketRepository, new ModelMapper());
    }

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
        assertThrows(ResourceNotFoundException.class, () -> ticketService.getTicketById(999L));
    }

    @Test
    public void testCreateNewTicket() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket1);

        TicketResponseDTO savedDTO = ticketService.createTicket(requestDTO);

        assertEquals(requestDTO.getDescription(), savedDTO.getDescription());
        assertEquals(requestDTO.getResolution(), savedDTO.getResolution());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testUpdateExistingTicket() {
        Ticket existingTicket = ticket1;
        existingTicket.setDescription("updated description");

        when(ticketRepository.findById(existingTicket.getId())).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        TicketResponseDTO updatedDTO = ticketService.updateTicket(existingTicket.getId(), requestDTO);

        assertEquals(existingTicket.getDescription(), updatedDTO.getDescription());
        assertEquals(existingTicket.getResolution(), updatedDTO.getResolution());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testDeleteExistingTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));

        assertDoesNotThrow(() -> ticketService.deleteTicket(1L));

        verify(ticketRepository, times(1)).delete(ticket1);
    }

    @Test
    public void testDeleteNonExistingTicket() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> ticketService.deleteTicket(999L));
        assertEquals("Could not find Ticket with id: 999", exception.getMessage());

        verify(ticketRepository, never()).delete(any());
    }
}
