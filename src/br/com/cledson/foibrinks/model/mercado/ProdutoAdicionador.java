package br.com.cledson.foibrinks.model.mercado;

public class ProdutoAdicionador {
	private final Venda VENDA;
	private final Produto PRODUTO;
	
	private double valorUnitario;
	private int qtdProdutos;
	
	public ProdutoAdicionador(Venda venda, Produto produto) {
		VENDA = venda;
		PRODUTO = produto;
	}

	public Venda getVenda() {
		return VENDA;
	}

	public Produto getProduto() {
		return PRODUTO;
	}
	
	public void setValorUnitario(double valor) {
		valorUnitario = valor;
	}
	
	public double getValorUnitario() {
		return valorUnitario;
	}
	
	public void setQtdProdutos(int qtd) {
		qtdProdutos = qtd;
	}
	
	public int getQtdProdutos() {
		return qtdProdutos;
	}
}
