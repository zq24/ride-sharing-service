package com.luv2code.springsecurity.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springsecurity.demo.entity.RideRequest;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Autowired
	MailService mailService;

	@Override
	public void sendOrderConfirmation(RideRequest theRideRequest) {
		mailService.sendEmail(theRideRequest);
	}
}
