package br.com.cledson.foibrinks.model;

import java.text.SimpleDateFormat;

/** Constantes universais, isto �, para todos os objetos baseados ou n�o em ORI.
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
	public static final String STRING_ERRO_ORI_INCADASTRAVEL = "O ORI n�o foi cadastrado porque seu c�digo n�o est� definido para cadastro (codigo != "
			+ LONG_ORI_CODIGO_CADASTRAVEL + ").";
	public static final String STRING_ERRO_ORI_CADASTRAVEL = "O ORI foi instanciado para cadastro (codigo = "
			+ LONG_ORI_CODIGO_CADASTRAVEL + ") e por isso n�o pode ser salvo.";
	public static final String STRING_ERRO_ORI_JA_EXISTE = "Um ORI com os mesmos atributos j� existe no banco de dados";
	public static final String STRING_ERRO_ORI_SOMENTE_LEITURA = "O ORI n�o pode ser salvo porque foi definido como somente leitura.";
	public static final String STRING_ERRO_ORI_VALOR_INVALIDO_DETALHADO = "O atributo <'%s' : '%s'> n�o foi configurado porque o valor � inv�lido.";
	public static final String STRING_ERRO_ORI_VALOR_INVALIDO = "'%s' n�o foi configurado porque o valor � invalido";

	/* Erros diversos. */
	public static final String STRING_ERRO_DAC_NAO_ENCONTRADO = "N�mero de linhas afetadas = 0.";
}
