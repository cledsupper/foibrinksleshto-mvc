<%@page import="br.com.cledson.foibrinks.model.mercado.ProdutoValidador"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Dimensao"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="../cabecalho.jsp"/>

<%
	String string_codigo = request.getParameter("codigo");
	Long codigo = Long.parseLong(string_codigo);
	Produto produto = null;
	try {
		produto = Produto.procura(codigo);
	} catch (Exception e) {}

	String subtitulo;
	String nome = "[?]";
	if (produto == null)
		subtitulo = "erro: produto não existe";
	else {
		nome = produto.getNome();
		subtitulo = "editando " + nome;
	}
%>

<script>
	document.title = 'FoiBrinks: ' + '<%= subtitulo %>';
	document.getElementById("myNavbar").innerHTML = 'P-LISTA-PRODUTOS';
</script>

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
    	<span class="input-group-addon" id="addon-altura">Altura em centímetros (cm)</span>
    	<input type="number" class="form-control" name="altura" placeholder="1.23" step="0.01" aria-describedby="addon-altura" value="<%= altura %>">
    </div>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-largura">Largura em centímetros (cm)</span>
    	<input type="number" class="form-control" name="largura" placeholder="1.23" step="0.01" aria-describedby="addon-largura" value="<%= largura %>">
    </div>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-profundidade">Profundidade em centímetros (cm)</span>
    	<input type="number" class="form-control" name="profundidade" placeholder="1.23" step="0.01" aria-describedby="addon-profundidade" value="<%= profundidade %>">
    </div>
    <br>
    <p>Peso</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-peso">Peso em gramas (g)</span>
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
%>
	<br><br>
</div>

<c:import url="../rodape.jsp"/>