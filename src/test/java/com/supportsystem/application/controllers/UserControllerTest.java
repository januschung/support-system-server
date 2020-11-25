package com.supportsystem.application.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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

import com.supportsystem.application.dtos.UserDTO;
import com.supportsystem.application.services.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;	

	@Test
	public void testGetUserByIdExistingUser() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("jare@jare.com");
		userDTO.setFirstName("firstname");
		userDTO.setLastName("lastname");
		userDTO.setEnabledFl(true);
		userDTO.setId(1L);
		userDTO.setPhone("8008889999");
		userDTO.setUsername("jare");
		
		
		when(userService.getUserById(anyLong())).thenReturn(userDTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		
		String expected = "{id:1,username:jare,email:jare@jare.com,firstName:firstname,lastName:lastname,phone:'8008889999',enabledFl:true}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
