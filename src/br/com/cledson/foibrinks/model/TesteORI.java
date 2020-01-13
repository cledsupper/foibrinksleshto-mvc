package br.com.cledson.foibrinks.model;

import br.com.cledson.foibrinks.model.pessoal.Cliente;

public class TesteORI {

	public static void main(String[] args) {
		Cliente cliente = new Cliente(1223324L, null);
		try {
			cliente.cadastra();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cliente.setDataNascimentoStringBr("12/01/2000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
