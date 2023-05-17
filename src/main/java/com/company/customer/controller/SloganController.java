package com.company.customer.controller;

import com.company.customer.exception.SloganMaxReachedException;
import com.company.customer.exception.UserNotFoundException;
import com.company.customer.service.SloganServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/campaigns")
public class SloganController {
	private final SloganServiceInterface sloganService;

	public SloganController(SloganServiceInterface sloganService) {
		this.sloganService = sloganService;
	}

	@PostMapping("/{id}/slogan")
	public ResponseEntity<Long> addSlogan(@PathVariable Long id, @RequestBody String slogan)
		throws UserNotFoundException, SloganMaxReachedException {

		Long idSlogan = sloganService.addSlogan(id, slogan);

		return new ResponseEntity<>(idSlogan, HttpStatus.OK);
	}

}
