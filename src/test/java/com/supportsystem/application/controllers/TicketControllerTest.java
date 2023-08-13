package com.supportsystem.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.services.TicketService;
import com.supportsystem.application.shared.Status;
import com.supportsystem.application.shared.Status.Resolution;

@WebMvcTest(TicketController.class)
class TicketControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TicketService ticketService;
	
	private static TicketResponseDTO ticketResponseDTO;
	
	@BeforeAll
	public static void init() {
		ticketResponseDTO = new TicketResponseDTO();
		ticketResponseDTO.setAssigneeId(-1L);
		ticketResponseDTO.setClientId(-2L);
		ticketResponseDTO.setCreatedBy(-1L);
		ticketResponseDTO.setDescription("whatever");
		ticketResponseDTO.setId(-999L);
		ticketResponseDTO.setModifiedBy(-1L);
		ticketResponseDTO.setResolution(Resolution.UNRESOLVED);
		ticketResponseDTO.setStatus(Status.Ticket.OPEN);
		
	}

	@Test
	public void testGetTicketByIdExistingTicket() throws Exception {
		
		when(ticketService.getTicketById(anyLong())).thenReturn(ticketResponseDTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		String expected = "{id:-999,createdBy:-1,modifiedBy:-1,assigneeId:-1,clientId:-2,description:whatever,status:OPEN,resolution:UNRESOLVED}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetTicketByIdNonExistingTicket() throws Exception {
		
		when(ticketService.getTicketById(anyLong())).thenThrow(new TicketNotFoundException(999L));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/999").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isNotFound());
		verify(ticketService, times(1)).getTicketById(999L);
		
	}

}
