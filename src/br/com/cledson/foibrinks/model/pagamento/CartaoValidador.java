package br.com.cledson.foibrinks.model.pagamento;

import br.com.cledson.foibrinks.model.ORIValorInvalidoException;

public class CartaoValidador {
	private final static long NUMERO_MIN_VALUE = 1000000000000000L;
	private final static long NUMERO_MAX_VALUE = 9999999999999999L;

	public static void validaNumeroCartao(Cartao cartao, long numero)
		throws ORIValorInvalidoException {
		if (numero < NUMERO_MIN_VALUE || numero > NUMERO_MAX_VALUE)
			throw new ORIValorInvalidoException(cartao.getVenda(),
					"O número do cartão inserido é inválido (número = " + numero + ")");
	}
}
