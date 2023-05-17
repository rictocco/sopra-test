package com.company.customer.service;

import com.company.customer.exception.SloganMaxReachedException;
import com.company.customer.exception.UserNotFoundException;
import com.company.customer.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SloganServiceImplTest {

	private SloganServiceInterface sloganService;
	@Mock
	private UserServiceInterface userService;

	private User user;

	@BeforeEach
	void setUp() {
		sloganService = new SloganServiceImpl(userService);

		user = new User();
		user.setId(1L);
		user.setName("John");
		user.setLastName("Doe");
		user.setAddress("123 Main St");
		user.setCity("Anytown");
		user.setEmailAddress("johndoe@example.com");
	}

	@Test
	void testAddSlogan() throws Exception {

		String sloganTest = "this is a slogan";

		when(userService.getUserById(anyLong())).thenReturn(user);

		Long sloganId = sloganService.addSlogan(user.getId(), sloganTest);

		assertNotNull(sloganId);
		verify(userService, times(1)).getUserById(anyLong());
	}

	@Test
	void testMaxSloganReached() throws Exception {

		when(userService.getUserById(anyLong())).thenReturn(user);

		Long slogan1Id = sloganService.addSlogan(user.getId(), "this is my first slogan!");
		Long slogan2Id = sloganService.addSlogan(user.getId(), "this is my second slogan!");
		Long slogan3Id = sloganService.addSlogan(user.getId(), "this is my third slogan!");

		assertNotNull(slogan1Id);
		assertNotNull(slogan2Id);
		assertNotNull(slogan3Id);


		assertThrows(SloganMaxReachedException.class, () -> sloganService.addSlogan(user.getId(), "this is my third slogan!"));

		verify(userService, times(4)).getUserById(anyLong());
	}

	@Test
	void testUserNotFound() {

		when(userService.getUserById(anyLong())).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> sloganService.addSlogan(user.getId(), "this is my first slogan!"));

		verify(userService, times(1)).getUserById(anyLong());
	}

}
