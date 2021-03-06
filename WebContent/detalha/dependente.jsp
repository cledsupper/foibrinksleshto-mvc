<%@page import="br.com.cledson.foibrinks.model.pessoal.PessoaConstantes"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.PessoaValidador"%>
<%@page import="br.com.cledson.foibrinks.bd.dao.ClienteDAO"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Dependente"%>
<%@page import="br.com.cledson.foibrinks.bd.dao.DependenteDAO"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.PessoaNaoEncontradaException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Cliente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="../cabecalho.jsp"/>

<%
	Long codigo_cliente = Long.parseLong(request.getParameter("codigo-cliente"));
	Long codigo = Long.parseLong(request.getParameter("codigo"));
	Cliente cliente = null;
	Dependente dep = null;
	try {
		cliente = ClienteDAO.le(codigo_cliente);
		dep = DependenteDAO.le(codigo);
	} catch (Exception e) {}

	String subtitulo;
	String nome = "[?]";
	if (dep == null)
		subtitulo = "erro: dependente n�o existe";
	else {
		nome = dep.getCliente().getNomeCompleto();
		int end = nome.indexOf(' ');
		nome = nome.substring(0, end > 0 ? end : nome.length());
		subtitulo = "editando dependente de " + nome;
	}
%>

<script>
	document.title = 'FoiBrinks: ' + '<%= subtitulo %>';
	document.getElementById("myNavbar").innerHTML = 'P-LISTA-CLIENTES';
</script>

<div class="container">
<%
	if (dep != null) {
		String nomeCompleto = dep.getNomeCompleto();
		String dataNascimento = dep.getDataNascimentoHTML();
		String genero = dep.getGenero();

		String depArgs = String.format("%d, %d", codigo_cliente, codigo); 

		boolean genero_outro = PessoaValidador.generoOutro(genero);
		char pronome = genero_outro || genero.equals(PessoaConstantes.STRING_GENERO_NAO_ESPECIFICAR) ?
				'e' : (genero.equals(PessoaConstantes.STRING_GENERO_FEMININO) ? 'a' : 'o');
%>
  <h1>Cadastro d<span class="letra-pronome"><%= pronome %></span> dependente de <%= nome %></h1>
<%
		String notifica_salvo = request.getParameter("notifica-salvo");
		if (notifica_salvo != null)
			out.write("<h3 class=\"aviso sucesso linha-centro\">OS DADOS FORAM SALVOS!</h3>");
%>
  <br>

  <form action="../control" method="POST">
  	<input type="hidden" name="acao" value="AtualizaDependenteAcao">
  	<input type="hidden" name="codigo-cliente" value="<%= codigo_cliente %>">
  	<input type="hidden" name="codigo" value="<%= codigo %>">

    <h3>Dados obrigat�rios</h3>
    <br>

    <p>Insira o nome completo</p>
    <div class="input-group">
      <span class="input-group-addon" id="addon-nome">Nome do dependente</span>
      <input type="text" class="form-control" name="nome" placeholder="Nome do Dependente Aqui" aria-describedby="addon-nome" value="<%= nomeCompleto %>" required>
    </div>
    <br><br>

    <p>Selecione/insira a data de nascimento</p>
    <div class="input-group">
      <span class="input-group-addon" id="addon-data-nascimento">Data de nascimento</span>
      <input type="date" class="form-control" name="data-nascimento" aria-describedby="addon-data-nascimento" value="<%= dataNascimento %>" required>
    </div>
    <br><br>

    <p>Selecione o g�nero d<span class="letra-pronome"><%= pronome %></span> dependente</p>
    <div class="input-group" id="compo-genero-grupo">
    	<span class="input-group-addon" id="addon-combo-genero">Sele��o de g�nero</span>
    	<select id="combo-genero" class="form-control" name="combo-genero" onchange="validaGenero();" aria-describedby="addon-combo-genero" required>
    		<option value="">[Selecione o g�nero]</option>
	    	<option value="MASCULINO"<% if (genero.equals("MASCULINO")) out.write(" selected=\"true\""); %>>Masculino</option>
    		<option value="FEMININO"<% if (genero.equals("FEMININO")) out.write(" selected=\"true\""); %>>Feminino</option>
    		<option value="OUTRO"<% if (genero_outro) out.write(" selected=\"true\""); %>>N�o bin�rio</option>
    		<option value="N/A"<% if (genero.equals("N/A")) out.write(" selected=\"true\""); %>>N�o especificar</option>
    	</select>
    </div>
    <div class="input-group" id="campo-genero-grupo" style="display: <%= genero_outro ? "table" : "none" %>;">
    	<span class="input-group-addon" id="addon-campo-genero">G�nero</span>
    	<input id="campo-genero" class="form-control" type="text" name="campo-genero" aria-describedby="addon-campo-genero" placeholder="neutro" value="<%= genero_outro ? genero : "" %>" maxlength="15">
    </div>
    <br><br>

    <p style="text-align: center">
    	<button class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span>	Salvar</button>
    	<button type="button" class="btn btn-danger" onclick="removeDependente(<%= depArgs %>)"><span class="glyphicon glyphicon-trash"></span>	Remover</button>
    </p>
  </form>
  <br><br>
<%
	}
	else {%>
  <h1>Dependente n�o encontrado</h1>
  <h3>Verifique se o dependente n�o foi removido</h3>
<%	}
%>
  <script src="../scripts/formatador-formulario.js"></script>
  <script src="../scripts/dependente-gerenciamento.js"></script>
</div>

<c:import url="../rodape.jsp"/>