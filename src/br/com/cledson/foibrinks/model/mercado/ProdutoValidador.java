package br.com.cledson.foibrinks.model.mercado;

import br.com.cledson.foibrinks.model.ORIValorInvalidoException;

public class ProdutoValidador {
	public static void validaDimensao(Produto produto, double valor, Dimensao.Enum dim)
		throws ORIValorInvalidoException {
		final String dimName = dim.name();
		if (valor < 0)
			throw new ORIValorInvalidoException(produto, String.format(
					ProdutoConstantes.STRING_ERRO_DIMENSAO_NEGATIVA, dimName));
	}

	public static void validaFaixaEtaria(Produto produto, short faixaEtaria)
		throws ORIValorInvalidoException {
		if (faixaEtaria < 0)
			throw new ORIValorInvalidoException(produto, ProdutoConstantes.STRING_ERRO_FAIXA_ETARIA_NEGATIVA);
		if (faixaEtaria > 18)
			throw new ORIValorInvalidoException(produto, ProdutoConstantes.STRING_ERRO_FAIXA_ETARIA_MUITO_ALTA);
	}

	public static boolean faixaEtariaOutro(short faixaEtaria) {
		if (faixaEtaria == ProdutoConstantes.SHORT_FAIXA_ETARIA_LIVRE)
			return false;
		if (faixaEtaria == ProdutoConstantes.SHORT_FAIXA_ETARIA_5_ANOS)
			return false;
		if (faixaEtaria == ProdutoConstantes.SHORT_FAIXA_ETARIA_10_ANOS)
			return false;
		if (faixaEtaria == ProdutoConstantes.SHORT_FAIXA_ETARIA_14_ANOS)
			return false;
		return true;
	}

	public static void validaPeso(Produto produto, double peso)
		throws ORIValorInvalidoException {
		if (peso < 0)
			throw new ORIValorInvalidoException(produto, ProdutoConstantes.STRING_ERRO_PESO_NEGATIVO);
	}

	public static void validaPreco(Produto produto, double preco)
			throws ORIValorInvalidoException {
			if (preco < 0)
				throw new ORIValorInvalidoException(produto, ProdutoConstantes.STRING_ERRO_PRECO_NEGATIVO);
		}
}
