<html>

<head>
	<title>Test Confirmation</title>
</head>

<body>

	The user is confirmed:
	
	<br>
	
	username: ${user.username}
	
	<br>
	
	Password: ${user.password}
	
	<br>
	
	Confirmed Password: ${user.confirmedPassword}
	
	<br>
	
	First Name: ${user.firstName}
	
	<br>
	
	Last Name: ${user.lastName}
	
	<br>
	
	Email: ${user.email}
	
	<br>
	
	<a href="${pageContext.request.contextPath}/">Back to the Landing Page</a>

</body>

</html>