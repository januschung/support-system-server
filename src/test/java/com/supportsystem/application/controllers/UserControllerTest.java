package com.supportsystem.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import com.supportsystem.application.response.dtos.UserDTO;
import com.supportsystem.application.services.UserService;
import com.supportsystem.application.shared.Status;

@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private static UserDTO userDTO;
	
	@BeforeAll
	public static void init() {
		userDTO = new UserDTO();
		userDTO.setEmail("jare@jare.com");
		userDTO.setFirstName("firstname");
		userDTO.setLastName("lastname");
		userDTO.setEnabledFl(true);
		userDTO.setId(1L);
		userDTO.setPhone("8008889999");
		userDTO.setUsername("jare");
		
	}

	@Test
	public void testGetUserByIdExistingUser() throws Exception {
		
		when(userService.getUserById(anyLong())).thenReturn(userDTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		
		String expected = "{id:1,username:jare,email:jare@jare.com,firstName:firstname,lastName:lastname,phone:'8008889999',enabledFl:true}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void testGetUserByIdNonExistingUser() throws Exception {
		
		when(userService.getUserById(anyLong())).thenThrow(new TicketNotFoundException(999L));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/999").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isNotFound());
		verify(userService, times(1)).getUserById(999L);
//		
		
	}

}
