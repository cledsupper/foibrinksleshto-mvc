package br.com.cledson.foibrinks.model.pessoal;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import br.com.cledson.foibrinks.model.ORI;
import br.com.cledson.foibrinks.model.ORIConstantes;
import br.com.cledson.foibrinks.model.ORIException;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.ORIUtilitarios;

public abstract class Pessoa extends ORI {
	public static final int NOME_COMPLETO_TAMANHO = 255;

	private String nomeCompleto;
	private Calendar dataNascimento;
	private String genero;

	/** Construtor APENAS para criar um objeto para novo cadastro. */
	public Pessoa() {
		super();
	}

	/** Construtor APENAS para a conversão de um objeto de um Banco de Dados.
	 * @param codigo - código do objeto no banco. */
	public Pessoa(long codigo) {
		super(codigo);
	}
	
	/** Verifica se o objeto é cadastrável no banco de dados.
	 * 
	 * @throws PessoaIncadastravelException caso o código do objeto seja diferente de -1.
	 */
	@Override
	public void cadastra() throws PessoaIncadastravelException, SQLException {
		try {
			super.cadastra();
		} catch (ORIException e) {
			throw new PessoaIncadastravelException(this);
		}
	}
	
	/** Método para alterar os dados no banco de dados.
	 * 
	 * @throws PessoaNaoEncontradaException
	 */
	@Override
	public void salva() throws PessoaNaoEncontradaException, SQLException {
		try {
			super.salva();
		} catch (ORIException e) {
			throw new PessoaNaoEncontradaException(this);
		}
	}

	/** Método para remover os dados no banco de dados.
	 * 
	 * @throws PessoaNaoEncontradaException
	 * @throws SQLException 
	 */
	public void remove() throws PessoaNaoEncontradaException, SQLException {
		try {
			super.remove();
		} catch (ORIException e) {
			throw new PessoaNaoEncontradaException(this);
		}
	}

	@Override
	protected String getORIDataString() {
		return this.getClass().getName() + " {"
				+ "\n  CODIGO: " + getCodigo() + ","
				+ "\n  nomeCompleto: \"" + nomeCompleto + "\""
				+ "\n}";
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}
	
	public String getDataNascimentoStringBr() {
		return ORIUtilitarios.dataParaStringBr(dataNascimento);
	}
	
	public String getDataNascimentoHTML() {
		return ORIUtilitarios.dataParaStringHTML(dataNascimento);
	}

	public String getGenero() {
		return genero;
	}

	public void setNomeCompleto(String nomeCompleto) throws ORIValorInvalidoException {
		PessoaValidador.validaNomeCompleto(this, nomeCompleto);
		this.nomeCompleto = nomeCompleto;
	}

	public void setDataNascimento(Calendar dataNascimento) throws ORIValorInvalidoException {
		PessoaValidador.validaDataNascimento(this, dataNascimento);
		this.dataNascimento = dataNascimento;
	}

	/** Altera data a partir de uma String com formato de data brasileiro: dd/MM/yyyy.
	 * Veja ORIUtilitarios e ORIConstantes.
	 * 
	 * @param dataNascimento
	 * @throws ORIValorInvalidoException
	 */
	public void setDataNascimentoStringBr(String dataNascimento) throws ORIValorInvalidoException {
		try {
			this.dataNascimento = ORIUtilitarios.stringBrParaData(dataNascimento);
		} catch (ParseException e) {
			throw new ORIValorInvalidoException(this, String.format(
					ORIConstantes.STRING_ERRO_ORI_VALOR_INVALIDO, "dataNascimento"));
		}
	}

	public void setDataNascimentoHTML(String dataNascimento) throws ORIValorInvalidoException {
		try {
			this.dataNascimento = ORIUtilitarios.dataHTMLParaData(dataNascimento);
		} catch (ParseException e) {
			throw new ORIValorInvalidoException(this,
					ORIConstantes.STRING_ERRO_ORI_VALOR_INVALIDO, "dataNascimento");
		}
	}

	public void setGenero(String genero) throws ORIValorInvalidoException {
		PessoaValidador.validaGenero(this, genero);
		this.genero = genero;
	}
}
