var formatoCPF = /^([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2})$/;

/* Eu peguei o código deste método na web. */
function formataCPF() {
	var campoCPF = $("#campo-cpf")[0];
	var cpf = campoCPF.value;

	if (formatoCPF.test(cpf) == false) {
		cpf = cpf.replace( /[^0-9]/g, "");

		if (cpf.length == 11) {
			cpf = cpf.replace(/^([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$/,
				"$1.$2.$3-$4");
			campoCPF.value = cpf;
		}
	}
};

function validaCPF() {
	var campo = $("#campo-cpf")[0];
	var cpf = campo.value;
	if (formatoCPF.test(cpf) == false) {
		if (campo.value.length == 11)
			formataCPF();
	}
	return true;
};

const COMBO_VALOR_OUTRO = "OUTRO";

function validaGenero() {
	const VALOR_MASCULINO = "MASCULINO";
	const VALOR_FEMININO = "FEMININO";
	const VALOR_NENHUM = "";
	var combo = $('#combo-genero');
	var div = $('#campo-genero-grupo');

	if (!(div.css('display') === "none")) {
		if (!(combo[0].value === COMBO_VALOR_OUTRO))
			div.css('display', 'none');
	} else if (combo[0].value === COMBO_VALOR_OUTRO) {
		div.css('display', 'table');
	}
	
	var valor = combo[0].value;
	var letras = $(".letra-pronome");
	if (letras.length > 0) {
		var letra_sel = (valor === VALOR_MASCULINO ? 'o'
			: (valor === VALOR_FEMININO ? 'a' : 'e'));

		for (var it = 0; it < letras.length; it++) {
			letras[it].innerHTML = letra_sel;
		}
	}
};

function validaFaixaEtaria() {
	const CAMPO_FAIXA_ETARIA_VALOR_MENOR = 0;
	const CAMPO_FAIXA_ETARIA_VALOR_MAIOR = 14;
	
	var combo = $('#combo-faixa-etaria');
	var div = $('#campo-faixa-etaria-grupo');

	if (!(div.css('display') === 'none')) {
		if (!(combo[0].value === COMBO_VALOR_OUTRO))
			div.css('display', 'none');
		var campo = $('#campo-faixa-etaria');
		if (campo[0].value < CAMPO_FAIXA_ETARIA_VALOR_MENOR)
			campo[0].value = CAMPO_FAIXA_ETARIA_VALOR_MAIOR;
		else if (campo[0].value > CAMPO_FAIXA_ETARIA_VALOR_MAIOR)
			campo[0].value = CAMPO_FAIXA_ETARIA_VALOR_MENOR;
	} else if (combo[0].value === COMBO_VALOR_OUTRO) {
		div.css('display', 'table');
	}
};