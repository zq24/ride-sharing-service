package com.luv2code.springsecurity.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.luv2code.springsecurity.demo.entity.Driver;
import com.luv2code.springsecurity.demo.entity.RequestStatus;
import com.luv2code.springsecurity.demo.entity.RideRequest;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.Sharer;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.user.DriverCrm;
import com.luv2code.springsecurity.demo.user.RiderCrm;
import com.luv2code.springsecurity.demo.user.SharerCrm;
import com.luv2code.springsecurity.demo.user.UserCrm;

@Controller
@SessionAttributes( { "joinRideSearchList", "numOfPassengersInSharerParty" })
public class DemoController {

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/")
	public String showLanding() {

		return "landing";
	}

	@GetMapping("/employees")
	public String showHome() {

		return "home";
	}

	// add request mapping for /leaders
	@GetMapping("/leaders")
	public String showLeaders() {

		return "leaders";
	}

	// add request mapping for /systems
	@GetMapping("/systems")
	public String showSystems() {

		return "systems";
	}

	// add the request mapping of registration for /register
	@GetMapping("/register")
	public String showRegistrationForm(Model theModel) {
		theModel.addAttribute("user", new UserCrm());
		return "registration";
	}

	// add the request mapping of /processForm to process the submitted data
	@PostMapping("/processUserForm")
	public String processUserForm(@Valid @ModelAttribute("user") UserCrm theUser, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "registration";
		} else {
			userService.save(theUser);
			return "confirmation";
		}
	}

	// add the request mapping of /updateUserForm to process the updated user data
	@PostMapping("/updateUserForm")
	public String updateUserForm(@Valid @ModelAttribute("loggedInUser") UserCrm theUser,
			BindingResult theBindingResult, Principal thePrincipal) {

		if (theBindingResult.hasErrors()) {
			System.out.println(theBindingResult.getAllErrors());
			return "redirect:/update-personal-info";
		} else {
			String username = thePrincipal.getName();
			User user = userService.findUserByUsername(username);
			user.setFirstName(theUser.getFirstName());
			user.setLastName(theUser.getLastName());
			user.setEmail(theUser.getEmail());
			userService.update(user);
			return "redirect:/employees";
		}
	}
	
	@GetMapping("/driverRegistration")
	public String driverRegistrationForm(Model model) {
		
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		
		User user = userService.findUserByUsername(username);
		if (user.getDriver() != null) {
			return "already-signed-up";
		}
		
		DriverCrm theDriverCrm = new DriverCrm();
		model.addAttribute("driver", theDriverCrm);
		return "driver-registration-form";
	}
	
	@PostMapping("/processDriverRegistration")
	public String processDriverRegistration(@Valid @ModelAttribute("driver") DriverCrm theDriverCrm,
				BindingResult theBindingResult) {
		
		if (theBindingResult.hasErrors()) {
			System.out.println("Driver Registration Error: " + theBindingResult.getAllErrors());
			return "driver-registration-form";
		}
		
		// get the current logged in user id
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		
		// retrieve the user from the DB
		User user = userService.findUserByUsername(username);
		
		// instantiate the Driver object
		Driver theDriver = new Driver();
		theDriver.setLicensePlateNumber(theDriverCrm.getLicensePlateNumber());
		theDriver.setCarType(theDriverCrm.getCarType());
		theDriver.setMaxOccupants(theDriverCrm.getMaxNumberOfPassengers());
		
		// add the driver's information to this user
		user.setDriver(theDriver);
		//theDriver.setUser(user);
		
		// add add driver's role to the user
		Role driverRole = new Role("ROLE_DRIVER");
		user.getRoles().add(driverRole);
		
		// save it to the DB
		userService.update(user);
		return "redirect:/employees";
	}
	
	@GetMapping("/rideRequest")
	public String requestRide(Model theModel) {
		RiderCrm theRiderCrm = new RiderCrm();
		theModel.addAttribute("riderRequest", theRiderCrm);
		return "ride-request";
	}
	
	@PostMapping("/processRideRequest")
	public String processRideRequest(@Valid @ModelAttribute("riderRequest") RiderCrm theRiderCrm,
			BindingResult theBindingResult) {
		
		System.out.println("Your location: " + theRiderCrm.getCurrentLocation());
		
		System.out.println("Destination: " + theRiderCrm.getDestination());
		
		System.out.println("Date: " + theRiderCrm.getDate());
		
		System.out.println("Time: " + theRiderCrm.getTime());
		
		System.out.println("Vehicle Type: " + theRiderCrm.getVehicleType());
		
		System.out.println("Shareable: " + theRiderCrm.getShareable());
		
		System.out.println("Special Request: " + theRiderCrm.getSpecialRequest());
		
		if (theBindingResult.hasErrors()) {
			return "ride-request";
		}
		
		RideRequest theRideRequest = new RideRequest();
		theRideRequest.setCustomerLocation(theRiderCrm.getCurrentLocation());
		theRideRequest.setDestination(theRiderCrm.getDestination());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
		String dateTime = theRiderCrm.getDate() + " " + theRiderCrm.getTime();
		System.out.println(dateTime);
		LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
		
		theRideRequest.setDateTime(formatDateTime);
		theRideRequest.setVehicleType(theRiderCrm.getVehicleType());
		theRideRequest.setShareable(theRiderCrm.getShareable());
		theRideRequest.setSpecialRequest(theRiderCrm.getSpecialRequest());
		if (theRiderCrm.getShareable().equals("yes")) {
			theRideRequest.setRequestStatus(RequestStatus.SHARED_ALLOWED_OPEN);
		} else {
			theRideRequest.setRequestStatus(RequestStatus.SHARED_NOT_ALLOWED_OPEN);
		}
		theRideRequest.setTotalPassengers(theRiderCrm.getTotalPassengers());
		
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		User user = userService.findUserByUsername(username);
		
		theRideRequest.setUser(user);
		userService.update(theRideRequest);
		
		return "redirect:/employees";
	}
	
	@GetMapping("/joinRide")
	public String joinRide(Model theModel) {
		SharerCrm theSharerCrm = new SharerCrm();
		theModel.addAttribute("theSharerCrm", theSharerCrm);
		return "join-a-ride";
	}
	
	@PostMapping("/processJoinRequest")
	public String processJoinRequest(@Valid @ModelAttribute("theSharerCrm") SharerCrm theSharerCrm,
			BindingResult theBindingResult, Model theModel) {
		
		if (theBindingResult.hasErrors()) {
			return "join-a-ride";
		}
		
		// get the user id
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		User user = userService.findUserByUsername(username);
		
		List<RideRequest> joinRequestSearch = userService.query(theSharerCrm, user);
		theModel.addAttribute("joinRideSearchList", joinRequestSearch);
		theModel.addAttribute("numOfPassengersInSharerParty", theSharerCrm.getTotalPassengers());
		return "join-ride-search-result";
	}
	
	@GetMapping("/joinARideWithOthers")
	public String joinARideWithOthers(@RequestParam("rideRequestId") int rideRequestId,
			@RequestParam("numOfPassengersInSharerParty") int numOfPassengersInSharerParty,
			// @RequestParam("originalSearchResult") List<RideRequest> originalSearchResult,
			Model theModel) {
		
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		User user = userService.findUserByUsername(username);
		
		RideRequest theRideRequest = userService.findTheRideRequest(rideRequestId);
		
		// The same user cannot join the same ride more than once
		if (userService.checkIfJoinRide(theRideRequest, user)) {
			
			// let the user know they have already joined this ride
			// add the parameter theModel
			theModel.addAttribute("alreadyJoin", true);
			// theModel.addAttribute("joinRideSearchList", originalSearchResult);
			return "join-ride-search-result";
		}
		
		Sharer theSharer = new Sharer();
		
		theSharer.setTotalPassengers(numOfPassengersInSharerParty);
		theSharer.setUser(user);
		theSharer.setRideRequest(theRideRequest);
		
		userService.save(theSharer);
		
		return "redirect:/employees";
	}
	
	@GetMapping("/driverRideSearching")
	public String driverRideSearching() {
		
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		User user = userService.findUserByUsername(username);
		
		Driver theDriver = user.getDriver();
		
		return "driver-ride-search-result";
	}
}