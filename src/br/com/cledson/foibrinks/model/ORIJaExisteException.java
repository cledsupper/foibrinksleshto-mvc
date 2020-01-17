package br.com.cledson.foibrinks.model;

public class ORIJaExisteException extends ORIException {

	public ORIJaExisteException(ORI ori) {
		super(ori, ORIConstantes.STRING_ERRO_ORI_JA_EXISTE);
	}

	public ORIJaExisteException(ORI ori, String mensagem) {
		super(ori, mensagem);
	}

	public ORIJaExisteException(ORIException e) {
		super(e);
	}
}
