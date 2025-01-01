package com.supportsystem.application.controllers;

import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.request.dtos.UserRequestDTO;
import com.supportsystem.application.response.dtos.UserResponseDTO;
import com.supportsystem.application.services.AppUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.supportsystem.application.utils.conversionUtility.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AppUserControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AppUserService service;

    @InjectMocks
    private AppUserController appUserController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appUserController).build();
    }

	private static UserResponseDTO responseDTO_1;
	private static UserResponseDTO responseDTO_2;
	private static UserRequestDTO requestDTO;

	@BeforeAll
	public static void init() {
		responseDTO_1 = new UserResponseDTO();
		responseDTO_1.setEmail("foo@bar.com");
		responseDTO_1.setFirstName("foo");
		responseDTO_1.setLastName("bar");
		responseDTO_1.setEnableFl(true);
		responseDTO_1.setId(1L);
		responseDTO_1.setPhone("8008889999");
		responseDTO_1.setUsername("foobar");

		responseDTO_2 = new UserResponseDTO();
		responseDTO_2.setEmail("baz@bar.com");
		responseDTO_2.setFirstName("baz");
		responseDTO_2.setLastName("bar");
		responseDTO_2.setEnableFl(true);
		responseDTO_2.setId(2L);
		responseDTO_2.setPhone("8008880000");
		responseDTO_2.setUsername("bazbar");

		requestDTO = new UserRequestDTO("foo", "foo@bar.com", "foo", "bar", "8008889999", "abc");

	}

	@Test
	public void testGetTickets() throws Exception {

		when(service.getAllUsers()).thenReturn(List.of(responseDTO_1, responseDTO_2));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		System.out.println(result);
		String expected = "[{id:1,username:foobar,email:foo@bar.com,firstName:foo,lastName:bar,phone:'8008889999',enableFl:true}," +
			"{id:2,username:bazbar,email:baz@bar.com,firstName:baz,lastName:bar,phone:'8008880000',enableFl:true}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetUserByIdExistingUser() throws Exception {

		when(service.getUserById(anyLong())).thenReturn(responseDTO_1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());

		String expected = "{id:1,username:foobar,email:foo@bar.com,firstName:foo,lastName:bar,phone:'8008889999',enableFl:true}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetUserByIdNonExistingUser() throws Exception {

		when(service.getUserById(anyLong())).thenThrow(new TicketNotFoundException(999L));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/999").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isNotFound());
		verify(service, times(1)).getUserById(999L);

	}

	@Test
	public void testSaveUser() throws Exception {
		when(service.save(requestDTO)).thenReturn(responseDTO_1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestDTO));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertNotNull(result);
		assertEquals(200, result.getResponse().getStatus());
		String expected = "{id:1,username:foobar,email:foo@bar.com,firstName:foo,lastName:bar,phone:'8008889999',enableFl:true}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

		verify(service, times(1)).save(requestDTO);
	}

}
