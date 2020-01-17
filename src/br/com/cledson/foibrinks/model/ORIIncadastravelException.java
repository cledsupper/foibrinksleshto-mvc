package br.com.cledson.foibrinks.model;

/** Modelo de Exception para um ORI incadastrável.
 * 
 * @author Cledson Cavalcanti
 */
public class ORIIncadastravelException extends ORIException {

	public ORIIncadastravelException(ORI ori) {
		super(ori, ORIConstantes.STRING_ERRO_ORI_INCADASTRAVEL);
	}

	public ORIIncadastravelException(ORI ori, String mensagem) {
		super(ori, mensagem);
	}

	public ORIIncadastravelException(ORIException e) {
		super(e);
	}
}
