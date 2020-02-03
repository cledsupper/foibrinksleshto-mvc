<%@page import="br.com.cledson.foibrinks.bd.dao.ProdutoDAO"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="../cabecalho.jsp"/>

<script>
	document.getElementById("myNavbar").innerHTML = "P-LISTA-PRODUTOS";
</script>

<div class="container">
<%
	ArrayList<Produto> produtos = null;
	try {
		produtos = ProdutoDAO.listaProdutos(true);
	} catch (Exception e) {
		e.printStackTrace();
		out.println("<h1 class=\"erro\">Erro ao ler dados:</h1>"
		+ "<h3>O banco de dados está conectado?</h3>");
	}

	if (produtos != null) {
		if (produtos.size() == 0) {
%>
		<h1><button type="button" onclick="voltar();" class="btn btn-secondary"><span class="glyphicon glyphicon-arrow-left"></span>Voltar</button> Vai mandar para a lua?</h1>
		<h3>Mas como se não há nenhum produto cadastrado???</h3>
		<br>
		<p class="linha-centro"><a href="../cadastra/produto.html"><button class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>	Adicionar produto</button></a></p>
<%
		} else {
%>
		<form action="clientes.jsp" method="POST">
			<h1><button type="button" onclick="voltar();" class="btn btn-secondary"><span class="glyphicon glyphicon-arrow-left"></span>Voltar</button> Vai mandar para a lua?</h1>
			<h3>Selecione os produtos</h3>
			<br>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">Adicionar</th>
						<th style="display: none">Código</th>
						<th scope="col">Nome do produto</th>
						<th scope="col">Preço (R$)</th>
						<th scope="col">Peso (g)</th>
						<th scope="col">Volume (m³)</th>
						<th scope="col">Frete estimado de Angicos para a Lua (R$)</th>
						<th scope="col">Quantidade</th>
					</tr>
				</thead>

				<tbody>
<%
			for (int i=0; i < produtos.size(); i++) {
				Produto produto = produtos.get(i);
				String codigo = String.format("%d", produto.getCodigo());
				String nome = produto.getNome();
				String preco = String.format("%.2f", produto.getPreco());
				String peso = String.format("%.1f", produto.getPeso());
				String volume = String.format("%.3f", produto.getVolume());
				String freteParaLua = String.format("%.2f", produto.getFreteParaLua());
%>
					<tr>
						<td class="linha-centro" scope="row"><input type="checkbox" name="produto-check" onchange="<%= "tornaProduto(" + i + ");" %>" ></td>
						<td style="display: none"><input class="linha-direito form-control" type="text" name="produto-codigo" value="<%= codigo %>" disabled=""></td>
						<td class="linha-centro"><button type="button" class="btn btn-primary" onclick="detalhaProduto(<%= codigo %>);"><span class="glyphicon glyphicon-edit"></span>	<%= nome %></button></td>
						<td class="linha-direito"><%= preco %></td>
						<td class="linha-direito"><%= peso %></td>
						<td class="linha-direito"><%= volume %></td>
						<td class="linha-direito"><input class="linha-direito form-control" type="text" name="produto-frete" value="<%= freteParaLua %>" readonly></td>
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
		</form>
<%
		}
	}
%>
	<br><br>
</div>


<script src="../scripts/produto-formulario.js"></script>
<script src="../scripts/produto-gerenciamento.js"></script>

<c:import url="../rodape.jsp"/>