package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.RideRequest;
import com.luv2code.springsecurity.demo.entity.Sharer;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.SharerCrm;

public interface UserDao {
	
	public User getUser(int theId);

	public void save(User theUser);

	public User findUserByUsername(String username);

	public void save(RideRequest theRideRequest);

	public List<RideRequest> query(SharerCrm theSharerCrm, User user);

	public RideRequest findTheRideRequest(int rideRequestId);

	public void save(Sharer theSharer);

	public boolean checkIfJoinRide(RideRequest theRideRequest, User user);
}
