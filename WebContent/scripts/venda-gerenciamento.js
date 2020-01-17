function detalhaVenda(codigo) {
	window.location.href = '../detalha/venda.jsp?codigo=' + codigo;
};

function removeVenda(codigo) {
	var yes = confirm("Remover a venda limpará todos os registros relacionados (exceto o(a) cliente e o(s) produto(s).\n" +
			"Tem certeza que quer continuar?");
	if (yes)
		window.location.href = '../control?codigo=' + codigo + '&acao=RemoveVendaAcao';
};