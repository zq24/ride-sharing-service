package com.luv2code.springsecurity.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.UserCrm;

public interface UserService extends UserDetailsService {
	
	// find the user through the service layer
	public User findUserByUsername(String username);
	
	public void save(UserCrm theUserCrm);
	
	public void update(User theUser);
}
