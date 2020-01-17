<%@page import="br.com.cledson.foibrinks.model.pessoal.PessoaConstantes"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.ClienteValidador"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.PessoaValidador"%>
<%@page import="br.com.cledson.foibrinks.mvc.Constantes"%>
<%@page import="br.com.cledson.foibrinks.model.pessoal.Cliente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String string_codigo = request.getParameter("codigo");
	Long codigo = Long.parseLong(string_codigo);
	Cliente cliente = null;
	try {
		cliente = Cliente.procura(codigo);
	} catch (Exception e) {}
%>
<!DOCTYPE html>
<html>
<head>
  <title>FoiBrinks: <%
	String nome = "?";
	if (cliente == null)
		out.write("erro: cliente não existe");
	else {
		nome = cliente.getNomeCompleto();
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
    <div class="collapse navbar-collapse" id="myNavbar">P-LISTA-CLIENTES</div>
  </div>
</nav>

<div class="container">
<%
	if (cliente != null) {
		String data_cadastro = cliente.getDataCadastroStringBr();
		String data_nascimento = cliente.getDataNascimentoHTML();
		String cpf =  ClienteValidador.paraCpfFormatado(cliente.getCpf());
		String genero = cliente.getGenero();
		String estadoCivil = cliente.getEstadoCivil();
		String rua = cliente.getRua();
		String bairro = cliente.getBairro();
		String cep = cliente.getCep();
		String cidade = cliente.getCidade();
		String estado = cliente.getEstado();

		boolean genero_outro = PessoaValidador.generoOutro(genero);
		char pronome = genero_outro || genero.equals(PessoaConstantes.STRING_GENERO_NAO_ESPECIFICAR) ?
				'e' : (genero.equals(PessoaConstantes.STRING_GENERO_FEMININO) ? 'a' : 'o');
%>
  <h1>Cadastro d<span class="letra-pronome"><%= pronome %></span> cliente</h1>
<%
		String notifica_salvo = request.getParameter("notifica-salvo");
		if (notifica_salvo != null)
			out.write("<h3 class=\"aviso sucesso linha-centro\">OS DADOS FORAM SALVOS!</h3>");
%>
  <br>

  <form action="../control?codigo=<%= codigo %>" method="POST">
    <input type="hidden" name="acao" value="AtualizaClienteAcao">

    <h4 class="observe">Cliente cadastrad<span class="letra-pronome"><%= pronome %></span> em: <%= data_cadastro %></h4>
    <br>
    <h3>Dependentes de <%= nome %></h3>
    <br>
    <div class="btn-group btn-group-lg" role="group" aria-label="Opções para gerenciar dependentes do cliente">
    	<button type="button" class="btn btn-primary" onclick="irPara('../lista/dependentes.jsp?codigo-cliente=<%= codigo %>');"><span class="glyphicon glyphicon-th-list"></span>	Gerenciar</button>
    	<button type="button" class="btn btn-success" onclick="irPara('../cadastra/dependente.jsp?codigo-cliente=<%= codigo %>');"><span class="glyphicon glyphicon-plus"></span>	Adicionar</button>
    </div>
    <br><br>
    <h3>Dados obrigatórios</h3>
    <br>
    <p>Insira o nome completo</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-nome">Nome completo</span>
    	<input type="text" class="form-control" placeholder="Nome do Cliente Aqui" name="nome" aria-describedby="addon-nome" value="<%= nome %>" required>
    </div>
    <br><br>

    <p>Selecione/insira a data de nascimento</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-data-nascimento">Data de nascimento</span>
    	<input type="date" class="form-control" name="data-nascimento" aria-describedby="addon-data-nascimento" value="<%= data_nascimento %>" required>
    </div>
    <br><br>

    <p>Selecione o gênero d<span class="letra-pronome"><%= pronome %></span> cliente</p>
    <div class="input-group" id="compo-genero-grupo">
    	<span class="input-group-addon" id="addon-combo-genero">Seleção de gênero</span>
    	<select id="combo-genero" class="form-control" name="combo-genero" onchange="validaGenero();" aria-describedby="addon-combo-genero" required>
    		<option value="">[Selecione o gênero]</option>
	    	<option value="MASCULINO"<% if (genero.equals("MASCULINO")) out.write(" selected=\"true\""); %>>Masculino</option>
    		<option value="FEMININO"<% if (genero.equals("FEMININO")) out.write(" selected=\"true\""); %>>Feminino</option>
    		<option value="OUTRO"<% if (genero_outro) out.write(" selected=\"true\""); %>>Não binário</option>
    		<option value="N/A"<% if (genero.equals("N/A")) out.write(" selected=\"true\""); %>>Não especificar</option>
    	</select>
    </div>
    <div class="input-group" id="campo-genero-grupo" style="display: <%= genero_outro ? "table" : "none" %>;">
    	<span class="input-group-addon" id="addon-campo-genero">Gênero</span>
    	<input id="campo-genero" class="form-control" type="text" name="campo-genero" aria-describedby="addon-campo-genero" placeholder="neutro" value="<%= genero_outro ? genero : "" %>" maxlength="15">
    </div>
    <br><br>

    <p>Insira o CPF</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-cpf">CPF</span>
    	<input type="text" class="form-control" name="cpf" placeholder="123.456.789-01" id="campo-cpf" aria-describedby="addon-cpf" value="<%= cpf %>" onchange="validaCPF();" required>
    </div>
    <br><br>

    <h3>As opções a seguir são opcionais e podem ficar em branco</h3>
    <p>Você pode alterá-los depois.</p>
	<br>
	<h4>Relacionamento</h4>
	<br>
    <p>Selecione o 'estado civil'</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-estado-civil">Seleção de estado civil</span>
    	<select class="form-control" name="estado-civil" aria-describedby="addon-estado-civil">
    		<option value="">Não especificar</option>
	    	<option value="SOLTEIRO"<% if (estadoCivil.equals("SOLTEIRO")) out.write(" selected=\"true\""); %>>Solteir<%= pronome %></option>
    		<option value="RELACIONAMENTO"<% if (estadoCivil.equals("RELACIONAMENTO")) out.write(" selected=\"true\""); %>>Namorando</option>
    		<option value="CASADO"<% if (estadoCivil.equals("CASADO")) out.write(" selected=\"true\""); %>>Casad<%= pronome %></option>
    	</select>
    </div>
    <br><br>
    
    <h4>Endereço</h4>
    <br>
    <p>Insira a rua onde <span class="letra-pronome"><%= pronome %></span> cliente mora</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-rua">Rua</span>
    	<input type="text" class="form-control" name="rua" placeholder="Rua Anônima S/A, S/N" aria-describedby="addon-rua" value="<%= rua %>">
    </div>
    <br>
    <p>Insira o bairro onde <span class="letra-pronome"><%= pronome %></span> cliente mora</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-bairro">Bairro</span>
    	<input type="text" class="form-control" name="bairro" placeholder="Candelaria Destruída" aria-describedby="addon-bairro" value="<%= bairro %>">
    </div>
    <br>
    <p>Insira o CEP</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-cep">CEP</span>
    	<input type="text" class="form-control" name="cep" placeholder="12345123" maxlength="8" aria-describedby="addon-cep" value="<%= cep %>">
    </div>
    <br>
    <p>Insira a cidade</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-cidade">Cidade</span>
    	<input type="text" class="form-control" name="cidade" placeholder="Nomilândia" aria-describedby="addon-cidade" value="<%= cidade %>">
    </div>
    <br>
    <p>Selecione o estado onde <span class="letra-pronome"><%= pronome %></span> cliente mora</p>
    <div class="input-group">
    	<span class="input-group-addon" id="addon-estado">Seleção de estado</span>
    	<select class="form-control" name="estado" aria-describedby="addon-estado">
    		<option value="">Não especificar</option>
    		<option value="AL"<% if (estado.equals("AL")) out.write(" selected=\"true\""); %>>Alagoas</option>
    		<option value="BA"<% if (estado.equals("BA")) out.write(" selected=\"true\""); %>>Bahia</option>
    		<option value="CE"<% if (estado.equals("CE")) out.write(" selected=\"true\""); %>>Ceará</option>
    		<option value="MA"<% if (estado.equals("MA")) out.write(" selected=\"true\""); %>>Maranhão</option>
    		<option value="PB"<% if (estado.equals("PB")) out.write(" selected=\"true\""); %>>Paraíba</option>
    		<option value="PE"<% if (estado.equals("PE")) out.write(" selected=\"true\""); %>>Pernambuco</option>
    		<option value="PI"<% if (estado.equals("PI")) out.write(" selected=\"true\""); %>>Piauí</option>
    		<option value="RN"<% if (estado.equals("RN")) out.write(" selected=\"true\""); %>>Rio Grande do Norte</option>
    	</select>
    </div>
    <br><br>

    <p style="text-align: center">
    	<button class="btn btn-success"><span class="glyphicon glyphicon-floppy-disk"></span>	Salvar</button>
    	<button type="button" class="btn btn-danger" onclick="removeCliente(<%= codigo %>);"><span class="glyphicon glyphicon-trash"></span>	Remover</button></p>
  </form>

  <!-- Este script torna o formulário mais dinâmico. -->
  <script src="../scripts/formatador-formulario.js"></script>
  <!-- Funções comuns de gerenciamento dos clientes -->
  <script src="../scripts/cliente-gerenciamento.js"></script>
<%
	}
	else {
%>
		<h1>Cliente não encontrado</h1>
		<h3>Verifique se o cliente não foi removido.</h3>
<%	}
%>
  <br><br>
</div>

<footer class="container-fluid text-center">
  <p id="leshto-copyright-footer-note"></p>
</footer>

</body>
</html>