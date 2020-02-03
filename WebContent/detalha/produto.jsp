<%@page import="br.com.cledson.foibrinks.model.mercado.ProdutoValidador"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Dimensao"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String string_codigo = request.getParameter("codigo");
	Long codigo = Long.parseLong(string_codigo);
	Produto produto = null;
	try {
		produto = Produto.procura(codigo);
	} catch (Exception e) {}
%>
<!DOCTYPE html>
<html>
<head>
  <title>FoiBrinks: <%
	String nome = "?";
	if (produto == null)
		out.write("erro: produto não existe");
	else {
		nome = produto.getNome();
		out.write("editando " + nome);
	}
  %></title>

  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- ARQUIVOS NECESSÁRIOS DO BOOTSTRAP E DA BIBLIOTECA JQUERY -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  
  <!-- Script que conserta a página como eu quero. -->
  <script type="text/javascript" src="../scripts/formatador-basico.js"></script>

  <!-- Veja modelo.jsp para saber o que essas coisas significam -->
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
      <a class="navbar-brand" href="..">TAV</a>
    </div>
    <!-- VEJA scripts/formatador-basico.js -->
    <div class="collapse navbar-collapse" id="myNavbar">P-LISTA-PRODUTOS</div>
  </div>
</nav>

<div class="container">
<%	if (produto != null) {
		String dataCadastro = produto.getDataCadastroStringBr();
		String marca = produto.getMarca();
		short faixaEtaria = produto.getFaixaEtaria();
		boolean faixaEtariaOutro = ProdutoValidador.faixaEtariaOutro(faixaEtaria);

		Double altura = produto.getDimensao(Dimensao.Enum.Altura);
		Double largura = produto.getDimensao(Dimensao.Enum.Largura);
		Double profundidade = produto.getDimensao(Dimensao.Enum.Profundidade);
		Double peso = produto.getPeso();
		Double preco = produto.getPreco(); %>
  <h1><button type="button" onclick="voltar();" class="btn btn-secondary"><span class="glyphicon glyphicon-arrow-left"></span>Voltar</button>	Cadastro de Produto</h1>
  <br>
  <h4 class="observe">Produto cadastrado em: <%= dataCadastro %></h4>
  <br>
<%
		String notifica_salvo = request.getParameter("notifica-salvo");
		if (notifica_salvo != null) {
%>
			<h3 class="aviso sucesso linha-centro">OS DADOS FORAM SALVOS!</h3>
<%		}
		String notifica_erro = request.getParameter("notifica-erro");
		if (notifica_erro != null) { %>
			<h3 class="aviso erro linha-centro">ERRO AO REMOVER</h3>
			<h4 class="aviso erro linha-centro">Produtos vendidos não podem ser removidos.</h4>
<%		} %>
  <br>

  <form action="../control?codigo=<%= codigo %>" method="POST">
    <input type="hidden" name="acao" value="AtualizaProdutoAcao">

    <h3>Dados obrigatórios</h3>
    <br>
    <p>Insira o nome</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-nome">Nome do produto</span>
    	<input type="text" class="form-control" name="nome" placeholder="Bola de Futebol" aria-describedby="addon-nome" value="<%= nome %>" required>
    </div>
    <br><br>

    <p>Insira a marca</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-marca">Marca ou nome da fabricante</span>
    	<input type="text" class="form-control" name="marca" placeholder="SuperBola" aria-describedby="addon-marca" value="<%= marca %>" required>
    </div>
    <br><br>

    <p>Insira a faixa etária</p>
    <p class="observe">A faixa etária do produto deve ser menor ou igual a 14 anos, mas você pode definir outro valor que não consta na lista, desde que respeite essa restrição.</p>
    <div class="input-group">
      <span class="input-group-addon" id="addon-combo-faixa-etaria">Seleção de faixa etária</span>
      <select id="combo-faixa-etaria" class="form-control" name="combo-faixa-etaria" aria-describedby="addon-combo-faixa-etaria" onchange="validaFaixaEtaria();" required>
        <option value="">[Selecione a faixa etária]</option>
        <option value="0"<% if (faixaEtaria == 0) out.write(" selected=\"true\""); %>>Livre</option>
        <option value="5"<% if (faixaEtaria == 5) out.write(" selected=\"true\""); %>>5 anos</option>
        <option value="10"<% if (faixaEtaria == 10) out.write(" selected=\"true\""); %>>10 anos</option>
        <option value="14"<% if (faixaEtaria == 14) out.write(" selected=\"true\""); %>>14 anos</option>
        <option value="OUTRO"<% if (faixaEtariaOutro) out.write(" selected=\"true\""); %>>Especificar</option>
      </select>
    </div>
    <div id="campo-faixa-etaria-grupo" class="input-group" style="display: <%= faixaEtariaOutro ? "table" : "none" %>;">
    	<span class="input-group-addon" id="addon-campo-faixa-etaria">Faixa etária</span>
    	<input id="campo-faixa-etaria" class="form-control" type="number" name="campo-faixa-etaria" placeholder="3" maxlength="2" aria-describedby="addon-campo-faixa-etaria" oninput="validaFaixaEtaria();"  value="<%= faixaEtariaOutro ? faixaEtaria : "" %>">
    </div>
    <br><br>

    <h3>Os dados seguintes não são obrigatórios, mas é recomendado preenchê-los.</h3>
    <p>Você pode alterá-los depois.</p>
    <br>
    <p class="observe linha-centro">Por favor, preencha os campos conforme o exemplo: 1234,50</p>
    <br>

    <h4>Dimensões</h4>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-altura">Altura</span>
    	<input type="number" class="form-control" name="altura" placeholder="1.23" step="0.01" aria-describedby="addon-altura" value="<%= altura %>">
    </div>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-largura">Largura</span>
    	<input type="number" class="form-control" name="largura" placeholder="1.23" step="0.01" aria-describedby="addon-largura" value="<%= largura %>">
    </div>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-profundidade">Profundidade</span>
    	<input type="number" class="form-control" name="profundidade" placeholder="1.23" step="0.01" aria-describedby="addon-profundidade" value="<%= profundidade %>">
    </div>
    <br>
    <p>Peso</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-peso">Peso (g)</span>
    	<input type="number" class="form-control" name="peso" placeholder="1.23" step="0.01" aria-describedby="addon-peso" value="<%= peso %>">
    </div>
    <br><br>
    <h3>Preço</h3>
    <h4>Insira o preço do produto</h4>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-preco">R$</span>
    	<input type="number" class="form-control" name="preco" placeholder="2.99" step="0.01" aria-describedby="addon-preco" value="<%= preco %>" required>
    </div>
    <br><br>

    <p style="text-align: center"><button class="btn btn-success"><span class="glyphicon glyphicon-floppy-disk"></span>	Salvar</button> <button type="button" class="btn btn-danger" onclick="removeProduto(<%= codigo %>);"><span class="glyphicon glyphicon-trash"></span>	Remover</button></p>
  </form>
  <script src="../scripts/formatador-formulario.js"></script>
  <script src="../scripts/produto-gerenciamento.js"></script>
  
<%
	}
	else {
%>
		<h1>Produto não encontrado</h1>
		<h3>Verifique se o produto não foi removido.</h3>
<%	}
	out.write("<br><br>");
%>
</div>

<footer class="container-fluid text-center">
  <p id="leshto-copyright-footer-note"></p>
</footer>

</body>
</html>