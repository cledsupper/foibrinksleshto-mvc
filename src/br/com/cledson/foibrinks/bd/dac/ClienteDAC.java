package br.com.cledson.foibrinks.bd.dac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.ConnectionFactory;
import br.com.cledson.foibrinks.model.pessoal.Cliente;
import br.com.cledson.foibrinks.model.pessoal.PessoaIncadastravelException;
import br.com.cledson.foibrinks.model.pessoal.PessoaJaExisteException;

/** ClienteDAC - Uma classe que oferece m�todos para manipula��o de dados dos
 * clientes no banco de dados.
 * <br>
 * <br>
 * <h1>Por qu� DAC (Data Access Class) e n�o DAO (Data Access Object)?</h1>
 * <p>N�o estou usando o padr�o DAO (Data Access Object) de prop�sito,
 * porque N�O � �TIL instanciar um objeto apenas para usar uma fun��o/
 * procedimento (f/p) que nem utiliza ele para funcionar.</p>
 * <br><br>
 * <h2>O que s�o m�todos?<h2>
 * <p>M�todos no paradigma de programa��o orientada a objetos existem
 * por uma justificativa: se h� <strong>um</strong> (<strong>1</strong>
 * ) objeto com atributos que caracterizam ele de tal maneira (em uma
 * palavra: "ESTADO" do objeto), e uma fun��o/procedimento DEPENDE do
 * estado desse objeto e OPERA sobre ele ou representa o seu
 * comportamento, ent�o ela deve ser declarada/definida como um m�todo.</p>
 * <br>
 * <h3>Express�o l�gica que define esse axioma, onde "foo" se refere a
 * uma f/p:</h3>
 * <blockquote>foo.depends_of(object) ^ (foo.operates_in(object) OR foo
 * .represents_behavior_of(object)) => foo.is_method()</blockquote>
 * <br>
 * <p>Todo m�todo de <strong>um</strong> objeto possui embutido um
 * ponteiro <strong>this</strong> exatamente por essa justificativa: o
 *  m�todo DEPENDE DO ESTADO DO OBJETO PARA FUNCIONAR.</p>
 * <br><br>
 * <h2>O que s�o fun��es e procedimentos?</h2>
 * <p>Em contrapartida, as <strong>fun��es e procedimentos</strong>
 * existem para realizar algum trabalho independente do estado de um
 * objeto, dependendo apenas de seus par�metros de entrada (que podem
 * conter um ou mais objetos).</p>
 * <br>
 * <p>Por serem um conjunto maior em rela��o aos m�todos, elas n�o
 * possuem um ponteiro this. Um m�todo � uma f/p, mas nem toda f/p �
 * um m�todo.</p>
 * <br><br>
 * <h2>RESUMO "DA �PERA"</h2>
 * <p>Porque nenhuma das f/ps presentes nesta classe operariam sob um
 * atributo de inst�ncia da classe, n�o as defini como m�todos.</p>
 * <br>
 * <p>As f/ps desta classe n�o dependem do estado de <strong>nenhum</strong>
 *  (<strong>0</strong>) objeto, ou sequer precisam de um objeto
 *  diretamente (exemplo: static Numero soma(Numero numero1, Numero
 *  numero2) � uma fun��o de classe que depende de <strong>DOIS</strong>
 *   (<strong>2</strong>) objetos do tipo Numero, logo n�o � m�todo)</p>
 * <br><br>
 * <h2><em>Entre para o movimento #DeixeOsObjetosBrincar(), em prol
 * do respeito ao objeto enquanto Ser Objeto!</em></h2>
 * 
 * @author Cledson Cavalcanti
 *
 */
