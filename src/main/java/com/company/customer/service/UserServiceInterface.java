package com.company.customer.service;

import com.company.customer.model.User;

public interface UserServiceInterface {

	User createUser(User user);
	User getUserById(Long id);
	User updateUser(Long id, User user);
}
