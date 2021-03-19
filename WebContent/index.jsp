<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ligando a tela com  servlet - Curso JSP</title>
</head>
<body>
	<div>	
		<form action="LoginServlet" method="post" id="formularioLogin">
			<h3>SISTEMA JSP</h3>
			<input type="text" name="login" id="login" placeholder="Login" class="estiloInput" required><br>
			<input type="password" name="senha" id="senha" placeholder="Chave de acesso" class="estiloInput" required><br>
			
			<input type="submit" value="Entrar">
		</form>
	</div>
</body>
</html>