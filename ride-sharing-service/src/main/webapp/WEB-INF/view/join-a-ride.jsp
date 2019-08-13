<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Join a Ride</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script>
            $(function () {
                $("#datepicker").datepicker();
            });
        </script>
<script>
$(document).ready(function(){
    $('input.timepicker').timepicker({});
});
</script>
</head>
<body>
	<form:form
		action="${pageContext.request.contextPath}/processJoinRequest"
		method="POST" modelAttribute="theSharerCrm">

		<label for="yourLocation">Your Location</label>
		<form:input path="currentLocation" id="yourLocation" />
		<form:errors path="currentLocation" />

		<br>

		<label for="destination">Destination</label>
		<form:input path="destination" id="destination" />
		<form:errors path="destination" />

		<br>

		<label for="datepicker">Reserve a Date and Time:</label>
		<form:input path="date" id="datepicker" />
		<form:errors path="date" />
		<br>
		<form:input path="time" class="timepicker" />
		<form:errors path="time" />

		<br>
		
		<label>Choose the Vehicle Type:</label>
		<br>
		Type 1<form:radiobutton path="vehicleType" value="TypeOne" />
		Type 2<form:radiobutton path="vehicleType" value="TypeTwo" />
		Type 3<form:radiobutton path="vehicleType" value="TypeThree" />
		Type 4<form:radiobutton path="vehicleType" value="TypeFour" />
		Does Not Matter<form:radiobutton path="vehicleType" value="DoesNotMatter" />
		<form:errors path="vehicleType" />
		<br>
		
		<label for="specialRequest">Any other request?</label>
		<form:input path="specialRequest" id="specialRequest"/>
		<br>
		
		<label for="totalPassengers">Total Passengers from Your Party:</label>
		<form:input path="totalPassengers" id="totalPassengers"/>
		<form:errors path="totalPassengers" />
		<br>

		<input type="submit" value="Submit">
	</form:form>
</body>
</html>