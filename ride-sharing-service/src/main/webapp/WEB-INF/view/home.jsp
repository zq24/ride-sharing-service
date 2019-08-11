<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>

<head>

<title>luv2code Company Home Page</title>

</head>

<body>

	<h2>luv2code Company Home Page</h2>
	<hr>

	<p>Welcome to the luv2code company home page!</p>

	<hr>

	<!-- Display user name and role -->

	<p>
		User:
		<security:authentication property="principal.username" />
		<br>
		<br> Role(s):
		<security:authentication property="principal.authorities" />
	</p>

	<hr>

	<security:authorize access="hasRole('MANAGER')">
		<!-- Add a link to point to /leaders ... this is for the managers -->

		<p>
			<a href="${pageContext.request.contextPath}/leaders">LeaderShip
				Meeting</a> (Only for manager peeps)
		</p>

	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<!-- Add a link to point to /systems ... this is for the admins -->

		<p>
			<a href="${pageContext.request.contextPath}/systems">IT Systems
				Meeting</a> (Only for Admin peeps)
		</p>
	</security:authorize>
	<hr>

	<p>
		<a href="${pageContext.request.contextPath}/update-personal-info">Update
			Your Personal Information</a>
	</p>
	
	<br>
	
	<p>
		<a href="${pageContext.request.contextPath}/driverRegistration">Register to become a driver</a>
	</p>
	
	<p>
		<a href="${pageContext.request.contextPath}/rideRequest">Request a Ride</a>
	</p>

	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">

		<input type="submit" value="Logout" />

	</form:form>
</body>

</html>