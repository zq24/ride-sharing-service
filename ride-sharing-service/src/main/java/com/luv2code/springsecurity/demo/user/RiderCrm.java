package com.luv2code.springsecurity.demo.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RiderCrm {
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	private String currentLocation;
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	private String destination;
	
	@NotNull(message="is required")
	private String date;
	
	@NotNull(message="is required")
	private String time;
	
	public RiderCrm() {}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "RiderCrm [currentLocation=" + currentLocation + ", destination=" + destination + ", date=" + date
				+ ", time=" + time + "]";
	}
}