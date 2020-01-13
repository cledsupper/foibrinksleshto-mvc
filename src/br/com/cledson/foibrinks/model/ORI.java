package br.com.cledson.foibrinks.model;

import java.sql.SQLException;

/** ORI (Objeto Relacional Identificável) é um modelo de objeto para objetos que são convertidos de/para bancos de dados relacionais E apresentam um valor-chave de tipo long (ou BIGINT).
 * 
 * @author Cledson Cavalcanti
 */
public abstract class ORI {
	private final long CODIGO;
	
	/** Todo objeto baseado em um ORI deve chamar super() no construtor para possibilitar o cadastro. */
	public ORI() {
		CODIGO = ORIConstantes.LONG_ORI_CODIGO_CADASTRAVEL;
	}
	
	/** Todo objeto baseado em um ORI deve chamar super(codigo) no construtor para registro da chave primária. */
	public ORI(long codigo) {
		CODIGO = codigo;
	}

	/** Verifica se o ORI está marcado para cadastro.
	 * 
	 * @return true if CODIGO == CODIGO_CADASTRAVEL
	 */
	public boolean cadastravel() {
		return CODIGO == ORIConstantes.LONG_ORI_CODIGO_CADASTRAVEL;
	}

	public void cadastra() throws ORIIncadastravelException, ORIException, SQLException {
		if (!cadastravel())
			throw new ORIIncadastravelException(this);
	}

	public void salva() throws ORIException, SQLException {
		if (cadastravel())
			throw new ORINaoEncontradoException(this);
	}

	public void remove() throws ORIException, SQLException {
		if (cadastravel())
			throw new ORINaoEncontradoException(this);
	}

	public long getCodigo() {
		return CODIGO;
	}
	
	/** Retorna uma descrição breve do objeto.
	 * 
	 * @return String
	 */
	protected String getORIDataString() {
		return this.getClass().getName() + " {"
				+ "\n  CODIGO: " + CODIGO
				+ "\n}";
	}
}
