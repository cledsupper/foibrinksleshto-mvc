<%@page import="br.com.cledson.foibrinks.bd.dao.VendaDAO"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Venda"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="../cabecalho.jsp"/>
<script>
	document.title = "FoiBrinks: Vendas";
	document.getElementById("myNavbar").innerHTML = "P-LISTA-VENDAS";
</script>

<div class="container">
<%
	String notifica_remocao = request.getParameter("notifica-remocao");
	if (notifica_remocao != null)
		out.write("<h2 class=\"aviso sucesso linha-centro\">Venda removida!</h2>");
	ArrayList<Venda> vendas = null;
	try {
		vendas = VendaDAO.listaVendas();
	} catch (Exception e) {
		e.printStackTrace();
		out.println("<h1 class=\"erro\">Erro ao listar vendas</h1>"
			+ "<h3>O banco de dados está conectado?</h3>");
	}

	if (vendas != null) {
		if (vendas.size() == 0) {
%>
			<h1>Nenhuma venda cadastrada :(</h1>
			<br>
			<p class="linha-centro"><a href="produtos.jsp"><button class="btn btn-primary"><span class="glyphicon glyphicon-shopping-cart"></span>	Vender</button></a></p>
<%
		} else {
%>
			<h1>Registro de vendas</h1>
			<h3>Visualize ou remova uma venda</h3>
			<table class="table">
				<thead>
					<tr>
						<th colspan="6" class="observe linha-centro" scope="row">Últimas vendas</th>
					</tr>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Vendido para</th>
						<th scope="col">Data</th>
						<th scope="col">Forma de pagamento</th>
						<th scope="col">Valor pago</th>
						<th class="linha-centro" scope="col">Ação</th>
					</tr>
				</thead>
				<tbody>
<%
	for (int i=0; i < vendas.size(); i++) {
		Venda venda = vendas.get(i);
		long codigo = venda.getCodigo();
		long codigoCliente = venda.getCliente().getCodigo();
		String nomeCliente = venda.getCliente().getNomeCompleto();
		String dataVenda = venda.getDataVendaStringBr();
		char forma_letra = venda.getPagamento().getForma();
		String forma = forma_letra == 'D' ? "Dinheiro em espécie" : "Cartão de crédito";
		String valorPago = String.format("R$ %.2f", venda.getPagamento().getValorPago());
%>
				<tr>
					<td class="linha-centro" scope="row"><%= codigo %></td>
					<td class="linha-direito">
						<button onclick="detalhaCliente(<%= codigoCliente %>);" class="btn btn-primary"><%= nomeCliente %></button>
					</td>
					<td class="linha-centro">
						<button onclick="detalhaVenda(<%= codigo %>);" class="btn btn-primary"><%= dataVenda %></button>
					</td>
					<td class="linha-centro"><%= forma %></td>
					<td class="linha-direito"><%= valorPago %></td>
					<td class="linha-centro">
						<button onclick="detalhaVenda(<%= codigo %>);" class="btn btn-primary"><span class="glyphicon glyphicon-info-sign"></span>	Detalhar</button>
						<button onclick="removeVenda(<%= codigo %>);" class="btn btn-danger" style="float: right"><span class="glyphicon glyphicon-trash"></span>	Remover</button>
					</td>
				</tr>
<%			} %>
			</tbody>
		</table>
		<br><br>
<%
		}
	}
%>
	<br><br>
</div>

<script src="../scripts/cliente-gerenciamento.js"></script>
<script src="../scripts/venda-gerenciamento.js"></script>

<c:import url="../rodape.jsp"/>