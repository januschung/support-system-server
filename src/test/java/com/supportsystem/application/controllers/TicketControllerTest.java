package com.supportsystem.application.controllers;

import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.request.dtos.TicketRequestDTO;
import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.services.TicketCommentService;
import com.supportsystem.application.services.TicketService;
import com.supportsystem.application.shared.Status;
import com.supportsystem.application.shared.Status.Resolution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static com.supportsystem.application.utils.conversionUtility.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService service;

    @Mock
    private TicketCommentService ticketCommentService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    private static TicketResponseDTO responseDTO_1;
    private static TicketResponseDTO responseDTO_2;
    private static TicketRequestDTO requestDTO;
    private static TicketCommentResponseDTO commentDTO_ticket_1_1;
    private static TicketCommentResponseDTO commentDTO_ticket_1_2;
    private static TicketCommentResponseDTO commentDTO_ticket_2_1;

    @BeforeAll
    public static void init() {
        responseDTO_1 = new TicketResponseDTO();
        responseDTO_1.setAssigneeId(-1L);
        responseDTO_1.setClientId(-2L);
        responseDTO_1.setCreatedBy(-1L);
        responseDTO_1.setDescription("whatever");
        responseDTO_1.setId(1L);
        responseDTO_1.setModifiedBy(-1L);
        responseDTO_1.setResolution(Resolution.UNRESOLVED);
        responseDTO_1.setStatus(Status.Ticket.OPEN);

        responseDTO_2 = new TicketResponseDTO();
        responseDTO_2.setAssigneeId(-1L);
        responseDTO_2.setClientId(-2L);
        responseDTO_2.setCreatedBy(-1L);
        responseDTO_2.setDescription("whatever");
        responseDTO_2.setId(2L);
        responseDTO_2.setModifiedBy(-1L);
        responseDTO_2.setResolution(Resolution.RESOLVED);
        responseDTO_2.setStatus(Status.Ticket.CLOSED);

        commentDTO_ticket_1_1 = new TicketCommentResponseDTO();
        commentDTO_ticket_1_1.setTicketId(1L);
        commentDTO_ticket_1_1.setCreatedBy(-1L);
        commentDTO_ticket_1_1.setDescription("comment 1");

        commentDTO_ticket_1_2 = new TicketCommentResponseDTO();
        commentDTO_ticket_1_2.setTicketId(1L);
        commentDTO_ticket_1_2.setCreatedBy(-1L);
        commentDTO_ticket_1_2.setDescription("comment 2");

        commentDTO_ticket_2_1 = new TicketCommentResponseDTO();
        commentDTO_ticket_2_1.setTicketId(2L);
        commentDTO_ticket_2_1.setCreatedBy(-1L);
        commentDTO_ticket_2_1.setDescription("comment 1");

        responseDTO_1.setTicketComments(List.of(commentDTO_ticket_1_1, commentDTO_ticket_1_2));
        responseDTO_2.setTicketComments(List.of(commentDTO_ticket_2_1));

        requestDTO = new TicketRequestDTO(-1L, -1L, new Date(), "whatever",
            -1L, -1L, Status.Ticket.NEW, Resolution.UNRESOLVED);
    }

    @Test
    public void testGetTickets() throws Exception {

        when(service.getAllTickets()).thenReturn(List.of(responseDTO_1, responseDTO_2));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertNotNull(result);
        assertEquals(200, result.getResponse().getStatus());
        String expected = asJsonString(List.of(responseDTO_1, responseDTO_2));
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetTicketByIdExistingTicket() throws Exception {

        when(service.getTicketById(anyLong())).thenReturn(responseDTO_1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertNotNull(result);
        assertEquals(200, result.getResponse().getStatus());
        String expected = asJsonString(responseDTO_1);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetTicketByIdNonExistingTicket() throws Exception {

        when(service.getTicketById(anyLong())).thenThrow(new TicketNotFoundException(999L));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/999").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andDo(print())
            .andExpect(status().isNotFound());
        verify(service, times(1)).getTicketById(999L);
    }

    @Test
    public void testCreateTicket() throws Exception {
        when(service.createTicket(requestDTO)).thenReturn(responseDTO_1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tickets")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestDTO));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertNotNull(result);
        assertEquals(201, result.getResponse().getStatus());
        String expected = asJsonString(responseDTO_1);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        verify(service, times(1)).createTicket(requestDTO);
    }

    @Test
    public void testUpdateTicket() throws Exception {
        when(service.updateTicket(anyLong(), any(TicketRequestDTO.class))).thenReturn(responseDTO_1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/tickets/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestDTO));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertNotNull(result);
        assertEquals(200, result.getResponse().getStatus());
        String expected = asJsonString(responseDTO_1);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        verify(service, times(1)).updateTicket(eq(1L), any(TicketRequestDTO.class));
    }

    @Test
    public void testDeleteTicket() throws Exception {
        doNothing().when(service).deleteTicket(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/{id}", 1L)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(service, times(1)).deleteTicket(1L);
    }

    @Test
    public void testDeleteTicketNonExistingTicketThenThrowError() throws Exception {
        doThrow(new TicketNotFoundException(999L)).when(service).deleteTicket(999L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/{id}", 999L)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof TicketNotFoundException)) // Validate exception type
            .andExpect(result -> assertEquals("Could not find ticket 999", result.getResolvedException().getMessage())) // Validate exception message
            .andDo(print());

        verify(service, times(1)).deleteTicket(999L);
    }

}
