package br.com.cledson.foibrinks.model.mercado;

import java.sql.SQLException;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.dao.ProdutoDAO;
import br.com.cledson.foibrinks.model.ORI;
import br.com.cledson.foibrinks.model.ORIException;
import br.com.cledson.foibrinks.model.ORIIncadastravelException;
import br.com.cledson.foibrinks.model.ORINaoEncontradoException;
import br.com.cledson.foibrinks.model.ORIUtilitarios;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;

public class Produto extends ORI {
	private final Calendar DATA_CADASTRO;
	
	private String nome;
	private String marca;
	private short faixaEtaria;
	private double[] dimensoes = new double[Dimensao.TAMANHO];
	private double peso;
	private double preco;

	/** Cria um produto para cadastro no banco de dados. */
	public Produto() {
		super();
		DATA_CADASTRO = null;
	}

	/** Para leitura de um produto já existente no banco de dados.
	 * 
	 * @param codigo - do produto existente.
	 * @param dataCadastro - data de cadastro.
	 */
	public Produto(long codigo, Calendar dataCadastro) {
		super(codigo);
		DATA_CADASTRO = dataCadastro;
	}

	/** Lê um produto do banco de dados.
	 * 
	 * @param codigo
	 * @return produto
	 * @throws SQLException
	 */
	public static Produto procura(long codigo) throws SQLException {
		return ProdutoDAO.le(codigo);
	}

	@Override
	public void cadastra() throws ORIIncadastravelException, ORIException,
			SQLException {
		super.cadastra();

		ProdutoDAO.registra(this);
	}

	@Override
	public void salva() throws ORIException, SQLException {
		super.salva();

		ProdutoDAO.salva(this);
	}

	@Override
	public void remove() throws ORIException, SQLException {
		super.remove();

		ProdutoDAO.remove(this);
	}
	
	@Override
	protected String getORIDataString() {
		return this.getClass().getName() + " {"
				+ "\n  CODIGO: " + getCodigo() + ","
				+ "\n  DATA_CADASTRO: " + getDataCadastroStringBr() + ","
				+ "\n  nome: \"" + nome + "\","
				+ "\n  marca: \"" + marca + "\","
				+ "\n  pre�o: " + faixaEtaria
				+ "\n}";
	}

	public Calendar getDataCadastro() {
		return DATA_CADASTRO;
	}
	
	public String getDataCadastroStringBr() {
		return ORIUtilitarios.dataHoraParaStringBr(DATA_CADASTRO);
	}

	public String getNome() {
		return nome;
	}

	public String getMarca() {
		return marca;
	}

	public short getFaixaEtaria() {
		return faixaEtaria;
	}

	public double[] getDimensoes() {
		return dimensoes;
	}

	/** Retorna um tamanho de acordo com a dimensão.
	 * 
	 * @param dimensao
	 * @return tamanho
	 * @throws IndexOutOfBoundsException se dimensao = Dimensao.Enum.Tamanho
	 */
	public double getDimensao(Dimensao.Enum dimensao) {
		switch (dimensao) {
		case Altura:
			return dimensoes[Dimensao.ALTURA];
		case Largura:
			return dimensoes[Dimensao.LARGURA];
		case Profundidade:
			return dimensoes[Dimensao.PROFUNDIDADE];
		case Todas:
			throw new IndexOutOfBoundsException(
					this.getClass().getName() + ".getDimensao(dimensao = Dimensao.Enum.Tamanho)");
		}
		return Double.NaN;
	}
	
	public double getVolume() {
		double volume = dimensoes[Dimensao.ALTURA] * dimensoes[Dimensao.LARGURA] * dimensoes[Dimensao.PROFUNDIDADE];
		return volume;
	}
	
	public double getFreteParaLua() {
		return getPeso()*ProdutoConstantes.DOUBLE_FRETE_PARA_LUA;
	}

	public double getPeso() {
		return peso;
	}

	public double getPreco() {
		return preco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setFaixaEtaria(short faixaEtaria) throws ORIValorInvalidoException {
		ProdutoValidador.validaFaixaEtaria(this, faixaEtaria);
		this.faixaEtaria = faixaEtaria;
	}

	/** Altera um tamanho de acordo com a dimensão para 'valor'.
	 * Altera todos os tamanhos para 'valor' se dimensao = Dimensao.Enum.Tamanho.
	 * 
	 * @param valor
	 * @param dimensao
	 * @throws ORIValorInvalidoException 
	 */
	public void setDimensao(double valor, Dimensao.Enum dimensao) throws ORIValorInvalidoException {
		ProdutoValidador.validaDimensao(this, valor, dimensao);
		switch (dimensao) {
		case Altura:
			dimensoes[Dimensao.ALTURA] = valor;
			break;
		case Largura:
			dimensoes[Dimensao.LARGURA] = valor;
			break;
		case Profundidade:
			dimensoes[Dimensao.PROFUNDIDADE] = valor;
			break;
		case Todas:
			dimensoes[Dimensao.ALTURA] = valor;
			dimensoes[Dimensao.LARGURA] = valor;
			dimensoes[Dimensao.PROFUNDIDADE] = valor;
		}
	}

	public void setPeso(double peso) throws ORIValorInvalidoException {
		ProdutoValidador.validaPeso(this, peso);
		this.peso = peso;
	}

	public void setPreco(double preco) throws ORIValorInvalidoException {
		ProdutoValidador.validaPreco(this, preco);
		this.preco = preco;
	}

}
