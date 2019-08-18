package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.entity.RideRequest;

public interface OrderService {

	public void sendOrderConfirmation(RideRequest theRideRequest);
}
