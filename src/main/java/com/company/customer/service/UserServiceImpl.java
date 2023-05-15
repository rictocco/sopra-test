package com.company.customer.service;

import com.company.customer.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserServiceInterface {
	private final Map<Long, User> userMap = new HashMap<>();
	private static long nextId = 1L;

	@Override
	public User createUser(User user) {
		user.setId(nextId++);
		userMap.put(user.getId(), user);
		return user;
	}

	@Override
	public User getUserById(Long id) {
		return userMap.get(id);
	}

	@Override
	public User updateUser(Long id, User user) {
		User existingUser = userMap.get(id);
		if (existingUser != null) {
			user.setId(id);
			userMap.put(id, user);
			return user;
		}
		return null;
	}
}
