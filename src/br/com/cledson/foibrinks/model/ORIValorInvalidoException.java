package br.com.cledson.foibrinks.model;

public class ORIValorInvalidoException extends ORIException {

	public ORIValorInvalidoException(
			ORI ori, String atributoNome, String atributoClassNome) {
		super(ori, String.format(ORIConstantes.STRING_ERRO_ORI_VALOR_INVALIDO_DETALHADO,
				atributoNome, atributoClassNome));
	}

	public ORIValorInvalidoException(ORI ori, String erro) {
		super(ori, erro);
	}
}
