package com.blog.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.config.AppConstants;
import com.blog.app.entity.Role;
import com.blog.app.entity.User;
import com.blog.app.exception.UserNotFoundException;
import com.blog.app.payload.UserDto;
import com.blog.app.repositories.RoleRepo;
import com.blog.app.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserRepo repo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerNewUser(UserDto dto) {

		User user = this.mapper.map(dto, User.class);
		// encoded the password
		user.setPwd(this.passwordEncoder.encode(user.getPwd()));
		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);

		User newUser = this.repo.save(user);
		return mapper.map(newUser, UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto form) {

		User user = dtoToUser(form);
		User save = repo.save(user);
		return userToDto(save);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = repo.findById(userId).orElseThrow(() -> new UserNotFoundException("user", "id", userId));
		BeanUtils.copyProperties(userDto, user);
		User updated = repo.save(user);
		return userToDto(updated);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = repo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "Id", userId));
		UserDto userDto = userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = repo.findAll();
		// to convert user in dtoUser
		List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = repo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "Id", userId));
		repo.delete(user);

	}

	private UserDto userToDto(User user) {

		UserDto dto = new UserDto();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	private User dtoToUser(UserDto dto) {
		User user = new User();
		BeanUtils.copyProperties(dto, user);
		return user;
	}

}
