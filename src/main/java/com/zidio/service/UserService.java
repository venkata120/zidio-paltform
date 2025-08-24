package com.zidio.service;

import java.util.List;

import com.zidio.Entities.User;
import com.zidio.Entities.User.Role;
import com.zidio.payload.UserDto;

public interface UserService {

	
	public UserDto createUser(UserDto userDto);
	void deleteUser(Long userId);

	List<User> getAllUsers();
	void updateUserRole(Long userId, Role role);
	void verifyDigiLockerForUser(String email);

	User findByEmail(String email);
	
}
