package br.com.cledson.foibrinks.bd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.ConnectionFactory;
import br.com.cledson.foibrinks.model.pessoal.Cliente;
import br.com.cledson.foibrinks.model.pessoal.Dependente;
import br.com.cledson.foibrinks.model.pessoal.PessoaIncadastravelException;
import br.com.cledson.foibrinks.model.pessoal.PessoaJaExisteException;

/** DependenteDAC - uma classe que oferece métodos para manipulação de dados dos
 * dependentes dos clientes no banco de dados.
 * 
 * @author Cledson Cavalcanti
 *
 */
public class DependenteDAO {
	/** Lê os dados de um dependente através do seu código.
	 * 
	 * @param codigo		- autoexplicativo.
	 * @return Dependente	- objeto com dados do dependente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
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

	/** Lê os dados de um dependente através do nome e da data de nascimento.
	 * 
	 * @param codigo		- autoexplicativo.
	 * @return Cliente		- objeto com dados do cliente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
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

	/** Retorna os dependentes do cliente em uma lista.
	 * 
	 * @param cliente					- objeto com o código do cliente.
	 * @return ArrayList<Dependente>	- lista de dependentes do cliente.
	 * @throws SQLException				- erro interno ou de engenharia.
	 */
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

	/** Registra os dados do dependente no banco de dados.
	 * 
	 * @param dependente					- objeto com os dados do dependente, marcado para cadastro (isto é: código = ORIConstantes.LONG_ORI_CODIGO_CADASTRAVEL).
	 * @throws SQLException					- erro interno ou de engenharia.
	 * @throws PessoaIncadastravelException	- dependente já registrado.
	 */
	public static void registra(Dependente dependente)
			throws SQLException, PessoaIncadastravelException {
		try {
			verificaSeExiste(dependente);
		} catch (PessoaJaExisteException e) {
			throw new PessoaIncadastravelException(e);
		}

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

	/** Atualiza os dados de um dependente existente no banco de dados.
	 * 
	 * @param dependente	- objeto com os dados do dependente.
	 * @return boolean		- se alguma linha foi afetada.
	 * @throws SQLException - erro interno ou de engenharia.
	 * @throws PessoaJaExisteException - outro dependente com
	 * mesmo nome/data de nascimento já existe.
	 */
	public static boolean salva(Dependente dependente) throws SQLException, PessoaJaExisteException {
		verificaSeExiste(dependente);

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

	/** Remove os dados de um dependente do banco de dados.
	 * 
	 * @param dependente	- objeto com os dados do dependente.
	 * @return boolean		- se alguma linha foi afetada.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
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

	/** Converte um ResultSet para Dependente.
	 * 
	 * @param rs			- ResultSet da consulta ao banco.
	 * @param cliente		- cliente responsável pelo dependente.
	 * @return Dependente	- objeto com os dados do dependente.
	 * @throws SQLException - erro interno ou de engenharia.
	 */
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
	
	/** Verifica pela existência de um dependente.
	 * 
	 * NOTA: os dados verificados são o nome completo e a data de nascimento. 
	 * 
	 * @param dependente				- objeto com dados de dependente.
	 * @throws SQLException				- erro interno ou de engenharia
	 * @throws PessoaJaExisteException	- dependente com mesmos dados já existe.
	 */
	private static void verificaSeExiste(Dependente dependente)
			throws SQLException, PessoaJaExisteException {
		Dependente outro_dep = lePorNomeData(dependente.getCliente(),
				dependente.getNomeCompleto(), dependente.getDataNascimento());
		if (outro_dep != null)
			if (outro_dep.getCodigo() != dependente.getCodigo())
				throw new PessoaJaExisteException(dependente,
						"Um dependente com mesmo nome/data de nascimento já existe.");
	}
}
