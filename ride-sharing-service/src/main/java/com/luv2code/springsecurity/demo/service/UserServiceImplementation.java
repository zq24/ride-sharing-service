package com.luv2code.springsecurity.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.UserDao;
import com.luv2code.springsecurity.demo.entity.RideRequest;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.Sharer;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.SharerCrm;
import com.luv2code.springsecurity.demo.user.UserCrm;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserDao userDao;

	/*
	 * @Autowired private RoleDao roleDao;
	 */

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User theUser = userDao.findUserByUsername(username);
		if (theUser == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(theUser.getUserName(), theUser.getPassword(),
				getAuthority(theUser.getRoles()));
	}

	@Override
	@Transactional
	public void save(UserCrm theUserCrm) {
		User theUser = new User();
		theUser.setUserName(theUserCrm.getUsername());
		theUser.setPassword(passwordEncoder.encode(theUserCrm.getPassword()));
		theUser.setFirstName(theUserCrm.getFirstName());
		theUser.setLastName(theUserCrm.getLastName());
		theUser.setEmail(theUserCrm.getEmail());
		theUser.setRoles(Arrays.asList(new Role("ROLE_EMPLOYEE")));
		userDao.save(theUser);
	}

	@Override
	@Transactional
	public User findUserByUsername(String username) {
		User theUser = userDao.findUserByUsername(username);
		return theUser;
	}

	private List<GrantedAuthority> getAuthority(List<Role> roleList) {
		List<GrantedAuthority> list = new ArrayList<>();
		for (Role role : roleList) {
			list.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		System.out.println(list);
		return list;
	}

	@Override
	@Transactional
	public void update(User theUser) {
		userDao.save(theUser);
	}

	@Override
	@Transactional
	public void update(RideRequest theRideRequest) {
		userDao.save(theRideRequest);
	}

	@Override
	@Transactional
	public List<RideRequest> query(SharerCrm theSharerCrm, User user) {
		
		List<RideRequest> joinRequestList = userDao.query(theSharerCrm, user);
		return joinRequestList;
	}

	@Override
	@Transactional
	public RideRequest findTheRideRequest(int rideRequestId) {
		RideRequest theRideRequest = userDao.findTheRideRequest(rideRequestId);
		return theRideRequest;
	}

	@Override
	@Transactional
	public void save(Sharer theSharer) {
		userDao.save(theSharer);
	}

	@Override
	@Transactional
	public boolean checkIfJoinRide(RideRequest theRideRequest, User user) {
		return userDao.checkIfJoinRide(theRideRequest, user);
	}
}