package br.com.cledson.foibrinks.model.pessoal;

import java.sql.SQLException;

import br.com.cledson.foibrinks.bd.dao.DependenteDAO;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;

public class Dependente extends Pessoa {
	private final Cliente CLIENTE;

	/** Cria um cadastro de dependente.
	 * 
	 * NOTA: o dependente criado com este construtor deve ser registrado em
	 * rela��o a um objeto Cliente obtido do banco de dados.
	 */
	public Dependente() {
		super();
		CLIENTE = null;
	}

	/** Cria um dependente para cliente.
	 * 
	 * NOTA: este construtor s� pode ser usado por (Cliente).adicionaDependente().
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

	/** Constr�i um cadastro para o dependente existente no banco de dados.
	 * 
	 * @param codigo
	 * @param cliente
	 */
	public Dependente(long codigo, Cliente cliente) {
		super(codigo);
		this.CLIENTE = cliente;
	}

	/** Registra o dependente do cliente no banco de dados.
	 * Este m�todo n�o deve ser chamado antes do cliente associado ser registrado! 
	 * @throws SQLException 
	 * @throws ORIValorInvalidoException */
	@Override
	public void cadastra() throws PessoaIncadastravelException, SQLException {
		super.cadastra();
		if (CLIENTE.cadastravel())
			throw new PessoaIncadastravelException(this);

		DependenteDAO.registra(this);
	}
	
	/** Salva as altera��es nos dados do dependete j� cadastrado.
	 * O cliente nunca � afetado.
	 * 
	 * NOTA: esta fun��o deve ser chamada diretamente.
	 * 
	 * @throws PessoaNaoEncontradaException
	 * @throws PessoaJaExisteException 
	 * @throws SQLException
	 */
	@Override
	public void salva()
			throws PessoaNaoEncontradaException, PessoaJaExisteException, SQLException {
		super.salva();

		if (DependenteDAO.salva(this) == false)
			throw new PessoaNaoEncontradaException(this);
	}

	/** Esta fun��o � chamada por (Cliente).removeDependente() para realizar a remo��o
	 * do dependente.
	 * 
	 * @throws PessoaNaoEncontradaException
	 * @throws SQLException
	 */
	@Override
	public void remove() throws PessoaNaoEncontradaException, SQLException {
		if (DependenteDAO.remove(this) == false)
			throw new PessoaNaoEncontradaException(this);

	}

	public Cliente getCliente() {
		return CLIENTE;
	}
}
