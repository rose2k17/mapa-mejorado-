<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<title>Mapa</title>
<style>
#right-panel {
	font-family: 'Roboto', 'sans-serif';
	line-height: 30px;
	padding-left: 10px;
}

#right-panel select, #right-panel input {
	font-size: 15px;
}

#right-panel select {
	width: 100%;
}

#right-panel i {
	font-size: 12px;
}

html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 100%;
	float: left;
	width: 63%;
	height: 100%;
}

#right-panel {
	float: right;
	width: 34%;
	height: 100%;
	overflow: auto;
}

.panel {
	height: 100%;
	overflow: auto;
}
</style>
</head>
<body>
	<%
		String origen = new String(request.getParameter("origen"));
		String destino = new String(request.getParameter("destino"));
	%>
	<div id="map"></div>
	<div id="right-panel">
		<select id="mode">
			<option value="DRIVING">Driving</option>
			<option value="WALKING">Walking</option>
			<option value="BICYCLING">Bicycling</option>
			<option value="TRANSIT">Transit</option>
		</select>
		<p>
			Total Distance: <span id="total"></span>
		</p>
	</div>
	<input id="origen" class="controls" type="hidden"
		placeholder="<%=origen%>" value="<%=origen%>">

	<input id="destino" class="controls" type="hidden"
		placeholder="<%=destino%>" value="<%=destino%>">
	<script>
		function initMap() {
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom : 4,
				center : {
					lat : -24.345,
					lng : 134.46
				}
			// Australia.
			});

			var directionsService = new google.maps.DirectionsService;
			var directionsDisplay = new google.maps.DirectionsRenderer({
				draggable : true,
				map : map,
				panel : document.getElementById('right-panel')
			});

			directionsDisplay.addListener('directions_changed', function() {
				computeTotalDistance(directionsDisplay.getDirections());
			});
			
			displayRoute(directionsService, directionsDisplay);
			
			document.getElementById('mode').addEventListener('change', function() {
				displayRoute(directionsService, directionsDisplay);
			});	
			
		}

		function displayRoute(service, display) {
			var selectedMode = document.getElementById('mode').value;
			service.route({
				origin : document.getElementById('origen').value,
				destination : document.getElementById('destino').value,
				travelMode : google.maps.TravelMode[selectedMode],
				avoidTolls : true
			}, function(response, status) {
				if (status === 'OK') {
					display.setDirections(response);
				} else {
					alert('Could not display directions due to: ' + status);
				}
			});
		}

		function computeTotalDistance(result) {
			var total = 0;
			var myroute = result.routes[0];
			for (var i = 0; i < myroute.legs.length; i++) {
				total += myroute.legs[i].distance.value;
			}
			total = total / 1000;
			document.getElementById('total').innerHTML = total + ' km';
		}
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCKtPhLTpAp84VaOouOpfYpNII05UvbVK8&callback=initMap">
		
	</script>
</body>
</html>