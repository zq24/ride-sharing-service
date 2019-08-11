package com.luv2code.springsecurity.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="driver")
public class Driver {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="license_plate_number")
	private String licensePlateNumber;
	
	@Column(name="car_type")
	private String carType;
	
	@Column(name="max_number_passengers_allowed")
	private int maxOccupants;
	
	@OneToOne(mappedBy="driver", cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH})
	private User user;
	
	public Driver() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getMaxOccupants() {
		return maxOccupants;
	}

	public void setMaxOccupants(int maxOccupants) {
		this.maxOccupants = maxOccupants;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Driver [id=" + id + ", licensePlateNumber=" + licensePlateNumber + ", carType=" + carType
				+ ", maxOccupants=" + maxOccupants + "]";
	}
}