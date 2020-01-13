package br.com.cledson.foibrinks.model.pagamento;

import java.sql.SQLException;

import br.com.cledson.foibrinks.model.ORISomenteLeituraException;
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
	
	/* Verifica o valor e processa o pagamento.
	 * 
	 * @param valor - pode ser um número natural ou VALOR_TOTAL (-1).
	 * @return true - se o valor do pagamento foi bem processado.
	 * @throws FormaPagamentoValorInvalidoException
	 * @throws ORISomenteLeituraException
	 * @throws SQLException 
	 * 
	public boolean paga(double valor) throws FormaPagamentoValorInvalidoException, ORISomenteLeituraException, SQLException {
		if (pagamentoCompletoRealizado())
			throw new ORISomenteLeituraException(VENDA, "Tentando pagar repetidas vezes");

		if (valor < 0)
			throw new FormaPagamentoValorInvalidoException(this, valor);

		if (valor != VALOR_TOTAL)
			setValorPago(this.valorPago + valor);
		else
			setValorPago(VENDA.geraCustoTotal());

		return true;
	}
	*/

	public boolean pago() {
		return valorPago != 0;
	}

	/*
	public boolean pagamentoCompletoRealizado() throws SQLException {
		return valorPago >= VENDA.geraCustoTotal();
	}*/

	public double getValorPago() {
		return valorPago;
	}

	/** Deve ser um n�mero natural.
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
