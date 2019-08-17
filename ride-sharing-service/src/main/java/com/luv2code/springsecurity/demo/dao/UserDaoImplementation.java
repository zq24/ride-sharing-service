package com.luv2code.springsecurity.demo.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.Driver;
import com.luv2code.springsecurity.demo.entity.RequestStatus;
import com.luv2code.springsecurity.demo.entity.RideRequest;
import com.luv2code.springsecurity.demo.entity.Sharer;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.SharerCrm;

@Repository
public class UserDaoImplementation implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getUser(int theId) {

		// get the current session
		Session currentSession = sessionFactory.getCurrentSession();

		User theUser = currentSession.get(User.class, theId);

		return theUser;
	}

	@Override
	public void save(User theUser) {

		// get the current session
		Session currentSession = sessionFactory.getCurrentSession();

		// save the user
		currentSession.saveOrUpdate(theUser);
	}

	@Override
	public User findUserByUsername(String username) {
		Session currentSession = sessionFactory.getCurrentSession();
		/*
		 * String hql = "FROM User_info U WHERE U.userName := user_name"; Query query =
		 * currentSession.createQuery(hql); query.setParameter("user_name", username);
		 * User theUser = (User) query.getSingleResult();
		 */
		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		theQuery.setParameter("uName", username);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void save(RideRequest theRideRequest) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(theRideRequest);
	}

	@Override
	public List<RideRequest> query(SharerCrm theSharerCrm, User user) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
		String dateTime = theSharerCrm.getDate() + " " + theSharerCrm.getTime();
		System.out.println(dateTime);
		LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
		
		Session currentSession = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();
		CriteriaQuery<RideRequest> criteria = builder.createQuery(RideRequest.class);
		Root<RideRequest> root = criteria.from(RideRequest.class);
		criteria.select(root);
		
		Predicate predicateForRequestStatus = builder.equal(root.get("requestStatus"), RequestStatus.SHARED_ALLOWED_OPEN);
		Predicate predicateForUser = builder.notEqual(root.get("user"), user);
		Predicate predicateForCustomerLocation = builder.equal(root.get("customerLocation"), theSharerCrm.getCurrentLocation());
		Predicate predicateForDestination = builder.equal(root.get("destination"), theSharerCrm.getDestination());
		Predicate predicateForDateTime = builder.equal(root.get("dateTime"), formatDateTime);
		
		// create a reference for checking the vehicle type
		/*
		 * Predicate predicateForVehicleType;
		 * 
		 * if (!theSharerCrm.getVehicleType().equals("DoesNotMatter")) {
		 * predicateForVehicleType = builder.equal(root.get("vehicleType"),
		 * theSharerCrm.getVehicleType()); }
		 */
		// Predicate predicateForVehicleTypeNotCare = builder.equal(root.get("vehicleType"), "DoesNotMatter");
		// Predicate predicateFinalVehicle = builder.or(predicateForVehicleType, predicateForVehicleTypeNotCare);
		Predicate finalPredicateDoesNotCareVehicleType = builder.and(predicateForRequestStatus, predicateForUser, predicateForCustomerLocation,
				predicateForDestination, predicateForDateTime);
		
		if (theSharerCrm.getVehicleType().equals("DoesNotMatter")) {
			criteria.where(finalPredicateDoesNotCareVehicleType);
		} else {
			Predicate predicateForVehicleType = builder.equal(root.get("vehicleType"), theSharerCrm.getVehicleType());
			Predicate finalPredicateDoCareVehicleType = builder.and(finalPredicateDoesNotCareVehicleType, predicateForVehicleType);
			criteria.where(finalPredicateDoCareVehicleType);
		}
		
		/*
		 * criteria.where(builder.equal(root.get("requestStatus"),
		 * RequestStatus.SHARED_ALLOWED_OPEN), builder.notEqual(root.get("user"), user),
		 * builder.equal(root.get("customerLocation"),
		 * theSharerCrm.getCurrentLocation()), builder.equal(root.get("destination"),
		 * theSharerCrm.getDestination()), builder.equal(root.get("dateTime"),
		 * formatDateTime));
		 */
		
		List<RideRequest> result = currentSession.createQuery(criteria).getResultList();
		System.out.println(result);
		return result;
	}

	@Override
	public RideRequest findTheRideRequest(int rideRequestId) {
		
		Session currentSession = sessionFactory.getCurrentSession();

		RideRequest theRideRequest = currentSession.get(RideRequest.class, rideRequestId);

		return theRideRequest;
	}

	@Override
	public void save(Sharer theSharer) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theSharer);
	}

	@Override
	public boolean checkIfJoinRide(RideRequest theRideRequest, User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();
		CriteriaQuery<Sharer> criteria = builder.createQuery(Sharer.class);
		Root<Sharer> root = criteria.from(Sharer.class);
		criteria.select(root);
		
		criteria.where(builder.equal(root.get("rideRequest"), theRideRequest),
				builder.equal(root.get("user"), user));
		
		List<Sharer> existed = currentSession.createQuery(criteria).getResultList();
		
		System.out.println(existed);
		System.out.println(existed != null);
		System.out.println(existed.size());
		
		return existed.size() != 0;
	}

	@Override
	public List<RideRequest> allRideRequests(Driver theDriver) {
		
		// get the total number of passengers that this driver's car could take
		int totalPassengers = theDriver.getMaxOccupants();
		
		// get the driver's vehicle type
		String vehicleType = theDriver.getCarType();
		
		Session currentSession = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();
		CriteriaQuery<RideRequest> criteria = builder.createQuery(RideRequest.class);
		Root<RideRequest> root = criteria.from(RideRequest.class);
		criteria.select(root);
		
		Predicate predicateForVehicleType = builder.equal(root.get("vehicleType"), vehicleType);
		Predicate predicateForVehicleTypeNotCare = builder.equal(root.get("vehicleType"), "DoesNotMatter");
		Predicate predicateFinalVehicle = builder.or(predicateForVehicleType, predicateForVehicleTypeNotCare);
		Predicate predicateDriverCannotAcceptOwnRequest = builder.notEqual(root.get("user"), theDriver.getUser());
		Predicate finalPredicate = builder.and(predicateFinalVehicle, predicateDriverCannotAcceptOwnRequest);
		criteria.where(finalPredicate);
		
		List<RideRequest> rideRequestList = currentSession.createQuery(criteria).getResultList();
		for (int i = 0; i < rideRequestList.size();) {
			
			// get a particular ride request
			RideRequest temp = rideRequestList.get(i);
			
			// get the request owner's party size
			int requestOwnerPartySize = temp.getTotalPassengers();
			
			// get the list of sharers for this particular ride request
			List<Sharer> listOfSharers = temp.getListOfSharers();
			
			// record the total number of sharers
			int totalNumberOfSharers = 0;
			
			for (int j = 0; j < listOfSharers.size(); j++) {
				totalNumberOfSharers += listOfSharers.get(j).getTotalPassengers();
			}
			
			int totalPassengersInThisRequest = requestOwnerPartySize + totalNumberOfSharers;
			
			if (totalPassengersInThisRequest > totalPassengers) {
				rideRequestList.remove(i);
			} else {
				i += 1;
			}
		}
		return rideRequestList;
	}
}