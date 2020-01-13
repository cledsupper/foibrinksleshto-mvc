package br.com.cledson.foibrinks.model.mercado;

public abstract class Dimensao {
	public static final int ALTURA			= 0;
	public static final int LARGURA			= 1;
	public static final int PROFUNDIDADE	= 2;
	public static final int TAMANHO			= 3;

	public static enum Enum {
		Altura,
		Largura,
		Profundidade,
		Todas
	};
}
