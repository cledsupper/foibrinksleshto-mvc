package br.com.cledson.foibrinks.model.pessoal;

import br.com.cledson.foibrinks.model.ORIConstantes;

public class PessoaConstantes {
	public static final String STRING_GENERO_MASCULINO = "MASCULINO";
	public static final String STRING_GENERO_FEMININO = "FEMININO";
	public static final String STRING_GENERO_NAO_ESPECIFICAR = "N/A";
	public static final String STRING_GENERO_OUTRO = "OUTRO";

	public static final String STRING_ERRO_PESSOA_INCADASTRAVEL = "Este %s n�o est� marcado/a para cadastro (codigo != "
			+ ORIConstantes.LONG_ORI_CODIGO_CADASTRAVEL + ").";
	public static final String STRING_ERRO_PESSOA_NAO_ENCONTRADA = "O %s abaixo n�o est� cadastrado no banco de dados.";
}
