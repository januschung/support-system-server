package com.supportsystem.application.services;

import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;
import com.supportsystem.application.repositories.TicketCommentRepository;
import com.supportsystem.application.exceptions.TicketCommentNotFoundException;
import com.supportsystem.application.domains.TicketComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketCommentServiceImplTest {

    @Mock
    private TicketCommentRepository ticketCommentRepository;

    private TicketCommentServiceImpl ticketCommentService;

    private TicketComment comment1, comment2;
    private TicketCommentResponseDTO commentDTO1, commentDTO2;

    @BeforeEach
    public void setUp() {
        ticketCommentService = new TicketCommentServiceImpl(ticketCommentRepository, new ModelMapper());
        comment1 = new TicketComment();
        comment1.setId(1L);
        comment1.setTicketId(1L);
        comment1.setDescription("First comment");

        comment2 = new TicketComment();
        comment2.setId(2L);
        comment2.setTicketId(1L);
        comment2.setDescription("Second comment");

        commentDTO1 = new TicketCommentResponseDTO();
        commentDTO1.setTicketId(1L);
        commentDTO1.setDescription("First comment");

        commentDTO2 = new TicketCommentResponseDTO();
        commentDTO2.setTicketId(1L);
        commentDTO2.setDescription("Second comment");
    }


    @Test
    public void testGetCommentsByTicketId_ExistingComments() {
        when(ticketCommentRepository.existsByTicketId(1L)).thenReturn(Boolean.TRUE);
        when(ticketCommentRepository.findAllByTicketId(1L)).thenReturn(List.of(comment1, comment2));

        List<TicketCommentResponseDTO> result = ticketCommentService.getAllTicketCommentsByTicketId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("First comment", result.get(0).getDescription());
        assertEquals("Second comment", result.get(1).getDescription());
    }

    @Test
    public void testGetCommentsByTicketId_NoComments() {
        when(ticketCommentRepository.existsByTicketId(1L)).thenReturn(Boolean.TRUE);
        when(ticketCommentRepository.findAllByTicketId(1L)).thenReturn(List.of());

        List<TicketCommentResponseDTO> result = ticketCommentService.getAllTicketCommentsByTicketId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetCommentByTicketIdAndCommentId_ExistingTicketAndComment() {
        when(ticketCommentRepository.existsByTicketId(1L)).thenReturn(Boolean.TRUE);
        when(ticketCommentRepository.findByTicketIdAndId(1L, 1L)).thenReturn(Optional.of(comment1));

        TicketCommentResponseDTO result = ticketCommentService.getCommentByTicketIdAndCommentId(1L, 1L);

        assertNotNull(result);
        assertEquals("First comment", result.getDescription());
    }

    @Test
    public void testGetCommentByTicketIdAndCommentId_ExistingTicketAndNoComment() {
        when(ticketCommentRepository.existsByTicketId(1L)).thenReturn(Boolean.TRUE);
        when(ticketCommentRepository.findByTicketIdAndId(1L, 999L)).thenReturn(Optional.empty());

        TicketCommentNotFoundException exception = assertThrows(TicketCommentNotFoundException.class, () -> {
            ticketCommentService.getCommentByTicketIdAndCommentId(1L, 999L);
        });

        assertEquals("Could not find comment 999 for ticket 1", exception.getMessage());
    }

    @Test
    public void testGetCommentByTicketIdAndCommentId_NonExistingComment() {
        TicketCommentNotFoundException exception = assertThrows(TicketCommentNotFoundException.class, () -> {
            ticketCommentService.getCommentByTicketIdAndCommentId(1L, 999L);
        });

        assertEquals("Could not find comment 999 for ticket 1", exception.getMessage());
    }
}
