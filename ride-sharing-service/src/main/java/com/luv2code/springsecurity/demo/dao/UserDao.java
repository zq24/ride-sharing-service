package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.User;

public interface UserDao {
	
	public User getUser(int theId);

	public void save(User theUser);

	public User findUserByUsername(String username);
}
