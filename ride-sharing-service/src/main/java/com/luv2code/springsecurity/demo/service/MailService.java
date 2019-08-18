package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.entity.RideRequest;

public interface MailService {

	public void sendEmail(final RideRequest theRideRequest);
}
