package br.com.cledson.foibrinks.model.pagamento;

import br.com.cledson.foibrinks.model.mercado.Venda;

public class Dinheiro extends FormaPagamento {
	public static final char FORMA = 'D';

	public Dinheiro(Venda venda) {
		super(venda);
	}
	
	@Override
	public char getForma() {
		return FORMA;
	}

}
