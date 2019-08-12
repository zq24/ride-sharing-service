<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Request a Ride</title>
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
		action="${pageContext.request.contextPath}/processRideRequest"
		method="POST" modelAttribute="riderRequest">

		<label for="yourLocation">Your Location</label>
		<form:input path="currentLocation" id="yourLocation" />

		<br>

		<label for="destination">Destination</label>
		<form:input path="destination" id="destination" />

		<br>

		<label for="datepicker">Reserve a Date and Time:</label>
		<form:input path="date" id="datepicker" />
		<form:input path="time" class="timepicker" />

		<br>

		<input type="submit" value="Submit">
	</form:form>
</body>
</html>