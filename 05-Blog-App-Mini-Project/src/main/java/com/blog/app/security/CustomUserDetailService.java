package com.blog.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.app.entity.User;
import com.blog.app.exception.UserNotFoundException;
import com.blog.app.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		 User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Email", "Email Id :" + email, 0));
		return user;
	}

}
