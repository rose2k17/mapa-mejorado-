<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registrar Artículo</title>
</head>
<body>
	<h1>Registrar Artículo</h1>
	<form action="adminArticulo?action=register" method="post">
		<table align="center">
			<tr>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td><table>
					<tr><td>origen</td></tr>
					<tr><td>destino</td></tr>
				</table>
				</td>
				<td><jsp:include page="_googlemaps.jsp"></jsp:include></td>
			</tr>
			<tr>
				<td>paquete:</td>
				<td><select name="paquete">
						<option>Grande</option>
						<option>Mediano</option>
						<option>Pequeño</option>
				</select></td>
			</tr>
			<tr>
				<td>fecha:</td>
				<td><input type="Date" name="fecha" /></td>
			</tr>
			<tr>
				<td>Remitente:</td>
				<td><input type="text" name="remitente" /></td>
			</tr>
			<tr>
				<td>transportista:</td>
				<td><input type="text" name="transportista" /></td>
			</tr>
			<tr>
				<td>Precio:</td>
				<td><input type="text" name="precio" /></td>
			</tr>

		</table>
		<br>
		<table border="0" align="center">
			<tr>
				<td><input type="submit" value="Agregar" name="agregar"></td>
			</tr>
		</table>
	</form>
</body>
</html>