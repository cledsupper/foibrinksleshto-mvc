function tornaForma(tipo) {
	var campo_numero_cartao = $('#campo-numero-cartao');
	if (tipo == 'C')
		campo_numero_cartao[0].outerHTML = campo_numero_cartao[0].outerHTML
			.replace('disabled', 'required');
	else campo_numero_cartao[0].outerHTML = campo_numero_cartao[0].outerHTML
		.replace('required', 'disabled');
};

botaoVenderLiberado = false;
function ativaBotao() {
	if (botaoVenderLiberado)
		return;
	var btn_vender = $('#btn-vender');
	btn_vender[0].outerHTML = btn_vender[0].outerHTML.replace('disabled=""', '');
	botaoVenderLiberado = true;
};