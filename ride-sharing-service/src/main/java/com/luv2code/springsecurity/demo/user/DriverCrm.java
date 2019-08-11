package com.luv2code.springsecurity.demo.user;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DriverCrm {
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	private String licensePlateNumber;
	
	@NotNull(message="is required")
	private String carType;
	
	@NotNull(message="is required")
	@Min(value=2, message="must be greater than or equal to 2")
	@Max(value=10, message="must be less than or equal to 10")
	private Integer maxNumberOfPassengers;
	
	public DriverCrm() {}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Integer getMaxNumberOfPassengers() {
		return maxNumberOfPassengers;
	}

	public void setMaxNumberOfPassengers(Integer maxNumberOfPassengers) {
		this.maxNumberOfPassengers = maxNumberOfPassengers;
	}

	@Override
	public String toString() {
		return "DriverCrm [licensePlateNumber=" + licensePlateNumber + ", carType=" + carType
				+ ", maxNumberOfPassengers=" + maxNumberOfPassengers + "]";
	}
}