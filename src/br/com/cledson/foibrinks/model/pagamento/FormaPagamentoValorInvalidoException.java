package br.com.cledson.foibrinks.model.pagamento;

import br.com.cledson.foibrinks.model.mercado.Venda;

public class FormaPagamentoValorInvalidoException extends Exception {
	private final FormaPagamento PAGAMENTO;
	private final double VALOR;

	public FormaPagamentoValorInvalidoException(FormaPagamento pagamento, double valorInvalido) {
		PAGAMENTO = pagamento;
		VALOR = valorInvalido;
	}
	
	@Override
	public String getMessage() {
		final Venda VENDA = PAGAMENTO.getVenda(); 
		String erro = "Recebeu " + VALOR + " (< 0.00) como valor de pagamento."
				+ "\n  Venda {"
				+ "\n    código: " + VENDA.getCodigo()
				+ "\n    cliente: " + PAGAMENTO.getVenda().getCliente().getNomeCompleto()
				+ "\n    dataVenda: " + VENDA.getDataVendaStringBr()
				+ "\n  }";
		
		return erro;
	}
}