public class ClienteDAC {
	/** L� os dados de um cliente atrav�s do c�digo.
	 * 
	 * @param codigo		- autoexplicativo.
	 * @return Cliente		- objeto com dados do cliente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
	public static Cliente le(long codigo) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		Cliente cliente = null;
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM clientes WHERE codigo = ?");
		stmt.setLong(1, codigo);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			cliente = resultSetParaCliente(rs);
		rs.close();
		stmt.close();
		conn.close();
		return cliente;
	}

	/** L� os dados de um cliente atrav�s do nome e da data de nascimento.
	 * 
	 * @param codigo		- autoexplicativo.
	 * @return Cliente		- objeto com dados do cliente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
	public static Cliente lePorNomeData(String nomeCompleto,
			Calendar dataNascimento) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		Cliente cliente = null;
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM clientes WHERE nome_completo = ? AND data_nascimento = ?");
		stmt.setString(1, nomeCompleto);
		stmt.setDate(2, new java.sql.Date(dataNascimento.getTimeInMillis()));
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			cliente = resultSetParaCliente(rs);
		rs.close();
		stmt.close();
		conn.close();
		return cliente;
	}

	/** Para casos de urg�ncia, � �til retornar o cliente atrav�s do CPF.
	 * 
	 * OBSERVA��O: O CPF DEVE ESTAR NO FORMATO 'OK', VEJA A DOCUMENTA��O DA CLASSE EM model.pessoal, Cliente, PARA SABER COMO TRANSFORMAR UM CPF 'leg�vel para humanos' EM 'ok' (formato do BD).
	 * 
	 * @param cpf			- CPF no formato 'OK' (ex.: 12345678901)
	 * @return Cliente		- objeto com dados do cliente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
	public static Cliente lePorCpf(String cpf) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		Cliente cliente = null;
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM clientes WHERE cpf = ?");
		stmt.setString(1, cpf);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			cliente = resultSetParaCliente(rs);
		rs.close();
		stmt.close();
		conn.close();
		return cliente;
	}

	/** Pesquisa atrav�s do nome ou parte dele
	 * 
	 * @param nome			- peda�o do nome do cliente.
	 * @return Cliente		- objeto com dados do cliente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
	public static ArrayList<Cliente> pesquisaPorNome(String nome) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM clientes WHERE nome_completo LIKE '%?%'");
		stmt.setString(1, nome);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			clientes.add(resultSetParaCliente(rs));
		rs.close();
		stmt.close();
		conn.close();
		return clientes;
	}

	/** Lista todos os clientes na ordem da primeira inclus�o no banco.
	 * 
	 * Se recentes = true: a lista � ordenada a partir da maior data, i.e., os clientes s�o ordenados a partir do �ltimo cadastrado.
	 * 
	 * @param recentes				- ordenar a partir do �ltimo registro?
	 * @return ArrayList<Cliente>	- uma lista de clientes.
	 * @throws SQLException			- erro interno ou de engenharia.
	 */
	public static ArrayList<Cliente> listaClientes(boolean recentes)
		throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM clientes";

		if (recentes)
			sql += " ORDER BY data_cadastro DESC";

		PreparedStatement stmt = conn
				.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		while (rs.next())
			clientes.add(resultSetParaCliente(rs));

		rs.close();
		stmt.close();
		conn.close();

