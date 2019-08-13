package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.RequestStatus;
import com.luv2code.springsecurity.demo.entity.RideRequest;
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
		
		Session currentSession = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();
		CriteriaQuery<RideRequest> criteria = builder.createQuery(RideRequest.class);
		Root<RideRequest> root = criteria.from(RideRequest.class);
		criteria.select(root);
		
		criteria.where(builder.equal(root.get("requestStatus"), RequestStatus.SHARED_ALLOWED_OPEN),
				builder.notEqual(root.get("user"), user));
		
		List<RideRequest> result = currentSession.createQuery(criteria).getResultList();
		System.out.println(result);
		return result;
	}
}