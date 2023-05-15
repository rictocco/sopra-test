package com.company.customer.service;

import com.company.customer.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
	private UserServiceImpl userService;

	User user;

	@BeforeEach
	void setUp() {
		userService = new UserServiceImpl();

		user = new User();
		user.setId(1L);
		user.setName("John");
		user.setLastName("Doe");
		user.setAddress("123 Main St");
		user.setCity("Anytown");
		user.setEmailAddress("johndoe@example.com");
	}

	@Test
	void testCreateUser() {
		User createdUser = userService.createUser(user);

		assertEquals(user.getName(), createdUser.getName());
		assertEquals(user.getLastName(), createdUser.getLastName());
		assertEquals(user.getAddress(), createdUser.getAddress());
		assertEquals(user.getCity(), createdUser.getCity());
		assertEquals(user.getEmailAddress(), createdUser.getEmailAddress());
		assertEquals(1L, createdUser.getId());
	}

	@Test
	void testGetUserById_withExistingUser() {
		userService.createUser(user);

		User retrievedUser = userService.getUserById(1L);

		assertEquals(user, retrievedUser);
	}

	@Test
	void testGetUserById_withNonExistingUser() {
		User retrievedUser = userService.getUserById(2L);

		assertNull(retrievedUser);
	}

	@Test
	void testUpdateUser_withExistingUser() {
		userService.createUser(user);

		User updatedUser = new User();
		updatedUser.setName("Updated");
		updatedUser.setLastName("User");
		updatedUser.setAddress("456 New St");
		updatedUser.setCity("Newtown");
		updatedUser.setEmailAddress("updateduser@example.com");

		User result = userService.updateUser(1L, updatedUser);

		assertEquals(1L, result.getId());
		assertEquals(updatedUser.getName(), result.getName());
		assertEquals(updatedUser.getLastName(), result.getLastName());
		assertEquals(updatedUser.getAddress(), result.getAddress());
		assertEquals(updatedUser.getCity(), result.getCity());
		assertEquals(updatedUser.getEmailAddress(), result.getEmailAddress());
	}

	@Test
	void testUpdateUser_withNonExistingUser() {
		User updatedUser = new User();
		updatedUser.setName("Updated");
		updatedUser.setLastName("User");
		updatedUser.setAddress("456 New St");
		updatedUser.setCity("Newtown");
		updatedUser.setEmailAddress("updateduser@example.com");

		User result = userService.updateUser(1L, updatedUser);

		assertNull(result);
	}
}
