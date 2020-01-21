package br.com.cledson.foibrinks.model.pessoal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.dao.ClienteDAO;
import br.com.cledson.foibrinks.bd.dao.DependenteDAO;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.ORIUtilitarios;

public class Cliente extends Pessoa {
	private final Calendar DATA_CADASTRO;

	private String cpf;
	private String estadoCivil;
	private String rua;
	private String bairro;
	private String cep;
	private String estado;
	private String cidade;

	/** Cria dados para cadastro de um novo cliente. */
	public Cliente() {
		super();
		DATA_CADASTRO = null;
	}

	/** Cria um objeto cliente para leitura do banco de dados. */
	public Cliente(long codigo, Calendar dataCadastro) {
		super(codigo);
		DATA_CADASTRO = dataCadastro;
	}

	public static Cliente procura(long codigo) throws SQLException {
		return ClienteDAO.getCliente(codigo);
	}

	public static Cliente procuraPorCPF(String cpf) throws SQLException {
		String cpfOk = ClienteValidador.paraCpfOk(cpf);
		if (cpfOk != null)
			return ClienteDAO.getClientePorCpf(cpfOk);

		return null;
	}

	public static Cliente[] procuraPorNome(String nome) throws SQLException {
		return (Cliente[]) ClienteDAO.getListaPorNome(nome).toArray();
	}

	public static Cliente procuraPorNomeData(String nome, Calendar data) throws SQLException {
		return ClienteDAO.getClientePorNomeData(nome, data);
	}

	public void adicionaDependente(Dependente dep)
			throws PessoaNaoEncontradaException, PessoaIncadastravelException, SQLException {
		if (cadastravel())
			throw new PessoaNaoEncontradaException(this);

		Dependente dependenteParaCadastro = new Dependente(this, dep);
		dependenteParaCadastro.cadastra();
	}

	/*
	public void removeDependente(Dependente dep)
			throws PessoaNaoEncontradaException, SQLException {
		if (cadastravel())
			throw new PessoaNaoEncontradaException(this);
		dep.remove();
	}
	*/

	/**
	 * Prepara os dados do cliente para cadastro no banco de dados e chama ClienteDAO.cadastra().
	 * 
	 * @throws PessoaIncadastravelException	- outro cliente com nome/data ou CPF
	 * alterados já existe.
	 * @throws SQLException					- erro interno ou de engenharia.
	 */
	@Override
	public void cadastra() throws PessoaIncadastravelException, SQLException {
		super.cadastra();

		ClienteDAO.registra(this);
	}

	/**
	 * Verifica e salva os dados no banco de dados.
	 * 
	 * @throws PessoaNaoEncontradaException	- cliente não existe no banco de dados.
	 * @throws PessoaJaExisteException		- outro cliente com nome/data ou CPF
	 * alterados já existe.
	 * @throws SQLException					- erro interno ou de engenharia.
	 */
	@Override
	public void salva()
			throws PessoaNaoEncontradaException, PessoaJaExisteException,
			SQLException {
		super.salva();

		if (ClienteDAO.salva(this) == false)
			throw new PessoaNaoEncontradaException(this);
	}

	/**
	 * Remove os dados do cliente no banco de dados.
	 * 
	 * @throws PessoaNaoEncontradaException
	 * @throws SQLException
	 */
	@Override
	public void remove() throws PessoaNaoEncontradaException, SQLException {
		super.remove();

		if (ClienteDAO.remove(getCodigo()) == false)
			throw new PessoaNaoEncontradaException(this);
	}

	public ArrayList<Dependente> getDependentes() throws SQLException {
		return DependenteDAO.listaDependentes(this);
	}

	public Calendar getDataCadastro() {
		return DATA_CADASTRO;
	}

	public String getDataCadastroStringBr() {
		return ORIUtilitarios.dataHoraParaStringBr(DATA_CADASTRO);
	}

	public String getCpf() {
		return cpf;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public String getRua() {
		return rua;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCep() {
		return cep;
	}

	public String getEstado() {
		return estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCpf(String cpf) throws ORIValorInvalidoException {
		this.cpf = ClienteValidador.paraCpfOk(this, cpf);
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
}
