<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Driver Ride Requests Search</title>
	
	<!-- reference css -->
	<link type="text/css"
		rel="stylesheet"
		href="${pageContext.request.contextPath}/css/style.css" />
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Driver Ride Requests Search</h2>
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
					<th>Sharer</th>
					<th>Action</th>
				</tr>
				
				<!-- loop over and print our customers -->
				<c:forEach var="tempDriverRideSearch" items="${allRideRequests}">
				
				<!-- construct an "confirm" link with rideRequest id -->
				<c:url var="confirmLink" value="/confirmAcceptingRequest">
					<c:param name="rideRequestId" value="${tempDriverRideSearch.id}" />
				</c:url>
				
					<tr>
						<td> ${tempDriverRideSearch.user.firstName} ${tempDriverRideSearch.user.lastName} </td>
						<td> ${tempDriverRideSearch.customerLocation} </td>
						<td> ${tempDriverRideSearch.destination} </td>
						<td> ${tempDriverRideSearch.dateTime} </td>
						<td>
							<c:forEach var="tempSharer" items="${tempDriverRideSearch.listOfSharers}">
								Sharer's Name: ${tempSharer.user.firstName} ${tempSharer.user.lastName}
								<br>
								Total Passenger From this Sharer's Party: ${tempSharer.totalPassengers}
							</c:forEach>
						</td>
						<td>
							<!-- display the join link -->
							<a href="${confirmLink}"
								onclick="if (!(confirm('Are you sure to accept this ride?'))) return false">Accept</a>
						</td>
					</tr>
				</c:forEach>
				
			</table>
			
		</div>
	</div>
	
</body>

</html>