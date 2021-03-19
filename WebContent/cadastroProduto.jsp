<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cadastrar Produtos</title>
</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<div>

		<form action="salvarProduto" method="post"
			onsubmit="return validaCampos() ? true : false">
			<label style="color: orange;">${ msg }</label> <input type="text"
				name="id" id="id" placeholder="Código do produto"
				readonly="readonly" value="${produto.id}"> <input
				type="text" name="nome" id="nome" placeholder="Nome do produto"
				value="${produto.nome}"> <input type="number"
				name="quantidade" id="quantidade" placeholder="Quantidade" min="0"
				value="${produto.quantidade}"> <input type="number"
				name="preco" id="preco" placeholder="Preço (R$)"
				value="${produto.preco}"> <input type="submit"
				value="Salvar"> <a href="salvarProduto?acao=listar">Cancelar</a>
		</form>
		<table id="tabelaProdutos">
			<tr>
				<th>ID</th>
				<th>Nome</th>
				<th>Qtde.</th>
				<th>Preço</th>
				<th>Excluir</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td><c:out value="${produto.id}"></c:out></td>
					<td><c:out value="${produto.nome}"></c:out></td>
					<td><c:out value="${produto.quantidade}"></c:out></td>
					<td><c:out value="R$ ${produto.preco}"></c:out></td>
					<td><a href="salvarProduto?acao=excluir&id=${produto.id}"><img
							src="resources/img/excluir.png" width="30px" height="30px"
							alt="Excluir produto" title="Excluir produto"></a></td>
					<td><a href="salvarProduto?acao=editar&id=${produto.id}"><img
							src="resources/img/editar.png" width="30px" height="30px"
							alt="Editar produto" title="Editar produto"></a></td>
				</tr>
			</c:forEach>

		</table>
	</div>
	<script type="text/javascript">
		function validaCampos() {
			if (document.getElementById("nome").value == '') {
				alert("O campo 'Nome' não pode ficar vazio.'");
				return false;
			} else if (document.getElementById("quantidade").value == '') {
				alert("O campo 'Quantidade' não pode ficar vazio.'");
				return false;
			} else if (document.getElementById("preco").value == '') {
				alert("O campo 'Preço' não pode ficar vazio.'");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>