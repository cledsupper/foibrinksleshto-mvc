package br.com.cledson.foibrinks.model;

public class ORINaoEncontradoException extends ORIException {

	public ORINaoEncontradoException(ORI ori) {
		super(ori, ORIConstantes.STRING_ERRO_ORI_CADASTRAVEL);
	}
	
	public ORINaoEncontradoException(ORI ori, String mensagem) {
		super(ori, mensagem);
	}
}
