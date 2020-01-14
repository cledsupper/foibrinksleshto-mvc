package br.com.cledson.foibrinks.model;

/** Modelo de Exception para tentativas de escrita em ORIs definidos como somente leitura.
 * 
 * @author Cledson Cavalcanti
 */
public class ORISomenteLeituraException extends ORIException {

	/** Lança um erro de escrita (ORISomenteLeituraException).
	 * 
	 * @param ori - objeto relacionado ao erro
	 */
	public ORISomenteLeituraException(ORI ori) {
		super(ori, ORIConstantes.STRING_ERRO_ORI_SOMENTE_LEITURA);
	}

	public ORISomenteLeituraException(ORI ori, String mensagem) {
		super(ori, mensagem);
	}

	ORISomenteLeituraException(ORIException e) {
		super(e);
	}
}
