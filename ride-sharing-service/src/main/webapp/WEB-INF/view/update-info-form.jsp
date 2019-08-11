<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>

</head>
	<title>Personal Information Update</title>
<body>

	<p>Update Your Personal Information</p>
	
	<br>
	
	<form:form action="${pageContext.request.contextPath}/updateUserForm" modelAttribute="loggedInUser"
		method="POST">
		
		<form:hidden path="username"/>
		<form:hidden path="password" />
		<form:hidden path="confirmedPassword"/>
		
		First Name: <form:input path="firstName"/>
		<form:errors path="firstName" />
		
		<br>
		
		Last Name: <form:input path="lastName"/>
		<form:errors path="lastName" />
		
		<br>
		
		Email:<form:input path="email"/>
		<form:errors path="email" />
		
		<br>
		
		<input type="submit" value="Save Your Change" />
		
	</form:form>

</body>


</html>