package br.com.cledson.foibrinks.model;

import java.text.SimpleDateFormat;

/** Constantes universais, isto é, para todos os objetos baseados ou não em ORI.
 * 
 * @author Cledson Cavalcanti
 */
public class ORIConstantes {
	public static final long LONG_ORI_CODIGO_CADASTRAVEL = -1;

	public static final SimpleDateFormat DATA_FORMATO_HTML = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATA_FORMATO_BR = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat DATA_HORA_FORMATO_BR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static final String STRING_DATA_NULL = "--/--/---- --:--:--";

	/* Erros relativos às Exceptions dos ORIs. */
	public static final String STRING_ERRO_ORI_INCADASTRAVEL = "O ORI não foi cadastrado porque seu código não está definido para cadastro (codigo != "
			+ LONG_ORI_CODIGO_CADASTRAVEL + ").";
	public static final String STRING_ERRO_ORI_CADASTRAVEL = "O ORI está marcado para cadastro (codigo = "
			+ LONG_ORI_CODIGO_CADASTRAVEL + ") e por isso não pode ser salvo.";
	public static final String STRING_ERRO_ORI_SOMENTE_LEITURA = "O ORI não pode ser salvo porque foi definido como somente leitura.";
	public static final String STRING_ERRO_ORI_VALOR_INVALIDO_DETALHADO = "O atributo <'%s' : '%s'> não foi configurado porque o valor é inválido.";
	public static final String STRING_ERRO_ORI_VALOR_INVALIDO = "'%s' não foi configurado porque o valor é invalido";

	/* Erros diversos. */
	public static final String STRING_ERRO_DAO_NAO_ENCONTRADO = "Número de linhas afetadas = 0.";
}
