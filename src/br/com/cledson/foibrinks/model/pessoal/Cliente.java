package br.com.cledson.foibrinks.model.pessoal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.dac.ClienteDAC;
import br.com.cledson.foibrinks.bd.dac.DependenteDAC;
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
		return ClienteDAC.le(codigo);
	}

	public static Cliente procuraPorCPF(String cpf) throws SQLException {
		String cpfOk = ClienteValidador.paraCpfOk(cpf);
		if (cpfOk != null)
			return ClienteDAC.lePorCpf(cpfOk);

		return null;
	}

	public static Cliente[] procuraPorNome(String nome) throws SQLException {
		return (Cliente[]) ClienteDAC.pesquisaPorNome(nome).toArray();
	}

	public static Cliente procuraPorNomeData(String nome, Calendar data) throws SQLException {
		return ClienteDAC.lePorNomeData(nome, data);
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
	 * @throws PessoaIncadastravelException
	 * @throws ORIValorInvalidoException
	 * @throws SQLException
	 */
	@Override
	public void cadastra() throws PessoaIncadastravelException, SQLException {
		super.cadastra();

		if (ClienteDAC.lePorNomeData(getNomeCompleto(), getDataNascimento()) != null)
			throw new PessoaIncadastravelException(this, "Um cliente com mesmo nome e data de nascimento já existe");
		else if (ClienteDAC.lePorCpf(this.getCpf()) != null)
			throw new PessoaIncadastravelException(this, "Um cliente com o mesmo CPF já existe");

		ClienteDAC.registra(this);
	}

	/**
	 * Verifica e salva os dados no banco de dados.
	 * 
	 * @throws PessoaNaoEncontradaException
	 */
	@Override
	public void salva() throws PessoaNaoEncontradaException, SQLException {
		super.salva();

		if (ClienteDAC.salva(this) == false)
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

		if (ClienteDAC.remove(getCodigo()) == false)
			throw new PessoaNaoEncontradaException(this);
	}

	public ArrayList<Dependente> getDependentes() throws SQLException {
		return DependenteDAC.listaDependentes(this);
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
