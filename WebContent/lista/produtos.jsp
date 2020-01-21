<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.cledson.foibrinks.bd.dao.ProdutoDAO"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="../cabecalho.jsp"/>

<script>
	document.getElementById("myNavbar").innerHTML = "P-LISTA-PRODUTOS";
</script>

<div class="container">
<%
	String notifica_remocao = request.getParameter("notifica-remocao");
	if (notifica_remocao != null)
		out.write("<h2 class=\"aviso sucesso linha-centro\">Produto removido!</h2>");

	ArrayList<Produto> produtos = null;
	try {
		produtos = ProdutoDAO.listaProdutos(true, null, null, null);
	} catch (Exception e) {
		e.printStackTrace();
		out.println("<h1 class=\"erro\">Erro ao ler dados:</h1>"
		+ "<h3>O banco de dados est� conectado?</h3>");
	}

	if (produtos != null) {
		if (produtos.size() == 0) {
%>
			<h1>Bem vindo!</h1>
			<h3>Nenhum produto registrado :(</h3>
			<br>
			<p class="linha-centro"><a href="../cadastra/produto.html"><button class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>	Adicionar produto</button></a></p>
<%
		} else {
%>
		<form action="clientes.jsp" method="POST">
		<h1>Cadastro de venda</h1>
		<h3>Selecione os produtos</h3>
		<table class="table">
			<thead>
				<tr>
					<th colspan="6" class="observe linha-centro" scope="row">Selecione os produtos e as quantidades deles</th>
				</tr>
				<tr>
					<th scope="col">Adicionar</th>
					<th scope="col">Codigo</th>
					<th scope="col">Nome do produto</th>
					<th scope="col">Marca</th>
					<th scope="col">Faixa et�ria</th>
					<th scope="col">Pre�o</th>
					<th class="linha-centro" scope="col">Quantidade para adicionar</th>
				</tr>
			</thead>
			<tbody>
<%
			for (int i=0; i < produtos.size(); i++) {
				Produto produto = produtos.get(i);
				String codigo = "" + produto.getCodigo();
				String nome = produto.getNome();
				String marca = produto.getMarca();
				String faixaEtaria = "" + produto.getFaixaEtaria();
				faixaEtaria = faixaEtaria.equals("0") ? "LIVRE" : faixaEtaria + " anos"; 
				String preco = String.format("R$ %.2f", produto.getPreco());
%>
				<tr>
					<td class="linha-centro" scope="row"><input type="checkbox" name="produto-check" onchange="<%= "tornaProduto(" + i + ");" %>" ></td>
					<td class="linha-direito"><input class="linha-direito form-control" type="text" name="produto-codigo" value="<%= codigo %>" disabled=""></td>
					<td class="linha-centro"><button type="button" class="btn btn-primary" onclick="detalhaProduto(<%= codigo %>);"><span class="glyphicon glyphicon-edit"></span>	<%= nome %></button></td>
					<td class="linha-centro"><%= marca %></td>
					<td class="linha-centro"><%= faixaEtaria %></td>
					<td class="linha-direito"><%= preco %></td>
					<td class="linha-centro"><input class="linha-direito form-control" type="number" name="produto-qtd" value="0" disabled=""></td>
				</tr>
<%
			}
%>
			</tbody>
		</table>
		<br><br>
		<p style="text-align: center;">
			<button id="btn-adicionar" class="btn btn-success" disabled=""><span class="glyphicon glyphicon-shopping-cart"></span>	Vender para</button></p>
<%
		}
	}
%>
		<br><br>
  </form>
</div>

<script src="../scripts/produto-formulario.js"></script>
<script src="../scripts/produto-gerenciamento.js"></script>

<c:import url="../rodape.jsp"/>