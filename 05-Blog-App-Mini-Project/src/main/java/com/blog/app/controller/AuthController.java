package com.blog.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.exception.ApiNotFoundException;
import com.blog.app.payload.JwtAuthRequest;
import com.blog.app.payload.JwtAuthResponse;
import com.blog.app.payload.UserDto;
import com.blog.app.security.JwtTokenHelper;
import com.blog.app.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	// Register New User

	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody UserDto dto) {
		UserDto registerNewUser = userService.registerNewUser(dto);
		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);

	}

	// login user
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");
			throw new ApiNotFoundException("Invalid Username or Password");
		}

	}
}
