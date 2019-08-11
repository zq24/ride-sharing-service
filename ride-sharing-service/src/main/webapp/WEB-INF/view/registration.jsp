<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
	<title>Rider Sharing Service Registration Page</title>
</head>

<body>

	<form:form action="${pageContext.request.contextPath}/processUserForm" modelAttribute="user"
		method="POST">
		
		Username: <form:input path="username" />
		<form:errors path="username" />
		
		<br><br>
		
		Password: <form:input path="password" type="password"/>
		<form:errors path="password" />
		
		<br><br>
		
		Confirm Your Password: <form:input path="confirmedPassword" type="password"/>
		<form:errors path="confirmedPassword" />
		
		<br><br>
		
		First Name: <form:input path="firstName" />
		<form:errors path="firstName" />
		
		<br><br>
		
		Last Name: <form:input path="lastName" />
		<form:errors path="lastName" />
		
		<br><br>
		
		Email: <form:input path="email" type="email"/>
		<form:errors path="email" />
		
		<br><br>
		
		<input type="submit" value="Submit" />
			
	</form:form>

</body>

</html>