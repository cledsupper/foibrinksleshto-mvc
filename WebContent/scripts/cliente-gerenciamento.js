function detalhaCliente(codigo) {
	window.location.href = '../detalha/cliente.jsp?codigo=' + codigo;
};

function removeCliente(codigo) {
	var yes = confirm("Tem certeza que quer remover o cliente?");
	if (yes)
		window.location.href = '../control?codigo=' + codigo + '&acao=RemoveClienteAcao';
};