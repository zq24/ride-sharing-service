package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.luv2code.springsecurity.demo.entity.RideRequest;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.SharerCrm;
import com.luv2code.springsecurity.demo.user.UserCrm;

public interface UserService extends UserDetailsService {
	
	// find the user through the service layer
	public User findUserByUsername(String username);
	
	public void save(UserCrm theUserCrm);
	
	public void update(User theUser);

	public void update(RideRequest theRideRequest);

	public List<RideRequest> query(SharerCrm theSharerCrm, User user);
}
