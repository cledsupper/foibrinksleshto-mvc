package br.com.cledson.foibrinks.model;

/** ORIException é uma classe modelo para exceções lançadas por objetos ORI.
 * 
 * @author Cledson Cavalcanti
 *
 */
public class ORIException extends Exception {
	protected final ORI OBJETO_RELACIONAL_IDENTIFICAVEL;
	protected String erro;
	
	public ORIException(ORI ori) {
		OBJETO_RELACIONAL_IDENTIFICAVEL = ori;
		erro = "O objeto a seguir apresentou um problema.\n"
				+ OBJETO_RELACIONAL_IDENTIFICAVEL.getORIDataString();
	}
	
	public ORIException(ORI ori, String mensagem) {
		OBJETO_RELACIONAL_IDENTIFICAVEL = ori;
		erro = mensagem + "\n"
				 + OBJETO_RELACIONAL_IDENTIFICAVEL.getORIDataString();
	}

	@Override
	public String getMessage() {
		return erro;
	}
	
	public ORI getORI() {
		return OBJETO_RELACIONAL_IDENTIFICAVEL;
	}
}
