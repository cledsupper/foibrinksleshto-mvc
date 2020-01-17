package br.com.cledson.foibrinks.model.pagamento;

import br.com.cledson.foibrinks.model.mercado.Venda;

/** Define o atributo 'pagamento' da classe Venda. 
 * 
 * @author Cledson Cavalcanti
 */
public abstract class FormaPagamento {
	// Representa o valor total
	public static final double VALOR_TOTAL = -1.0;
	// Representa a forma de pagamento retornada por getForma()
	public static final char FORMA = '0';

	private final Venda VENDA;

	private double valorPago;

	/** Constrói o objeto referente ao registro de pagamento de uma venda.
	 * 
	 * @param venda
	 */
	public FormaPagamento(Venda venda) {
		VENDA = venda;
		valorPago = 0;
	}
	
	public Venda getVenda() {
		return VENDA;
	}

	/** Retorna o caractere auxiliar que representa a subclasse FormaPagamento.
	 * 
	 * @return <subclass>.FORMA
	 */
	public abstract char getForma();

	public boolean pago() {
		return valorPago != 0;
	}

	public double getValorPago() {
		return valorPago;
	}

	/** Deve ser um número natural.
	 * 
	 * @param valor
	 * @throws FormaPagamentoValorInvalidoException
	 */
	public void setValorPago(double valor) throws FormaPagamentoValorInvalidoException {
		if (valor < 0)
			throw new FormaPagamentoValorInvalidoException(this, valor);
		valorPago = valor;
	}
}
