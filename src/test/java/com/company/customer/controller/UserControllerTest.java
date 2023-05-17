package com.company.customer.controller;

import com.company.customer.model.User;
import com.company.customer.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userServiceImpl;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testCreateCustomer() throws Exception {
		User user = createUser();

		when(userServiceImpl.createUser(any())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.post("/signin")
				.content(objectMapper.writeValueAsString(user))
				.contentType("application/json"))
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.address").value("123 Main St"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Anytown"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("johndoe@example.com"));
	}

	@Test
	public void testGetCustomer() throws Exception {
		User user = createUser();
		when(userServiceImpl.getUserById(anyLong())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.address").value("123 Main St"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Anytown"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress").value("johndoe@example.com"));

	}

	@Test
	public void testUpdateCustomer() throws Exception {
		User user = createUser();

		when(userServiceImpl.updateUser(anyLong(), any())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.put("/updateUser/{id}", 1)
				.content(objectMapper.writeValueAsString(user))
				.contentType("application/json"))
			.andExpect(status().isOk());
	}

	private User createUser() {
		return User.builder()
			.id(1L)
			.name("John")
			.lastName("Doe")
			.address("123 Main St")
			.city("Anytown")
			.emailAddress("johndoe@example.com")
			.build();
	}
}
