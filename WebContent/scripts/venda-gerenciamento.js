detalhaVenda = function(codigo) {
	window.location.href = '../detalha/venda.jsp?codigo=' + codigo;
};
removeVenda = function(codigo) {
	var yes = confirm("Remover a venda limpar� todos os registros relacionados (exceto o(a) cliente e o(s) produto(s).\n" +
			"Tem certeza que quer continuar?");
	if (yes)
		window.location.href = '../control?codigo=' + codigo + '&acao=RemoveVendaAcao';
};