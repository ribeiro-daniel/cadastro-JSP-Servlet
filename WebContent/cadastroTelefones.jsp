<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastrar telefones</title>
</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<h1>Cadastro de telefones</h1>
	<form action="salvarTelefones" method="post"
		onsubmit="return validaCampos() ? true : false">
		<table>
			<tr>
				<td>ID:</td>
				<td><input type="text" value="${usuarioTeste.id}"
					readonly="readonly"></td>
				<td><input type="text" value="${usuarioTeste.nome}"
					readonly="readonly"></td>
			</tr>

			<tr>
				<td>Número:</td>
				<td><input type="tel" name="numero" id="numero"
					placeholder="(0) 0 0000-000"></td>
				<td><select id="tipo" name="tipo">
						<option>Casa</option>
						<option>Trabalho</option>
						<option>Comercial</option>
				</select></td>
			</tr>

			<tr>
				<td></td>
				<td><input type="submit" value="Salvar"></td>
			</tr>

		</table>
	</form>
	<table>
		<tr>
			<th>ID</th>
			<th>Número</th>
			<th>Tipo</th>
			<th>Excluir</th>
		</tr>

		<c:forEach items="${telefones}" var="telefone">
			<tr>
				<td><c:out value="${telefone.id}"></c:out></td>
				<td><c:out value="${telefone.numero}"></c:out></td>
				<td><c:out value="${telefone.tipo}"></c:out></td>

				<td><a href="salvarTelefones?acao=deletarTel&tel=${telefone.id}"><img src="resources/img/excluir.png"
						width="30px" height="30px" title="Excluir" alt="Excluir"></a></td>
			</tr>
		</c:forEach>


	</table>


	<script type="text/javascript">
		function validaCampos() {
			if (document.getElementById("numero").value == '') {
				alert("O campo 'Número' não pode ficar vazio.");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>