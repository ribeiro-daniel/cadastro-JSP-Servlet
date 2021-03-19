<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>
<title>Cadastro</title>
</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<div>
		<form action="salvarUsuario" method="post" onsubmit="return validaCampos() ? true : false" enctype="multipart/form-data">
			
			<label style="color: orange;">${ msg }</label> 
			
			<input type="text"	readonly="readonly" id="id" name="id" value="${ usuario.id }" placeholder="ID"> 
			<input type="text" id="cadLogin"	name="cadLogin" value="${ usuario.login }" placeholder="Cadastrar login">
			<input type="password"	id="cadSenha" name="cadSenha" value="${ usuario.senha }" placeholder="Cadastrar chave"> 
			<input type="text" id="cadNome" name="cadNome" value="${ usuario.nome }" placeholder="Cadastrar nome">	 
			<input type="tel" id="cadTelefone" name="cadTelefone" value="${ usuario.telefone }" placeholder="Cadastrar telefone">
			<input type="text" name="cep" id="cep" value="${ usuario.cep }" placeholder="Cadastrar CEP" onblur="consultaCep()"> 
			<input type="text" name="rua" id="rua" value="${ usuario.rua }" placeholder="Rua" >
			<input type="text" name="bairro" id="bairro" placeholder="Bairro" value="${ usuario.bairro }">
			<input type="text" name="cidade" id="cidade" value="${ usuario.cidade }" placeholder="Cidade" >
			<input type="text" name="uf" id="uf" placeholder="UF"value="${ usuario.uf }" >
			<input type="text" name="ibge" id="ibge" placeholder="IBGE" value="${ usuario.ibge }"> 
			<input type="file" name="foto" value="Foto" > 
				
			<input type="submit" value="Salvar">
			<a href="salvarUsuario?acao=listar">Cancelar</a>
		</form>
		<table id="tabelaUsuarios">
			<tr>
				<th>ID</th>
				<th>Login</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>Excluir</th>
				<th>Editar</th>
				<th>Telefones</th>
			</tr>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td><c:out value="${ usuario.id }"></c:out></td>

					<td><c:out value="${ usuario.login }"></c:out></td>

					<td><c:out value="${ usuario.nome }"></c:out></td>

					<td><c:out value="${ usuario.telefone }"></c:out></td>

					<td><a href="salvarUsuario?acao=delete&id=${ usuario.id }"><img
							src="resources/img/excluir.png" width="30px" height="30px"
							alt="Excluir" title="Excluir"></a></td>

					<td><a href="salvarUsuario?acao=editar&id=${ usuario.id }"><img
							src="resources/img/editar.png" width="30px" height="30px"
							alt="Editar" title="Editar"></a></td>
					<td>
						<a href="salvarTelefones?acao=addTel&id=${usuario.id}">
						<img src="resources/img/telefone.png" width="30px" height="30px" alt="Telefones" title="Telefones">
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>

	</div>
	<script type="text/javascript">
		function validaCampos() {
			if (document.getElementById("cadLogin").value == '') {
				alert("O campo 'Login' não pode ficar vazio.'");
				return false;
			} else if (document.getElementById("cadSenha").value == '') {
				alert("O campo 'Chave' não pode ficar vazio.'");
				return false;
			} else if (document.getElementById("cadNome").value == '') {
				alert("O campo 'Nome' não pode ficar vazio.'");
				return false;
			} else if (document.getElementById("cadTelefone").value == '') {
				alert("O campo 'Telefone' não pode ficar vazio.")
				return false;
			}
			return true;
		}
		function consultaCep() {
			var cep = $("#cep").val();

			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {
						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#uf").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} else {
							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#uf").val('');
							$("#ibge").val('');
							alert("CEP não encontrado.");
						}
					});
		}
	</script>
</body>
</html>