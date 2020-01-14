package br.com.cledson.foibrinks.model.pessoal;

import java.sql.SQLException;

import br.com.cledson.foibrinks.bd.dac.DependenteDAC;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;

public class Dependente extends Pessoa {
	private final Cliente CLIENTE;

	/** Cria um cadastro de dependente.
	 * 
	 * NOTA: o dependente criado com este construtor deve ser registrado em
	 * relação a um objeto Cliente obtido do banco de dados.
	 */
	public Dependente() {
		super();
		CLIENTE = null;
	}

	/** Cria um dependente para cliente.
	 * 
	 * NOTA: este construtor só pode ser usado por (Cliente).adicionaDependente().
	 * 
	 * @param cliente	- objeto com os dados do cliente.
	 * @param dep		- objeto com os dados do novo dependente.
	 */
	protected Dependente(Cliente cliente, Dependente dep) {
		super();
		this.CLIENTE = cliente;
		try {
			setNomeCompleto(dep.getNomeCompleto());
			setDataNascimento(dep.getDataNascimento());
			setGenero(dep.getGenero());
		} catch (ORIValorInvalidoException e) {
			e.printStackTrace();
		}
	}

	/** Constrói um cadastro para o dependente existente no banco de dados.
	 * 
	 * @param codigo
	 * @param cliente
	 */
	public Dependente(long codigo, Cliente cliente) {
		super(codigo);
		this.CLIENTE = cliente;
	}

	public static Dependente procura(long codigo) throws SQLException {
		return DependenteDAC.le(codigo);
	}

	public static Dependente[] procuraDependentes(long codigoCliente) {
		// TODO implemente este método
		return null;
	}
	
	public static Dependente[] procuraDependentes(Cliente cliente) {
		return procuraDependentes(cliente.getCodigo());
	}

	/** Registra o dependente do cliente no banco de dados.
	 * Este método não deve ser chamado antes do cliente associado ser registrado! 
	 * @throws SQLException 
	 * @throws ORIValorInvalidoException */
	@Override
	public void cadastra() throws PessoaIncadastravelException, SQLException {
		super.cadastra();
		if (CLIENTE.cadastravel())
			throw new PessoaIncadastravelException(this);

		DependenteDAC.registra(this);
	}
	
	/** Salva as alterações nos dados do dependete já cadastrado.
	 * O cliente nunca é afetado.
	 * 
	 * NOTA: esta função deve ser chamada diretamente.
	 * 
	 * @throws PessoaNaoEncontradaException
	 * @throws PessoaJaExisteException 
	 * @throws SQLException
	 */
	@Override
	public void salva()
			throws PessoaNaoEncontradaException, PessoaJaExisteException, SQLException {
		super.salva();

		if (DependenteDAC.salva(this) == false)
			throw new PessoaNaoEncontradaException(this);
	}

	/** Esta função é chamada por (Cliente).removeDependente() para realizar a remoção
	 * do dependente.
	 * 
	 * @throws PessoaNaoEncontradaException
	 * @throws SQLException
	 */
	@Override
	public void remove() throws PessoaNaoEncontradaException, SQLException {
		if (DependenteDAC.remove(this) == false)
			throw new PessoaNaoEncontradaException(this);

	}

	public Cliente getCliente() {
		return CLIENTE;
	}
}
