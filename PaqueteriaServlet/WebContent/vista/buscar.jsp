<!-- Código a abrir como html en el navegador donde aparece mapa y barra búsquedas con predicción de texto -->
<!-- Places search box. https://developers.google.com/maps/documentation/javascript/examples/places-searchbox -->
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Buscar</title>
  </head>
  <body>
  	<form action="adminArticulo?action=tabla" method="post">
	  <jsp:include page="_googlemaps.jsp"></jsp:include>
	  <input type=submit value=Mostrar>
  	</form>
    <a href="adminArticulo?action=mostrar">Mostrar</a>
  </body>
</html>