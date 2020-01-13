<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.PessoaNaoEncontradaException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Cliente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String string_codigo_cliente = request.getParameter("codigo-cliente");
   	Long codigo_cliente = Long.parseLong(string_codigo_cliente);
   	Cliente cliente = null;
   	try {
   		cliente = Cliente.procura(codigo_cliente);
   	} catch (Exception e) {}
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>FoiBrinks: <%
    String nome = "?";
	if (cliente == null)
		out.write("erro: cliente não existe");
	else {
		nome = cliente.getNomeCompleto();
		int end = nome.indexOf(' ');
		nome = nome.substring(0, end > 0 ? end : nome.length());
		out.write("adicionar dependente para " + nome);
	}
%></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- ARQUIVOS NECESSÁRIOS DO BOOTSTRAP E DA BIBLIOTECA JQUERY -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  
  <!-- Script que conserta a página como eu quero -->
  <script type="text/javascript" src="../scripts/formatador-basico.js"></script>
 
  <style> 
    .navbar {
      margin-bottom: 50px;
      border-radius: 0;
    }

     .jumbotron {
      margin-bottom: 0;
    }

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
      <a class="navbar-brand" href="..">TAV</a>
    </div>
    <!-- VEJA scripts/formatador-basico.js -->
    <div class="collapse navbar-collapse" id="myNavbar">P-LISTA-CLIENTES</div>
  </div>
</nav>

<div class="container">
  <h1>Cadastro d<span class="letra-pronome">o</span> dependente de <%= nome %></h1>
  <br>

  <form action="../control" method="POST">
  	<input type="hidden" name="acao" value="CadastraDependenteAcao">
  	<input type="hidden" name="codigo-cliente" value="<%= codigo_cliente %>">

    <h3>Dados obrigatórios</h3>
    <br>

    <p>Insira o nome completo</p>
    <div class="input-group">
      <span class="input-group-addon" id="addon-nome">Nome do dependente</span>
      <input type="text" class="form-control" name="nome" placeholder="Nome do Dependente Aqui" aria-describedby="addon-nome" required>
    </div>
    <br><br>

    <p>Selecione/insira a data de nascimento</p>
    <div class="input-group">
      <span class="input-group-addon" id="addon-data-nascimento">Data de nascimento</span>
      <input type="date" class="form-control" name="data-nascimento" aria-describedby="addon-data-nascimento" required>
    </div>
    <br><br>

    <p>Selecione o gênero d<span class="letra-pronome">o</span> dependente</p>
    <div class="input-group">
      <span class="input-group-addon" id="addon-combo-genero">Seleção de gênero</span>
      <select id="combo-genero" class="form-control" name="combo-genero" aria-describedby="addon-combo-genero" onchange="validaGenero();" required>
        <option value="">[Selecione o gênero]</option>
        <option value="MASCULINO">Masculino</option>
        <option value="FEMININO">Feminino</option>
        <option value="OUTRO">Não binário</option>
        <option value="N/A">Não especificar</option>
      </select>
    </div>
    <div id="campo-genero-grupo" class="input-group" style="display: none;">
      <span class="input-group-addon" id="addon-campo-genero">Gênero</span>
      <input id="campo-genero" type="text" class="form-control" name="campo-genero" maxlength="15">
    </div>
    <br><br>

    <p style="text-align: center"><button class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span>	Salvar</button></p>
  </form>
  <br><br>
  <script src="../scripts/formatador-formulario.js"></script>
</div>

<footer class="container-fluid text-center">
  <p id="leshto-copyright-footer-note"></p>
</footer>

</body>
</html>