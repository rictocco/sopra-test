package com.company.customer.service;

import com.company.customer.error.SloganMaxReachedException;
import com.company.customer.error.UserNotFoundException;
import com.company.customer.model.Slogan;
import com.company.customer.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SloganServiceImpl implements SloganServiceInterface {
	private final Map<Long, List<Slogan>> userSloganListMap = new HashMap<>();
	private static long nextId = 1L;
	private final UserServiceInterface userService;

	public SloganServiceImpl(UserServiceInterface userService) {
		this.userService = userService;
	}

	@Override
	public Long addSlogan(Long userId, String slogan) throws Exception {
		User user = userService.getUserById(userId);
		List<Slogan> slogans;

		if (user == null) { throw new UserNotFoundException();}

		if (userSloganListMap.containsKey(userId)) {
			slogans = userSloganListMap.get(userId);
			if (slogans.size() == 3) {
				throw new SloganMaxReachedException(String.format("The user %s has reached the max slogan permitted", userId));
			}
		} else {
			slogans = new ArrayList<>();
		}
		slogans.add(Slogan.builder().id(nextId++).user(user).slogan(slogan).build());
		userSloganListMap.put(userId, slogans);
		return nextId;

	}
}
