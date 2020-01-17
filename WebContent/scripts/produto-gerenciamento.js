function detalhaProduto(codigo) {
	window.location.href = '../detalha/produto.jsp?codigo=' + codigo;
};

function removeProduto(codigo) {
	var yes = confirm("Tem certeza que quer remover o produto?\n" +
			"Note que isso não funcionará caso o produto tenha sido vendido");
	if (yes)
		window.location.href = '../control?codigo=' + codigo + '&acao=RemoveProdutoAcao';
};