package com.luv2code.springsecurity.demo.user;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SharerCrm {
	
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
	
	@NotNull(message="is required")
	private String vehicleType;
	
	@NotNull(message="is required")
	@Min(value=1, message="must be greater than or equal to 1")
	@Max(value=4, message="must be less than or equal to 4")
	private int totalPassengers;
	
	private String specialRequest;
	
	public SharerCrm() {}

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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getTotalPassengers() {
		return totalPassengers;
	}

	public void setTotalPassengers(int totalPassengers) {
		this.totalPassengers = totalPassengers;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}

	@Override
	public String toString() {
		return "SharerCrm [currentLocation=" + currentLocation + ", destination=" + destination + ", date=" + date
				+ ", time=" + time + ", vehicleType=" + vehicleType + ", totalPassengers=" + totalPassengers
				+ ", specialRequest=" + specialRequest + "]";
	}
}
