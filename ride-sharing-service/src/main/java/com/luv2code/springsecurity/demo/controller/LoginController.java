package com.luv2code.springsecurity.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.user.UserCrm;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		
		return "fancy-login";
	}
	
	// add request mapping for /access-denied
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "access-denied";
	}
	
	@GetMapping("/update-personal-info")
	public String updatePersonalInfo(Model model) {
		// String userName = ((UserDetails)principal).getUsername();
		
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		
		System.out.println("The username of the current logged in user: " + username);
		User theUser = userService.findUserByUsername(username);

		UserCrm theUserCrm = new UserCrm();
		theUserCrm.setUsername(theUser.getUserName());
		theUserCrm.setPassword(theUser.getPassword());
		theUserCrm.setConfirmedPassword(theUser.getPassword());
		theUserCrm.setFirstName(theUser.getFirstName());
		theUserCrm.setLastName(theUser.getLastName());
		theUserCrm.setEmail(theUser.getEmail());

		model.addAttribute("loggedInUser", theUserCrm);
		return "update-info-form";
	}
}