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
		Predicate predicateForVehicleType = builder.equal(root.get("vehicleType"), theSharerCrm.getVehicleType());
		Predicate predicateForVehicleTypeNotCare = builder.equal(root.get("vehicleType"), "DoesNotMatter");
		Predicate predicateFinalVehicle = builder.or(predicateForVehicleType, predicateForVehicleTypeNotCare);
		Predicate finalPredicate = builder.and(predicateForRequestStatus, predicateForUser, predicateForCustomerLocation,
				predicateForDestination, predicateForDateTime, predicateFinalVehicle);
		criteria.where(finalPredicate);
		
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
}