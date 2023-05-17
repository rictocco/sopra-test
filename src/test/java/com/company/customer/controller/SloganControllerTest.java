package com.company.customer.controller;

import com.company.customer.exception.SloganMaxReachedException;
import com.company.customer.exception.UserNotFoundException;
import com.company.customer.service.SloganServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SloganController.class)
class SloganControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SloganServiceInterface sloganService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
	}

	@Test
	public void testAddSlogan() throws Exception {
		String slogan = "This is a test slogan!";

		when(sloganService.addSlogan(anyLong(), anyString())).thenReturn(1L);

		mockMvc.perform(MockMvcRequestBuilders.post("/{id}/slogan", 1L)
				.content(objectMapper.writeValueAsString(slogan))
				.contentType("application/json"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("1")));
	}

	@Test
	public void testAddSlogan_UserNotFound() throws Exception {
		String slogan = "This is a test slogan!";

		when(sloganService.addSlogan(anyLong(), anyString())).thenThrow(new UserNotFoundException("user not found"));

		mockMvc.perform(MockMvcRequestBuilders.post("/{id}/slogan", 1L)
				.content(objectMapper.writeValueAsString(slogan))
				.contentType("application/json"))
			.andExpect(status().isNotFound());
	}

	@Test
	public void testAddSlogan_MaximumSloganReached() throws Exception {
		String slogan = "This is a test slogan!";

		when(sloganService.addSlogan(anyLong(), anyString())).thenThrow(new SloganMaxReachedException("maximum slogan reached!"));

		mockMvc.perform(MockMvcRequestBuilders.post("/{id}/slogan", 1L)
				.content(objectMapper.writeValueAsString(slogan))
				.contentType("application/json"))
			.andExpect(status().isBadRequest());
	}


}
