package br.com.cledson.foibrinks.model.pagamento;

import br.com.cledson.foibrinks.model.ORISomenteLeituraException;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.mercado.Venda;

public class Cartao extends FormaPagamento {
	public static final char FORMA = 'C';

	private final long NUMERO_CARTAO;

	public Cartao(Venda venda, long numeroCartao) throws ORIValorInvalidoException {
		super(venda);
		CartaoValidador.validaNumeroCartao(this, numeroCartao);
		NUMERO_CARTAO = numeroCartao;
	}

	public long getNumeroCartao() {
		return NUMERO_CARTAO;
	}

	@Override
	public char getForma() {
		return FORMA;
	}

	/** Esta função verificaria se o número do cartão é válido.
	 * Como isso está fora do escopo deste projeto, não é feita nenhuma verificação.
	 * 
	 * @return true
	 */
	public boolean verifica() {
		return true;
	}
}
