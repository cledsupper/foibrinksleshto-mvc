const VERSAO = "1.2.191224 beta";

$(document).ready(function() {
	/* Identifica a p�gina corrente e constr�i uma barra de navega��o apropriada. */
	var myNavbar = $("#myNavbar");
	var bakHtml = myNavbar.html();
	myNavbar.html(
		'<ul class="nav navbar-nav">'
			//+ (bakHtml === 'P-INDEX' ? '<li class="active"><a href="#">'
			//		: '<li><a href="/foiBrinksLeshtoMVC/index.html">') + 'P�gina inicial</a></li>'
			/* P�ginas din�micas tem suas pr�prias p�ginas iniciais. */
			+ (bakHtml === 'P-LISTA-PRODUTOS' ? '<li class="active">' : '<li>') + '<a href="/foiBrinksLeshtoMVC/lista/produtos.jsp">Produtos</a></li>'
			+ (bakHtml === 'P-LISTA-CLIENTES' ? '<li class="active">' : '<li>') + '<a href="/foiBrinksLeshtoMVC/lista/clientes.jsp">Clientes</a></li>'
			+ (bakHtml === 'P-LISTA-VENDAS' ? '<li class="active">' : '<li>') + '<a href="/foiBrinksLeshtoMVC/lista/vendas.jsp">Vendas</a></li>'

			+ (bakHtml === 'P-SOBRE' ? '<li class="active"><a href="#">' : '<li><a href="/foiBrinksLeshtoMVC/sobre.html">')
				+ 'Sobre o TAV</a></li>'
			+ (bakHtml === 'P-MODELO' ? '<li class="active"><a href="#">UAU, VO�� ME ACHOU :0</a></li>' : '')
		+ '</ul>\
		<ul class="nav navbar-nav navbar-right">'
		+ (bakHtml === 'P-CADASTRA-CLIENTE' ? '<li class="active"><a href="#">'
					: '<li><a href="/foiBrinksLeshtoMVC/cadastra/cliente.html">')
		+ '<span class="glyphicon glyphicon-user"></span> Cadastrar cliente</a></li>'
		+ (bakHtml === 'P-CADASTRA-PRODUTO' ? '<li class="active"><a href="#">'
					: '<li><a href="/foiBrinksLeshtoMVC/cadastra/produto.html">')
		+ '<span class="glyphicon glyphicon-shopping-cart"></span> Cadastrar Produto</a></li>\
		</ul>'
	);
	
	if (bakHtml === 'P-SOBRE') {
		var titulo = $(document)[0].title;
		$(document)[0].title = titulo.replace("VERSAO", VERSAO);
		var spans = $('.VERSAO');
		for (var i=0; i < spans.length; i++)
			spans[i].innerHTML = VERSAO;
	}
	else if (bakHtml !== 'P-LISTA-PRODUTOS') {
		var h1 = $('h1');
		h1[1].innerHTML = '<button type="button" onclick="voltar();" class="btn btn-secondary">'
			+ '<span class="glyphicon glyphicon-arrow-left"></span>'
			+ ' Voltar</button>	' + h1[1].innerHTML;
	}
	
	var td = $('td');
	td.css("vertical-align", "middle");

	/* Colocar nota de copyright no rodap�. */
	var yearNow = new Date().getYear() + 1900;
	$("#leshto-copyright-footer-note").text(
			"FoiBrinks by Leshto � " + yearNow + " Todos os direitos reservados.");
});

function irPara(endereco) {
	window.location.href = endereco;
}

function voltar() {
	history.back();
}