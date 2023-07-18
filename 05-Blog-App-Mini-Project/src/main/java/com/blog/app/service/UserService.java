package com.blog.app.service;

import java.util.List;

import com.blog.app.payload.UserDto;

public interface UserService {

	public UserDto registerNewUser(UserDto dto);
	
	public UserDto createUser(UserDto user);

	public UserDto updateUser(UserDto user, Integer userId);

	public UserDto getUserById(Integer userId);

	public List<UserDto> getAllUser();

	void deleteUser(Integer userId);

}
