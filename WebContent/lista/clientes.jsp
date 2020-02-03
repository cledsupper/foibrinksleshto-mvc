<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.cledson.foibrinks.bd.dao.ClienteDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="../cabecalho.jsp"/>

<script>
	document.getElementById("myNavbar").innerHTML = "P-LISTA-CLIENTES";
</script>

<div class="container">
<%
	String notifica_remocao = request.getParameter("notifica-remocao");
	if (notifica_remocao != null)
		out.write("<h2 class=\"aviso sucesso linha-centro\">Cliente removido!</h2>");
	ArrayList<Cliente> clientes = null;
	try {
		clientes = ClienteDAO.lista(true);
	} catch (Exception e) {
		out.println("<h1 class=\"erro\">Erro ao listar clientes</h1>\n"
		+ "<h3>O banco de dados está conectado?</h3>");
	}

	/* BLOCO-CLIENTES-NAO-NULL */
	if (clientes != null) {
		if (clientes.size() == 0) {
%>
			<h1>Nenhum cliente cadastrado :(</h1>
			<br>
			<p class="linha-centro"><a href="../cadastra/cliente.html"><button class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>	Adicionar cliente</button></a></p>
<%
		}
		/* BLOCO-EXITEM-CLIENTES */
		else {
			String[] produtos_codigos = request.getParameterValues("produto-codigo");

			/* BLOCO-VENDA */
			if (produtos_codigos != null) {
				String[] produtos_qtds = request.getParameterValues("produto-qtd");
%>
				<script>document.title = "FoiBrinks: Venda";</script>
				<form action="../control" method="POST">
			    	<input type="hidden" name="acao" value="CadastraVendaAcao">
<%				for (int i=0; i < produtos_codigos.length; i++) { %>
					<input type="hidden" name="produto-codigo" value="<%= produtos_codigos[i] %>" >
					<input type="hidden" name="produto-qtd" value="<%= produtos_qtds[i] %>" >
<%				}
%>
					<h1>Cadastro de venda</h1>
					<h3>Selecione um cliente</h3>
					<table class="table">
						<thead>
							<tr>
								<th class="observe" colspan="4" scope="row">Escolha o cliente para quem a venda está sendo feita.</th>
							</tr>
							<tr>
								<th scope="col">Selecionar</th>
								<th scope="col">Código</th>
								<th scope="col">Nome</th>
								<th scope="col">Data de nascimento</th>
							</tr>
						</thead>
						<tbody>
<%
				for (int i=0; i < clientes.size(); i++) {
					String codigo = "" + clientes.get(i).getCodigo();
					String nome = clientes.get(i).getNomeCompleto();
					String dataNascimento = clientes.get(i).getDataNascimentoStringBr();
%>
							<tr>
								<td class="linha-centro" scope="row">
<input type="radio" name="cliente-radio" value="<%= codigo %>" onchange="ativaBotao();"></td>
								<td class="linha-centro"> <%= codigo %> </td>
								<td class="linha-centro"><button type="button" class="btn btn-secondary"><%= nome %></button></td>
								<td class="linha-centro"><%= dataNascimento %></td>
							</tr>
<%				}
%>
						</tbody>
					</table>
					<br><br>

					<h3>Dados de pagamento</h3>
					<p>Forma de pagamento</p>
					<p><input type="radio" name="forma-radio" value="D" onchange="tornaForma('D');" checked="true"> Dinheiro</p>
					<p><input type="radio" name="forma-radio" value="C" onchange="tornaForma('C');"> Crédito</p>
					<p>Número do cartão: <input id="campo-numero-cartao" class="linha-direito form-control" type="text" name="numero-cartao" maxlength="16" disabled></p>
					<br><br>
					<p class="linha-centro">
						<button id="btn-vender" class="btn btn-success" disabled=""><span class="glyphicon glyphicon-shopping-cart"></span>	Registrar venda</button>
					</p>
				</form>

				<script src="../scripts/venda-formulario.js"></script>
<%			} /* BLOCO-VENDA */

			else { /* BLOCO-CLIENTES */
%>
				<script>document.title = "FoiBrinks: Clientes";</script>
				<h1>Clientes</h1>
				<h3>Na lista abaixo, você pode atualizar ou remover os dados de um cliente</h3>
				<table class="table">
					<thead>
						<tr>
							<th colspan="3" class="observe" scope="row">Esta lista mostra os últimos clientes cadastrados</th>
						</tr>
						<tr>
							<th scope="col">#</th>
							<th scope="col">Nome do cliente</th>
							<th scope="col">O que você quer fazer?</th>
						</tr>
					</thead>
					<tbody>
<%
				for (int i=0; i < clientes.size(); i++) {
					String codigo = "" + clientes.get(i).getCodigo();
					String nome = clientes.get(i).getNomeCompleto();
%>
					<tr>
						<td scope="row" class="linha-centro"><%= codigo %></td>
						<td class="linha-centro">
								<button class="btn btn-primary" onclick="detalhaCliente(<%= codigo %>);"><%= nome %></button>
						</td>
						<td class="linha-centro">
							<div class="btn-group mr-2" role="group" aria-label="Grupo de controle">
								<button onclick="detalhaCliente(<%= codigo %>);" class="btn btn-warning"><span class="glyphicon glyphicon-edit"></span>	Editar</button>
								<button onclick="removeCliente(<%= codigo %>);" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span>	Remover</button>
							</div>  
						</td>
					</tr>
<%
				}
%>
					</tbody>
				</table>
				<script src="../scripts/cliente-gerenciamento.js"></script>
<%
			} /* BLOCO-CLIENTES */
		} /* BLOCO-EXISTEM-CLIENTES */
	} /* BLOCO-CLIENTES-NAO-NULL */
%>
	<br><br>
</div>

<c:import url="../rodape.jsp"/>