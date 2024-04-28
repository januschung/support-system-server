package com.supportsystem.application.controllers;

import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.request.dtos.TicketRequestDTO;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.services.TicketService;
import com.supportsystem.application.shared.Status;
import com.supportsystem.application.shared.Status.Resolution;
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

import java.util.Date;
import java.util.List;

import static com.supportsystem.application.utils.conversionUtility.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService service;

    private static TicketResponseDTO responseDTO_1;
    private static TicketResponseDTO responseDTO_2;
    private static TicketRequestDTO requestDTO;

    @BeforeAll
    public static void init() {
        responseDTO_1 = new TicketResponseDTO();
        responseDTO_1.setAssigneeId(-1L);
        responseDTO_1.setClientId(-2L);
        responseDTO_1.setCreatedBy(-1L);
        responseDTO_1.setDescription("whatever");
        responseDTO_1.setId(-999L);
        responseDTO_1.setModifiedBy(-1L);
        responseDTO_1.setResolution(Resolution.UNRESOLVED);
        responseDTO_1.setStatus(Status.Ticket.OPEN);

        responseDTO_2 = new TicketResponseDTO();
        responseDTO_2.setAssigneeId(-1L);
        responseDTO_2.setClientId(-2L);
        responseDTO_2.setCreatedBy(-1L);
        responseDTO_2.setDescription("whatever");
        responseDTO_2.setId(-998L);
        responseDTO_2.setModifiedBy(-1L);
        responseDTO_2.setResolution(Resolution.RESOLVED);
        responseDTO_2.setStatus(Status.Ticket.CLOSED);

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
        System.out.println(result);
        String expected = "[{id:-999,createdOn:null,createdBy:-1,modifiedBy:-1,lastModified:null,assigneeId:-1,clientId:-2,description:whatever,status:OPEN,resolution:UNRESOLVED}," +
            "{id:-998,createdOn:null,createdBy:-1,modifiedBy:-1,lastModified:null,assigneeId:-1,clientId:-2,description:whatever,status:CLOSED,resolution:RESOLVED}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetTicketByIdExistingTicket() throws Exception {

        when(service.getTicketById(anyLong())).thenReturn(responseDTO_1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertNotNull(result);
        assertEquals(200, result.getResponse().getStatus());
        String expected = "{id:-999,createdBy:-1,modifiedBy:-1,assigneeId:-1,clientId:-2,description:whatever,status:OPEN,resolution:UNRESOLVED}";
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
    public void testSaveTicket() throws Exception {
        when(service.save(requestDTO)).thenReturn(responseDTO_1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tickets")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestDTO));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertNotNull(result);
        assertEquals(200, result.getResponse().getStatus());
        String expected = "{id:-999,createdBy:-1,modifiedBy:-1,assigneeId:-1,clientId:-2,description:whatever,status:OPEN,resolution:UNRESOLVED}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        verify(service, times(1)).save(requestDTO);
    }

}