		return clientes;
	}

	/** Registra os dados de um cliente no banco de dados.
	 * 
	 * @param Cliente						- objeto com dados do cliente, marcado
	 * para cadastro (isto �: c�digo = ORIConstantes.LONG_ORI_CODIGO_CADASTRAVEL).
	 * @throws SQLException					- erro interno ou de engenharia.
	 * @throws PessoaIncadastravelException - erro de duplicidade.
	 */
	public static void registra(Cliente cliente)
			throws SQLException, PessoaIncadastravelException {
		try {
			verificaSeExiste(cliente);
		} catch (PessoaJaExisteException e) {
			throw new PessoaIncadastravelException(e);
		}

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO clientes("
						+ "nome_completo, data_nascimento, cpf, genero, estado_civil, rua, "
						+ "bairro, cep, cidade, estado"
						+ ") VALUES(?, ?, ?, ?, ?, ?, " + "	?, ?, ?, ?)");
		stmt.setString(1, cliente.getNomeCompleto());
		stmt.setDate(2, new java.sql.Date(cliente.getDataNascimento()
				.getTimeInMillis()));
		stmt.setString(3, cliente.getCpf());
		stmt.setString(4, cliente.getGenero());
		stmt.setString(5, cliente.getEstadoCivil());
		stmt.setString(6, cliente.getRua());

		stmt.setString(7, cliente.getBairro());
		stmt.setString(8, cliente.getCep());
		stmt.setString(9, cliente.getCidade());
		stmt.setString(10, cliente.getEstado());

		stmt.execute();
		stmt.close();
		conn.close();
	}

	/** Salva os dados de um cliente existente no banco de dados.
	 * 
	 * @param cliente					- objeto com os dados do cliente.
	 * @return boolean					- se alguma linha foi afetada.
	 * @throws SQLException 			- erro interno ou de engenharia.
	 * @throws PessoaJaExisteException	- erro de duplicidade.
	 */
	public static boolean salva(Cliente cliente)
			throws SQLException, PessoaJaExisteException {
		verificaSeExiste(cliente);

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("UPDATE clientes SET nome_completo=?, data_nascimento=?,"
						+ " cpf=?, genero=?, estado_civil=?,"
						+ " rua=?, bairro=?, cep=?, cidade=?, estado=? WHERE codigo = ?");
		stmt.setString(1, cliente.getNomeCompleto());
		stmt.setDate(2, new java.sql.Date(cliente.getDataNascimento().getTimeInMillis()));

		stmt.setString(3, cliente.getCpf());
		stmt.setString(4, cliente.getGenero());
		stmt.setString(5, cliente.getEstadoCivil());

		stmt.setString(6, cliente.getRua());
		stmt.setString(7, cliente.getBairro());
		stmt.setString(8, cliente.getCep());
		stmt.setString(9, cliente.getCidade());
		stmt.setString(10, cliente.getEstado());
		stmt.setLong(11, cliente.getCodigo());

		int row_count = stmt.executeUpdate();
		stmt.close();
		conn.close();

		return row_count > 0;	
	}

	/** Remove os dados do cliente do banco de dados.
	 * 
	 * @param codigo		- c�digo do cliente.
	 * @return boolean		- se alguma linha foi afetada.
	 * @throws SQLException	- erro interno ou de engenharia.
	 */
	public static boolean remove(long codigo) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM clientes WHERE codigo = ?");
		stmt.setLong(1, codigo);

		int row_count = stmt.executeUpdate();
		stmt.close();
		conn.close();

		return row_count > 0;
	}

	/** Verifica se um cliente com mesmo nome e data de nascimento j� existe no banco de dados.
	 * 
	 * @param cliente					- objeto com os dados do cliente.
	 * @throws PessoaJaExisteException	- caso um cliente com os mesmos dados j� exista.
	 * @throws SQLException				- erro interno ou de engenharia.
	 */
	public static void verificaSeExiste(Cliente cliente)
			throws PessoaJaExisteException, SQLException {
		Cliente outro_cliente = lePorNomeData(cliente.getNomeCompleto(),
				cliente.getDataNascimento());
		if (outro_cliente != null)
			if (outro_cliente.getCodigo() != cliente.getCodigo())
				throw new PessoaJaExisteException(cliente,
					"Outro cliente com mesmo nome e data de nascimentos j� existe.");

		outro_cliente = lePorCpf(cliente.getCpf());
		if (outro_cliente != null)
			if (outro_cliente.getCodigo() != cliente.getCodigo())
				throw new PessoaJaExisteException(cliente,
					"Outro cliente com o mesmo CPF j� existe.");
	}

	private static Cliente resultSetParaCliente(ResultSet rs)
			throws SQLException {
		Cliente cliente;

		Calendar dataCadastro = Calendar.getInstance();
		dataCadastro.setTime(rs.getDate("data_cadastro"));
	
		// 1, 2
		cliente = new Cliente(rs.getLong("codigo"), dataCadastro);

		try {
			// 3
			cliente.setNomeCompleto(rs.getString("nome_completo"));
			// 4
			Calendar dataNascimento = Calendar.getInstance();
			dataNascimento.setTime(rs.getDate("data_nascimento"));
			cliente.setDataNascimento(dataNascimento);
	
			// 5, 6
			cliente.setCpf(rs.getString("cpf"));
			cliente.setGenero(rs.getString("genero"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 7, 8, 9, 10, 11, 12
		cliente.setEstadoCivil(rs.getString("estado_civil"));
		cliente.setRua(rs.getString("rua"));
		cliente.setBairro(rs.getString("bairro"));
		cliente.setCep(rs.getString("cep"));
		cliente.setEstado(rs.getString("estado"));
		cliente.setCidade(rs.getString("cidade"));

		return cliente;
	}
}
