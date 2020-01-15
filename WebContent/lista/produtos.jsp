<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.cledson.foibrinks.bd.dac.ProdutoDAC"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <title>FoiBrinks: Produtos</title>

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
    <div class="collapse navbar-collapse" id="myNavbar">P-LISTA-PRODUTOS</div>
  </div>
</nav>

<div class="container">
<%
	String notifica_remocao = request.getParameter("notifica-remocao");
	if (notifica_remocao != null)
		out.write("<h2 class=\"aviso sucesso linha-centro\">Produto removido!</h2>");

	ArrayList<Produto> produtos = null;
	try {
		produtos = ProdutoDAC.listaProdutos(true, null, null, null);
	} catch (Exception e) {
		e.printStackTrace();
		out.println("<h1 class=\"erro\">Erro ao ler dados:</h1>"
				+ "<h3>O banco de dados está conectado?</h3>");
	}

	if (produtos != null) {
		if (produtos.size() == 0) { %>
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
					<th scope="col">Faixa etária</th>
					<th scope="col">Preço</th>
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
  <script src="../scripts/produto-formulario.js"></script>
  </form>
</div>

<footer class="container-fluid text-center">
  <p id="leshto-copyright-footer-note"></p>
</footer>

<script src="../scripts/produto-gerenciamento.js"></script>
</body>
</html>