<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Join Ride Search Result</title>
	
	<!-- reference css -->
	<link type="text/css"
		rel="stylesheet"
		href="${pageContext.request.contextPath}/css/style.css" />
</head>

<body>

	<c:if test="${alreadyJoin}">
		<script>
			alert("You have already joined this ride!");
		</script>
	</c:if>

	<div id="wrapper">
		<div id="header">
			<h2>Join Ride Search Result</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
			
			<!-- add the html table here -->
			
			<table>
				<tr>
					<th>Owner of this ride</th>
					<th>Pick Up Location</th>
					<th>Destination</th>
					<th>Date and Time</th>
					<th>Vehicle Type</th>
					<th>Total Passenger From Owner's Party</th>
					<th>Action</th>
				</tr>
				
				<!-- loop over and print our customers -->
				<c:forEach var="tempJoinRide" items="${joinRideSearchList}">
				
				<!-- construct an "join" link with rideRequest id -->
				<c:url var="joinLink" value="/joinARideWithOthers">
					<c:param name="rideRequestId" value="${tempJoinRide.id}" />
					<c:param name="numOfPassengersInSharerParty" value="${numOfPassengersInSharerParty}" />
					<c:param name="sharerVehicleType" value="${sharerVehicleType}"></c:param>
				</c:url>
				
					<tr>
						<td> ${tempJoinRide.user.firstName} ${tempJoinRide.user.lastName} </td>
						<td> ${tempJoinRide.customerLocation} </td>
						<td> ${tempJoinRide.destination} </td>
						<td> ${tempJoinRide.dateTime} </td>
						<td> ${tempJoinRide.vehicleType} </td>
						<td> ${tempJoinRide.totalPassengers} </td>
						<td>
							<!-- display the join link -->
							<a href="${joinLink}"
								onclick="if (!(confirm('Are you sure to join this ride?'))) return false">Join</a>
						</td>
					</tr>
				</c:forEach>
				
			</table>
			
		</div>
	</div>
	
</body>

</html>