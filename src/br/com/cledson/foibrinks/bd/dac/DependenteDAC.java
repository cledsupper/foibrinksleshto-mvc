package br.com.cledson.foibrinks.bd.dac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.ConnectionFactory;
import br.com.cledson.foibrinks.model.pessoal.Cliente;
import br.com.cledson.foibrinks.model.pessoal.Dependente;

public class DependenteDAC {
	public static Dependente le(long codigo) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM clientes_dependentes WHERE codigo=?");
		stmt.setLong(1, codigo);

		Long codigo_cliente = null;
		Dependente dep = null;
		Cliente cliente = null;

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			codigo_cliente = rs.getLong("codigo_cliente");
			cliente = Cliente.procura(codigo_cliente);
			dep = resultSetParaDependente(rs, cliente);
		}
		rs.close();
		stmt.close();
		conn.close();

		return dep;
	}

	public static Dependente lePorNomeData(Cliente cliente,
			String nomeCompleto,
			Calendar dataNascimento) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM clientes_dependentes" +
				" WHERE nome_completo=? AND data_nascimento=?");
		stmt.setString(1, nomeCompleto);
		stmt.setDate(2, new java.sql.Date(dataNascimento.getTimeInMillis()));
		
		Dependente dep = null;
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			dep = resultSetParaDependente(rs, cliente);

		return dep;
	}

	public static ArrayList<Dependente> listaDependentes(Cliente cliente)
			throws SQLException {
		Connection conn = ConnectionFactory.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM clientes_dependentes WHERE codigo_cliente=?");
		stmt.setLong(1, cliente.getCodigo());
		ResultSet rs = stmt.executeQuery();

		ArrayList<Dependente> dependentes = new ArrayList<Dependente>();
		while (rs.next())
			dependentes.add(resultSetParaDependente(rs, cliente));

		stmt.close();
		conn.close();

		return dependentes;
	}

	public static void registra(Dependente dependente)
			throws SQLException {
		Connection conn = ConnectionFactory.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement(
						"INSERT INTO clientes_dependentes(codigo_cliente, nome_completo, data_nascimento, genero)" +
						" VALUES(?, ?, ?, ?)");
		stmt.setLong(1, dependente.getCliente().getCodigo());
		stmt.setString(2, dependente.getNomeCompleto());
		stmt.setDate(3, new java.sql.Date(
				dependente.getDataNascimento().getTimeInMillis()));
		stmt.setString(4, dependente.getGenero());
		
		stmt.executeUpdate();
		stmt.close();
		conn.close();
	}

	public static boolean salva(Dependente dependente) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt = conn
				.prepareStatement("UPDATE clientes_dependentes SET nome_completo=?, " +
						"data_nascimento=?, genero=? WHERE codigo=?");
		stmt.setString(1, dependente.getNomeCompleto());
		stmt.setDate(2, new java.sql.Date(
				dependente.getDataNascimento().getTimeInMillis()));
		stmt.setString(3, dependente.getGenero());
		stmt.setLong(4, dependente.getCodigo());
		
		int row_count = stmt.executeUpdate();
		
		stmt.close();
		conn.close();
		return row_count > 0;
	}
	
	public static boolean remove(Dependente dependente) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM clientes_dependentes WHERE codigo=?");
		stmt.setLong(1, dependente.getCodigo());
		
		int row_count = stmt.executeUpdate();
		
		stmt.close();
		conn.close();
		return row_count > 0;
	}

	private static Dependente resultSetParaDependente(ResultSet rs,
			Cliente cliente) throws SQLException {
		Dependente dep = new Dependente(rs.getLong("codigo"), cliente);

		try {
			dep.setNomeCompleto(rs.getString("nome_completo"));

			Calendar cal = Calendar.getInstance();
			cal.setTime(rs.getDate("data_nascimento"));
			dep.setDataNascimento(cal);

			dep.setGenero(rs.getString("genero"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dep;
	}
}
