<%@page import="br.com.cledson.foibrinks.model.pessoal.Cliente"%>
<%@page import="br.com.cledson.foibrinks.bd.dac.ClienteDAC"%>
<%@page import="br.com.cledson.foibrinks.bd.dac.DependenteDAC"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Dependente"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%
Long codigo_cliente = Long.parseLong(request.getParameter("codigo-cliente"));
Cliente cliente = ClienteDAC.le(codigo_cliente);
%>
<html>
<head>
  <title>FoiBrinks: Dependentes</title>

  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- ARQUIVOS NECESSÁRIOS DO BOOTSTRAP E DA BIBLIOTECA JQUERY -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

  <!-- USAR CACHE PARA TESTAR EM DESENVOLVIMENTO QUANDO OFFLINE
  <link rel="stylesheet" href="/foiBrinksLeshto/cache/bootstrap.min.css">
  <script src="/foiBrinksLeshto/cache/jquery.min.js"></script>
  <script src="/foiBrinksLeshto/cache/bootstrap.min.js"></script>
  -->
  <!-- Script que conserta a página como eu quero -->
  <script type="text/javascript" src="../scripts/formatador-basico.js"></script>
 
  <style>
    /* Remove the navbar's default rounded borders and increase the bottom margin */ 
    .navbar {
      margin-bottom: 50px;
      border-radius: 0;
    }
    
    /* Remove the jumbotron's default bottom margin */ 
     .jumbotron {
      margin-bottom: 0;
    }
   
    /* Add a gray background color and some padding to the footer */
    footer {
      background-color: #f2f2f2;
      padding: 25px;
    }
  </style>

  <!-- Para estilizar os formulários do jeito que eu quero. -->
  <link rel="stylesheet" href="../css/formularios.css">
</head>
<body>

<div class="jumbotron">
  <div class="container text-center">
    <h1>FoiBrinks</h1>      
    <p>Terminal de Atendimento do Vendedor</p>
  </div>
</div>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">TAV</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">P-LISTA-CLIENTES</div>
  </div>
</nav>

<div class="container">
<%
	String notifica_remocao = request.getParameter("notifica-remocao");
	if (notifica_remocao != null)
		out.write("<h2 class=\"aviso sucesso linha-centro\">Dependente removido!</h2>");

	ArrayList<Dependente> dependentes = null;
	try {
		dependentes = DependenteDAC.listaDependentes(cliente);
	} catch (Exception e) {
		e.printStackTrace();
		out.println("<h1 class=\"erro\">Erro ao ler dados:</h1>"
				+ "<h3>O banco de dados está conectado?</h3>");
	}

	if (dependentes != null) { %>
		<h1>Dependentes de <%= cliente.getNomeCompleto() %></h1>
<%
		if (dependentes.size() == 0) { %>
			<h3>Nenhum dependente foi encontrado no banco de dados :(</h3>
			<br>
			<p class="linha-centro"><a href="../cadastra/dependente.jsp?codigo-cliente=<%= codigo_cliente %>"><button class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>	Cadastrar dependente</button></a></p>
<%
		} else {
%>
		<h3>Dependentes</h3>

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

<footer class="container-fluid text-center">
  <p id="leshto-copyright-footer-note"></p>
</footer>

<script src="../scripts/dependente-gerenciamento.js"></script>
</body>
</html>