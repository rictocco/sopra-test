package com.company.customer.service;

import com.company.customer.exception.SloganMaxReachedException;
import com.company.customer.exception.UserNotFoundException;

public interface SloganServiceInterface {

	Long addSlogan(Long userId, String slogan) throws UserNotFoundException, SloganMaxReachedException;
}
