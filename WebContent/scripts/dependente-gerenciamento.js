function detalhaDependente(codigoCliente, codigoDependente) {
	window.location.href = '../detalha/dependente.jsp?'
		+'codigo-cliente=' + codigoCliente + '&codigo=' + codigoDependente;
};

function removeDependente(codigoCliente, codigoDependente) {
	var yes = confirm("Tem certeza que quer remover esse dependente?");
	if (yes)
		window.location.href = '../control?'
			+ 'codigo-cliente=' + codigoCliente + '&codigo=' + codigoDependente + '&acao=RemoveDependenteAcao';
};