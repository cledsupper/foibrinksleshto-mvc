package br.com.cledson.foibrinks.model.mercado;

public class ProdutoConstantes {
	public static final String STRING_ERRO_FAIXA_ETARIA_NEGATIVA = "O valor definido para a faixa etaria é menor que 0.";
	public static final String STRING_ERRO_FAIXA_ETARIA_MUITO_ALTA = "O valor definido para a faixa etaria é maior que 14.";
	public static final String STRING_ERRO_DIMENSAO_NEGATIVA = "O valor definido para a dimensão '%s' é menor que 0.";
	public static final String STRING_ERRO_PESO_NEGATIVO = "O valor definido para o peso é menor que 0.";
	public static final String STRING_ERRO_PRECO_NEGATIVO = "O valor definido para o preço é menor que 0.";

	public static final short SHORT_FAIXA_ETARIA_LIVRE = 0;
	public static final short SHORT_FAIXA_ETARIA_5_ANOS = 5;
	public static final short SHORT_FAIXA_ETARIA_10_ANOS = 10;
	public static final short SHORT_FAIXA_ETARIA_14_ANOS = 14;
	
	public static final int INT_LIMITE_DESCONTO_PARA_RECENTES = 4;

	public static final double DOUBLE_PRECO_DESCONTO_PARA_RECENTES_PERC = 3.018735;
	public static final double DOUBLE_FRETE_PARA_LUA = 123456;
}
