package br.com.cledson.foibrinks.mvc;

public class Constantes {
	public static final String STRING_URI_APP = "/foiBrinksLeshtoMVC";

	/* URIs de páginas comumente usadas. */
	public static final String STRING_URI_LISTA_CLIENTES = STRING_URI_APP + "/lista/clientes.jsp";
	public static final String STRING_URI_LISTA_DEPENDENTES = STRING_URI_APP + "/lista/dependentes.jsp";
	public static final String STRING_URI_LISTA_PRODUTOS = STRING_URI_APP + "/lista/produtos.jsp";
	public static final String STRING_URI_LISTA_VENDAS = STRING_URI_APP + "/lista/vendas.jsp";

	public static final String STRING_URI_DETALHA_CLIENTE = STRING_URI_APP + "/detalha/cliente.jsp";
	public static final String STRING_URI_DETALHA_DEPENDENTE = STRING_URI_APP + "/detalha/dependente.jsp";
	public static final String STRING_URI_DETALHA_PRODUTO = STRING_URI_APP + "/detalha/produto.jsp";
	public static final String STRING_URI_DETALHA_VENDA = STRING_URI_APP + "/detalha/venda.jsp";

	/* URIs para actions de cadastro. */
	public static final String STRING_URI_ACAO_CADASTRA_CLIENTE = STRING_URI_APP + "/cadastraCliente";
	public static final String STRING_URI_ACAO_CADASTRA_DEPENDENTE = STRING_URI_APP + "/cadastraDependente";
	public static final String STRING_URI_ACAO_CADASTRA_PRODUTO = STRING_URI_APP + "/cadastraProduto";
	public static final String STRING_URI_ACAO_CADASTRA_VENDA = STRING_URI_APP + "/cadastraVenda";
	
	/* URIs para actions de edição. */
	public static final String STRING_URI_ACAO_EDITA_CLIENTE = STRING_URI_APP + "/editaCliente";
	public static final String STRING_URI_ACAO_EDITA_DEPENDENTE = STRING_URI_APP + "/editaDependente";
	public static final String STRING_URI_ACAO_EDITA_PRODUTO = STRING_URI_APP + "/editaProduto";
	//public static final String STRING_URI_ACAO_EDITA_VENDA = STRING_URI_APP + "/editaVenda";

	public static final String STRING_URI_ACAO_REMOVE_CLIENTE = STRING_URI_APP + "/removeCliente";
	public static final String STRING_URI_ACAO_REMOVE_DEPENDENTE = STRING_URI_APP + "/removeDependente";
	public static final String STRING_URI_ACAO_REMOVE_PRODUTO = STRING_URI_APP + "/removeProduto";
	public static final String STRING_URI_ACAO_REMOVE_VENDA = STRING_URI_APP + "/removeVenda";

	public static final String COMBO_VALOR_OUTRO = "OUTRO";

	public static final String STRING_FORMA_DINHEIRO = "Dinheiro em espécie";
	public static final String STRING_FORMA_CARTAO = "Cartão de crédito";
	
	public static final double VALOR_IMPOSTO = 0.225;
}
