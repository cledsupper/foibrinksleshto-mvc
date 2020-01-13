<%@page import="br.com.cledson.foibrinks.model.pagamento.Cartao"%>
<%@page import="br.com.cledson.foibrinks.model.pagamento.Dinheiro"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Produto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.ProdutoAdicionador"%>
<%@page import="br.com.cledson.foibrinks.bd.dac.VendaDAC"%>
<%@page import="br.com.cledson.foibrinks.model.mercado.Venda"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String string_codigo = request.getParameter("codigo");
	Long codigo = Long.parseLong(string_codigo);
	Venda venda = null;
	try {
		venda = VendaDAC.le(codigo);
	} catch (Exception e) {}
%>
<!DOCTYPE html>
<html>
<head>
  <title>FoiBrinks: <%
	if (venda == null)
		out.write("erro: venda não existe");
	else
		out.write("venda " + venda.getCodigo());
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
    <div class="collapse navbar-collapse" id="myNavbar">P-LISTA-VENDAS</div>
  </div>
</nav>

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
		String string_forma = forma == Dinheiro.FORMA ?
				Constantes.STRING_FORMA_DINHEIRO : Constantes.STRING_FORMA_CARTAO;
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
    <input type="number" class="form-control" aria-describedby="addon-custo-total" value="<%= custoTotal %>" readonly>
  </div>
  <div class="input-group">
  	<span class="input-group-addon" id="addon-imposto">Tributos totais R$</span>
  	<input type="number" class="form-control" aria-describedby="addon-imposto" value="<%= impostos %>" readonly>
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
		<input type="number" class="form-control" aria-describedby="addon-valor-pago" value="<%= venda.getPagamento().getValorPago() %>" readonly>
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
		double valorUnitario = carrinho.get(i).getValorUnitario();
		double qtdProdutos = carrinho.get(i).getQtdProdutos();
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
<%	}
%>

  <br><br>
</div>

<footer class="container-fluid text-center">
  <p id="leshto-copyright-footer-note"></p>
</footer>

</body>
</html>