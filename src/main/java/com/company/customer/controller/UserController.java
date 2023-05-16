package com.company.customer.controller;

import com.company.customer.model.User;
import com.company.customer.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private final UserServiceImpl userServiceImpl;

	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	@PostMapping("/signin")
	public ResponseEntity<User> createUser(@RequestBody User User) {
		User createdUser = userServiceImpl.createUser(User);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User User = userServiceImpl.getUserById(id);
		if (User == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(User, HttpStatus.OK);
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User User) {
		User updatedUser = userServiceImpl.updateUser(id, User);
		if (updatedUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
