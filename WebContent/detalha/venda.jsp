<%@page import="br.com.cledson.foibrinks.model.pagamento.Cartao"%>
<%@page import="br.com.cledson.foibrinks.model.pagamento.Dinheiro"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.ProdutoAdicionador"%>
<%@page import="br.com.cledson.foibrinks.bd.dao.VendaDAO"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Venda"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="../cabecalho.jsp"/>
<%
	String string_codigo = request.getParameter("codigo");
	Long codigo = Long.parseLong(string_codigo);
	Venda venda = null;
	try {
		venda = VendaDAO.le(codigo);
	} catch (Exception e) {}
	
	String subtitulo;
	if (venda == null)
		subtitulo = "erro: venda não existe";
	else
		subtitulo = "venda " + venda.getCodigo();
%>

<script>
	document.title = 'FoiBrinks: ' + '<%= subtitulo %>';
	document.getElementById("myNavbar").innerHTML = 'P-LISTA-VENDAS';
</script>

<div class="container">
<%	if (venda != null) {
		Long codigoCliente = venda.getCliente().getCodigo();
		String nomeCliente = venda.getCliente().getNomeCompleto();
		ArrayList<ProdutoAdicionador> carrinho = venda.getCarrinho();
%>
  <h1>Venda num. <%= codigo %></h1>
  <br>
  <h4 class="observe">Venda para <%= nomeCliente %> realizada em <%= venda.getDataVendaStringBr() %></h4>
  <br>
<%
		String notifica_erro = request.getParameter("notifica-erro");
		if (notifica_erro != null) { %>
			<h3 class="erro linha-centro">ERRO AO REMOVER</h3>
			<h4 class="erro linha-centro">Contate o suporte técnico.</h4>
<%		}
		double custoTotal = venda.geraCustoTotal();
		double impostos = custoTotal*Constantes.VALOR_IMPOSTO;
		char forma = venda.getPagamento().getForma();
		String string_custoTotal = String.format("%.2f", custoTotal);
		String string_impostos = String.format("%.2f", impostos);
		String string_forma = forma == Dinheiro.FORMA ?
				Constantes.STRING_FORMA_DINHEIRO : Constantes.STRING_FORMA_CARTAO;
		String valorPago = String.format("%.2f", venda.getPagamento().getValorPago());
%>
  <br>

  <h3>Dados da venda</h3>
  <br>
  <h4>Cliente e vendedor</h4>
  <div class="input-group">
  	<span class="input-group-addon" id="addon-cliente-nome">Cliente</span>
  	<button onclick="detalhaCliente(<%= venda.getCliente().getCodigo() %>);" class="btn btn-primary form-control" aria-describedby="addon-cliente-nome"><%= nomeCliente %></button>
  </div>
  <div class="input-group">
  	<span class="input-group-addon" id="addon-vendedor-nome">Vendedor</span>
  	<input type="text" class="form-control" aria-describedby="addon-vendedor-nome" value="Terminal" readonly>
  </div>
  <p class="linha-centro"></p>
  <br>

  <h4>Custos e pagamento</h4>
  <div class="input-group">
    <span class="input-group-addon" id="addon-custo-total">Custo total R$</span>
    <input type="text" class="form-control" aria-describedby="addon-custo-total" value="<%= string_custoTotal %>" readonly>
  </div>
  <div class="input-group">
  	<span class="input-group-addon" id="addon-imposto">Tributos totais R$</span>
  	<input type="text" class="form-control" aria-describedby="addon-imposto" value="<%= string_impostos %>" readonly>
  </div>
  <br>

  <h4>Forma de pagamento</h4>
  <div class="input-group">
    <span class="input-group-addon" id="addon-forma">Forma de pagamento</span>
    <input type="text" class="form-control" aria-describedby="addon-forma" value="<%= string_forma %>" readonly>
  </div>
<%
	if (forma == Cartao.FORMA) {
		String numeroCartao = "" + ((Cartao)venda.getPagamento()).getNumeroCartao();
		numeroCartao = "**** **** **** " + numeroCartao.substring(numeroCartao.length()-4, numeroCartao.length());
%>
		<div class="input-group">
			<span class="input-group-addon" id="addon-numero-cartao">Cartão de crédito</span>
			<input type="text" class="form-control" aria-describedby="addon-numero-cartao" value="<%= numeroCartao %>" readonly>
		</div>
<%
	}
%>
  <div class="input-group">
		<span class="input-group-addon" id="addon-valor-pago">Valor pago R$</span>
		<input type="text" class="form-control" aria-describedby="addon-valor-pago" value="<%= valorPago %>" readonly>
  </div>
  <br><br>

  <h3>Produtos</h3>
  <br>
  <h4>Carrinho de vendas</h4>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Nome do produto</th>
        <th scope="col">Marca</th>
        <th scope="col">Valor unitário (R$)</th>
        <th scope="col">Quantidade</th>
      </tr>
    </thead>
    <tbody>
<%
	for (int i=0; i < carrinho.size(); i++) {
		Produto produto = carrinho.get(i).getProduto();
		String valorUnitario = String.format("%.2f", carrinho.get(i).getValorUnitario());
		String qtdProdutos = String.format("%d", carrinho.get(i).getQtdProdutos());
%>
      <tr>
        <td class="linha-centro" scope="row">
        	<button onclick="detalhaProduto(<%= produto.getCodigo() %>);" class="btn btn-primary"><%= produto.getNome() %></button>
        </td>
        <td class="linha-centro"><%= produto.getMarca() %></td>
        <td class="linha-centro"><%= valorUnitario %></td>
        <td class="linha-centro"><%= qtdProdutos %></td>
      </tr>
<%
	}
%>
    </tbody>
  </table>
  <br><br>
  <p class="linha-centro">
  	<button onclick="removeVenda(<%= codigo %>);" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span>	Remover</button>
  </p>

  <script src="../scripts/cliente-gerenciamento.js"></script>
  <script src="../scripts/venda-gerenciamento.js"></script>
  <script src="../scripts/produto-gerenciamento.js"></script>
<%
	}
	else {
%>
		<h1>Venda não encontrada</h1>
		<h3>Verifique se a venda não foi removida.</h3>
<%
	}
%>

  <br><br>
</div>

<c:import url="../rodape.jsp"/>