package com.luv2code.springsecurity.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ride_request")
public class RideRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="customer_location")
	private String customerLocation;
	
	@Column(name="destination")
	private String destination;
	
	@Column(name="date_time")
	private LocalDateTime dateTime;
	
	@Column(name="vehicle_type")
	private String vehicleType;
	
	@Column(name="share_or_not")
	private String shareable;
	
	@Column(name="additional_request")
	private String specialRequest;
	
	@Column(name="total_passengers_from_your_party")
	private int totalPassengers;
	
	@Enumerated(EnumType.STRING)
	@Column(name="request_status")
	private RequestStatus requestStatus;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="user_id_number")
	private User user;
	
	@OneToMany(mappedBy="rideRequest",
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
					CascadeType.DETACH, CascadeType.REFRESH})
	private List<Sharer> listOfSharers;
	
	public RideRequest() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getShareable() {
		return shareable;
	}

	public void setShareable(String shareable) {
		this.shareable = shareable;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getTotalPassengers() {
		return totalPassengers;
	}

	public void setTotalPassengers(int totalPassengers) {
		this.totalPassengers = totalPassengers;
	}

	public List<Sharer> getListOfSharers() {
		return listOfSharers;
	}

	public void setListOfSharers(List<Sharer> listOfSharers) {
		this.listOfSharers = listOfSharers;
	}

	/*
	 * @Override public String toString() { return "RideRequest [id=" + id +
	 * ", customerLocation=" + customerLocation + ", destination=" + destination +
	 * ", dateTime=" + dateTime + ", vehicleType=" + vehicleType + ", shareable=" +
	 * shareable + ", specialRequest=" + specialRequest + ", totalPassengers=" +
	 * totalPassengers + ", requestStatus=" + requestStatus + ", user=" + user +
	 * ", listOfSharers=" + listOfSharers + "]"; }
	 */
}