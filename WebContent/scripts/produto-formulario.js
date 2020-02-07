const CAMPO_PRODUTO_QTD_PADRAO = '<input class="linha-direito form-control" type="number" name="produto-qtd" value="0" disabled="">';
const CAMPO_PRODUTO_QTD_ATIVADO = '<input class="linha-direito form-control" type="number" name="produto-qtd" value="1" onchange="validaProdutoQtd(INDEX);">';

contaSelecoes = 0;
function tornaBotao() {
	var btn_adicionar = $("#btn-adicionar");
	if (contaSelecoes > 0)
		btn_adicionar[0].outerHTML = btn_adicionar[0].outerHTML.replace('disabled=""', '');
	else
		btn_adicionar[0].outerHTML = btn_adicionar[0].outerHTML.replace('>', 'disabled="">');
};

tornaProdutoDesativado = false;
function tornaProduto(i) {
	if (tornaProdutoDesativado)
		return;
	var produto_check = $('input[name="produto-check"]');
	var produto_qtd = $('input[name="produto-qtd"]');
	var produto_codigo = $('input[name="produto-codigo"]');
	var produto_preco = $('input[name="produto-preco"]');
	var produto_frete_lua = $('input[name="produto-frete-lua"]');

	if (produto_check[i].checked) {
		contaSelecoes++;
		produto_codigo[i].outerHTML = produto_codigo[i].outerHTML.replace('disabled', 'readonly');
		produto_qtd[i].outerHTML = CAMPO_PRODUTO_QTD_ATIVADO.replace('INDEX', i);
		produto_preco[i].outerHTML = produto_preco[i].outerHTML.replace('disabled', 'readonly');
		if (produto_frete_lua.length > i)
			produto_frete_lua[i].outerHTML = produto_frete_lua[i].outerHTML.replace('disabled', 'readonly');
	} else {
		contaSelecoes = contaSelecoes == 0 ? 0 : contaSelecoes-1;
		produto_codigo[i].outerHTML = produto_codigo[i].outerHTML.replace('readonly', 'disabled');
		produto_qtd[i].outerHTML = CAMPO_PRODUTO_QTD_PADRAO;
		produto_preco[i].outerHTML = produto_preco[i].outerHTML.replace('readonly', 'disabled');
		if (produto_frete_lua.length > i)
			produto_frete_lua[i].outerHTML = produto_frete_lua[i].outerHTML.replace('readonly', 'disabled');
	}
	tornaBotao();
};

validaProdutoQtd = function(i) {
	var produto_check = $('input[name="produto-check"]');
	var produto_codigo = $('input[name="produto-codigo"]');
	var produto_qtd = $('input[name="produto-qtd"]');
	if (produto_qtd[i].value <= 0) {
		tornaProdutoDesativado = true;
		produto_check[i].checked = false;
		produto_codigo[i].outerHTML = produto_codigo[i].outerHTML.replace('readonly', 'disabled');
		produto_qtd[i].outerHTML = CAMPO_PRODUTO_QTD_PADRAO;
		contaSelecoes = contaSelecoes == 0 ? 0 : contaSelecoes-1;
		tornaBotao();
		tornaProdutoDesativado = false;
	}
};