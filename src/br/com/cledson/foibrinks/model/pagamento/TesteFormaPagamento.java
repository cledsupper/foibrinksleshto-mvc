package br.com.cledson.foibrinks.model.pagamento;

import java.util.Calendar;
import java.util.Date;

import br.com.cledson.foibrinks.model.ORIException;
import br.com.cledson.foibrinks.model.ORISomenteLeituraException;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.mercado.Venda;
import br.com.cledson.foibrinks.model.pessoal.Cliente;

public class TesteFormaPagamento {
	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		try {
			cliente.setNomeCompleto("Joaquim da Silva");
		} catch (ORIValorInvalidoException e1) {
			e1.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR,
				cal.get(Calendar.DAY_OF_YEAR)-6 /* dias */);

		Venda venda = new Venda(
				231424,
				cliente, cal);
		try {
			Cartao cartao = new Cartao(venda, 5825512345678901L);
			venda.setPagamento(cartao);
			cartao.setValorPago(5);
			venda.setPagamento(null);
		} catch (ORIException | FormaPagamentoValorInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
