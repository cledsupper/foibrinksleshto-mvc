<%@page import="br.com.cledson.foibrinks.bd.dao.DependenteDAO"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Dependente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.cledson.foibrinks.bd.dao.ClienteDAO"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Cliente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="../cabecalho.jsp"/>
<script>
	document.title="FoiBrinks: Dependentes";
	document.getElementById("myNavbar").innerHTML = "P-LISTA-CLIENTES";
</script>

<div class="container">
<%
Long codigo_cliente = Long.parseLong(request.getParameter("codigo-cliente"));
Cliente cliente = ClienteDAO.le(codigo_cliente);

String notifica_remocao = request.getParameter("notifica-remocao");
if (notifica_remocao != null)
	out.write("<h2 class=\"aviso sucesso linha-centro\">Dependente removido!</h2>");

ArrayList<Dependente> dependentes = null;
try {
	dependentes = DependenteDAO.listaDependentes(cliente);
} catch (Exception e) {
	e.printStackTrace();
	out.println("<h1 class=\"erro\">Erro ao ler dados:</h1>"
	+ "<h3>O banco de dados está conectado?</h3>");
}

if (dependentes != null) {
%>
<h1>Dependentes de <%= cliente.getNomeCompleto() %></h1>
<%
		if (dependentes.isEmpty()) { %>
			<h3>Nenhum dependente foi encontrado :?</h3>
			<br>
			<p class="linha-centro"><a href="../cadastra/dependente.jsp?codigo-cliente=<%= codigo_cliente %>"><button class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>	Cadastrar dependente</button></a></p>
<%
		} else {
%>
		<h3>Dependentes</h3>
		<br>
		<a href="../cadastra/dependente.jsp?codigo-cliente=<%= codigo_cliente %>"><button class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>	Cadastrar dependente</button></a>

		<table class="table">
			<thead>
				<tr>
					<th colspan="6" class="observe linha-centro" scope="row">Edite ou remova dependentes</th>
				</tr>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Nome do dependente</th>
					<th scope="col">O que você quer fazer?</th>
				</tr>
			</thead>
			<tbody>
<%
			for (int i=0; i < dependentes.size(); i++) {
				Dependente dep = dependentes.get(i);
				String codigo = "" + dep.getCodigo();
				String nome = dep.getNomeCompleto();
				String depArgs = String.format("%d, %d", cliente.getCodigo(), dep.getCodigo());
%>
				<tr>
					<td class="coluna-centro" scope="row"><%= codigo %></td>
					<td class="coluna-centro"><button class="btn btn-primary" onclick="detalhaDependente(<%= depArgs %>);"><%= nome %></button></td>
					<td class="coluna-centro">
						<button class="btn btn-warning" onclick="detalhaDependente(<%= depArgs %>)"><span class="glyphicon glyphicon-edit"></span>	Editar</button>
						<button class="btn btn-danger" onclick="removeDependente(<%= depArgs %>)"><span class="glyphicon glyphicon-trash"></span>	Remover</button>
					</td>
				</tr>
<%
			}
%>
			</tbody>
		</table>
<%
		}
	}
%>
		<br><br>
</div>

<script src="../scripts/dependente-gerenciamento.js"></script>

<c:import url="../rodape.jsp"/>