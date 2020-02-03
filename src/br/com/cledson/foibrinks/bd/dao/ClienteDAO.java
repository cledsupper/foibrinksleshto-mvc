package br.com.cledson.foibrinks.bd.dao;

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

/** ClienteDAO - Uma classe que oferece métodos para manipulação de dados dos
 * clientes no banco de dados.
 * 
 * @author Cledson Cavalcanti
 *
 */
public class ClienteDAO {
	/** Lê os dados de um cliente através do código.
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

	/** Lê os dados de um cliente através do nome e da data de nascimento.
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

	/** Para casos de urgência, é útil retornar o cliente através do CPF.
	 * 
	 * OBSERVAÇÃO: O CPF DEVE ESTAR NO FORMATO 'OK', VEJA A DOCUMENTAÇÃO DA CLASSE EM model.pessoal, Cliente, PARA SABER COMO TRANSFORMAR UM CPF 'legível para humanos' EM 'ok' (formato do BD).
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

	/** Pesquisa através do nome ou parte dele
	 * 
	 * @param nome			- pedaço do nome do cliente.
	 * @return Cliente		- objeto com dados do cliente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
	public static ArrayList<Cliente> listaPorNome(String nome) throws SQLException {
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

	/** Lista todos os clientes na ordem da primeira inclusão no banco.
	 * 
	 * Se recentes = true: a lista é ordenada a partir da maior data, i.e., os clientes são ordenados a partir do último cadastrado.
	 * 
	 * @param recentes				- ordenar a partir do último registro?
	 * @return ArrayList<Cliente>	- uma lista de clientes.
	 * @throws SQLException			- erro interno ou de engenharia.
	 */
	public static ArrayList<Cliente> lista(boolean recentes)
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
	 * para cadastro (isto é: código = ORIConstantes.LONG_ORI_CODIGO_CADASTRAVEL).
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
	 * @param codigo		- código do cliente.
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

	/** Verifica pela existência de um cliente
	 * 
	 * NOTA: os dados verificados são o nome completo e a data de nascimento em conjunto, e o CPF.
	 * 
	 * @param cliente					- objeto com os dados do cliente.
	 * @throws PessoaJaExisteException	- caso um cliente com os mesmos dados já exista.
	 * @throws SQLException				- erro interno ou de engenharia.
	 */
	public static void verificaSeExiste(Cliente cliente)
			throws PessoaJaExisteException, SQLException {
		Cliente outro_cliente = lePorNomeData(cliente.getNomeCompleto(),
				cliente.getDataNascimento());
		if (outro_cliente != null)
			if (outro_cliente.getCodigo() != cliente.getCodigo())
				throw new PessoaJaExisteException(cliente,
					"Outro cliente com mesmo nome e data de nascimentos já existe.");

		outro_cliente = lePorCpf(cliente.getCpf());
		if (outro_cliente != null)
			if (outro_cliente.getCodigo() != cliente.getCodigo())
				throw new PessoaJaExisteException(cliente,
					"Outro cliente com o mesmo CPF já existe.");
	}

	/** Transforma dados de um ResultSet em um Cliente.
	 * 
	 *  @param rs - ResultSet
	 *  @return Cliente
	 */
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
