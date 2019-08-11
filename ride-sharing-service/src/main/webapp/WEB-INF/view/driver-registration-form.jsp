<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<head>
	<title>Driver Registration</title>
</head>

<body>
	
	<form:form action="${pageContext.request.contextPath}/processDriverRegistration"
		modelAttribute="driver" method="POST">
		
		License Plate Number: <form:input path="licensePlateNumber"/>
		
		<br>
		
		Select the Type of Your Car:
		
		<form:select path="carType">
		
			<form:option value="TypeOne" label="Type 1" />
			<form:option value="TypeTwo" label="Type 2" />
			<form:option value="TypeThree" label="Type 3" />
			<form:option value="TypeFour" label="Type 4" />
		
		</form:select>
		
		<br>
		
		Maximum Number Of Passengers Allowed: <form:input path="maxNumberOfPassengers"/>
		
		<br>
		
		<input type="submit" value="Finish the Registration" />
		
	</form:form>
	
</body>
<html>