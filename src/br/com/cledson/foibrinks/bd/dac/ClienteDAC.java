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

public class ClienteDAC {
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

	public static void registra(Cliente cliente) throws SQLException {
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

	public static boolean salva(Cliente cliente) throws SQLException {
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

	public static void verificaSeExiste(Cliente cliente)
			throws PessoaIncadastravelException, SQLException {
		Cliente outro_cliente = lePorNomeData(cliente.getNomeCompleto(),
				cliente.getDataNascimento());
		if (outro_cliente != null)
			throw new PessoaIncadastravelException(cliente,
					"Cliente já cadastrado.");

		outro_cliente = lePorCpf(cliente.getCpf());
		if (outro_cliente != null)
			throw new PessoaIncadastravelException(cliente,
					"Cliente já cadastrado.");
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
