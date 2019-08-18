package com.luv2code.springsecurity.demo.service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.luv2code.springsecurity.demo.entity.RideRequest;
import com.luv2code.springsecurity.demo.entity.User;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendEmail(RideRequest theRideRequest) {

		MimeMessagePreparator preparator = getMessagePreparator(theRideRequest.getUser(), theRideRequest);

		try {
			mailSender.send(preparator);
			System.out.println("Message Send...Hurrey");
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private MimeMessagePreparator getMessagePreparator(final User theUser, final RideRequest theRideRequest) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom("customerserivces@rideSharing.com");
				mimeMessage.setRecipient(Message.RecipientType.TO,
						new InternetAddress(theUser.getEmail()));
				mimeMessage.setText("Dear " + theUser.getFirstName()
						+ ", thank you for using our services. Your ride request from " +  theRideRequest.getCustomerLocation()
						+ " to " + theRideRequest.getDestination() + " is confirmed.");
				mimeMessage.setSubject("Your Ride Request on Ride Sharing Service");
			}
		};
		return preparator;
	}
}
