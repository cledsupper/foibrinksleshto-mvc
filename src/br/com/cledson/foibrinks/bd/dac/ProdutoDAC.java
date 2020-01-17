package br.com.cledson.foibrinks.bd.dac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.ConnectionFactory;
import br.com.cledson.foibrinks.model.mercado.Produto;
import br.com.cledson.foibrinks.model.mercado.Dimensao;

public class ProdutoDAC {
	public static Produto le(long codigo) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM produtos WHERE codigo=?");
		stmt.setLong(1, codigo);

		ResultSet rs = stmt.executeQuery();
		Produto produto = null;
		if (rs.next())
			produto = resultSetParaProduto(rs);
		
		rs.close();
		stmt.close();
		conn.close();
		return produto;
	}

	public static Produto lePorNomeMarca(String nome, String marca) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM produtos WHERE nome=? AND marca=?");
		stmt.setString(1, nome);
		stmt.setString(2, marca);

		ResultSet rs = stmt.executeQuery();
		Produto produto = null;
		if (rs.next())
			produto = resultSetParaProduto(rs);
		
		rs.close();
		stmt.close();
		conn.close();
		
		return produto;
	}

	/** Retorna de uma lista 
	 * 
	 * @param recentes - caso true, ordena a lista a partir do último produto cadastrado.
	 * @param faixaEtaria - caso não nulo, filtra os produtos pela faixa etária indicada
	 * @param marca - caso definido, filtra os produtos pela marca
	 * @param dataCadastro - caso definido, filtra os itens entre as datas: {0 => data inicial; 1 => data final ou tempo atual se nulo}
	 * @return ArrayList<Produto>
	 * @throws SQLException
	 */
	public static ArrayList<Produto> listaProdutos(
			boolean recentes,
			Short faixaEtaria,
			String marca,
			Calendar[] datas
			) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();

		String sql = "SELECT * FROM produtos";

		/* Algoritmo de construção da String do filtro. Gambiarra? talvez...
		 * se for gambi, garanto ser outro ní­vel. :)
		 * 
		 * Um índice é definido abaixo do anterior. */
		int faixaEtariaIndice = 0;
		int marcaIndice = 0;
		int datasIndice = 0;
		if (faixaEtaria != null || marca != null || datas != null) {
			/* Começamos de 0... */
			int proximo = 0;
			sql += " WHERE";
			/* (atributo) passado como parâmetro? então incrementa (proximo) e atribui ao seu índice respectivo. */
			if (faixaEtaria != null) {
				proximo++;
				faixaEtariaIndice = proximo;
				sql += " faixa_etaria_indicada >= ?";
			}
			if (marca != null) {
				if (proximo > 0) sql += " AND";
				proximo++;
				marcaIndice = proximo;
				sql += " marca=?";
			}
			if (datas != null) {
				if (proximo > 0) sql += " AND";
				proximo++;
				datasIndice = proximo;
				sql += " AND (data_cadastro >= ? AND data_cadastro < ?)";
			}
		}
		if (recentes)
			sql += " ORDER BY data_cadastro DESC";

		PreparedStatement stmt = conn
				.prepareStatement(sql);

		/* Se um índice é diferente de 0, é porque há um filtro pro seu elemento,
		 * então setamos o parâmetro para o Statement de acordo com o seu índice. */
		if (faixaEtariaIndice != 0)
			stmt.setShort(faixaEtariaIndice, faixaEtaria);
		if (marcaIndice != 0)
			stmt.setString(marcaIndice, marca);
		if (datasIndice != 0) {
			stmt.setDate(datasIndice+0, new java.sql.Date(datas[0].getTimeInMillis()));
			stmt.setDate(datasIndice+1, new java.sql.Date(
					datas[1] != null ?
							datas[1].getTimeInMillis()
							: Calendar.getInstance().getTimeInMillis()));
		}

		ArrayList<Produto> produtos = new ArrayList<Produto>();
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			produtos.add(resultSetParaProduto(rs));
		return produtos;
	}

	public static void registra(Produto produto) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO produtos(nome, marca, " +
						"faixa_etaria_indicada, altura, largura, profundidade, " +
						"peso, preco) VALUES(?, ?, ?, ?, " +
						"?, ?, ?, ?)");
		stmt.setString(1, produto.getNome());
		stmt.setString(2, produto.getMarca());
		stmt.setShort(3, produto.getFaixaEtaria());
		stmt.setDouble(4, produto.getDimensao(Dimensao.Enum.Altura));
		stmt.setDouble(5, produto.getDimensao(Dimensao.Enum.Largura));
		stmt.setDouble(6, produto.getDimensao(Dimensao.Enum.Profundidade));
		stmt.setDouble(7, produto.getPeso());
		stmt.setDouble(8, produto.getPreco());
		
		stmt.executeUpdate();
	}

	public static boolean salva(Produto produto) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("UPDATE produtos SET nome=?, marca=?, " +
						"faixa_etaria_indicada=?, altura=?, largura=?, " +
						"profundidade=?, peso=?, preco=? " +
						"WHERE codigo=?");
		stmt.setString(1, produto.getNome());
		stmt.setString(2, produto.getMarca());
		stmt.setShort(3, produto.getFaixaEtaria());
		stmt.setDouble(4, produto.getDimensao(Dimensao.Enum.Altura));
		stmt.setDouble(5, produto.getDimensao(Dimensao.Enum.Largura));
		stmt.setDouble(6, produto.getDimensao(Dimensao.Enum.Profundidade));
		stmt.setDouble(7, produto.getPeso());
		stmt.setDouble(8, produto.getPreco());
		stmt.setLong(9, produto.getCodigo());

		int row_count = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return row_count > 0;
	}

	public static boolean remove(Produto produto) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM produtos WHERE codigo=?");
		stmt.setLong(1, produto.getCodigo());

		int row_count = stmt.executeUpdate();

		stmt.close();
		conn.close();

		return row_count > 0;
	}

	private static Produto resultSetParaProduto(ResultSet rs) throws SQLException {
		Long codigo = rs.getLong("codigo");
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getDate("data_cadastro"));
		
		Produto produto = new Produto(codigo, cal);
		produto.setNome(rs.getString("nome"));
		produto.setMarca(rs.getString("marca"));
		try {
			produto.setFaixaEtaria(rs.getShort("faixa_etaria_indicada"));

			produto.setDimensao(rs.getDouble("altura"), Dimensao.Enum.Altura);
			produto.setDimensao(rs.getDouble("largura"), Dimensao.Enum.Largura);
			produto.setDimensao(rs.getDouble("profundidade"), Dimensao.Enum.Profundidade);

			produto.setPeso(rs.getDouble("peso"));
			produto.setPreco(rs.getDouble("preco"));

		} catch(Exception e) {
			e.printStackTrace();
		}

		return produto;
	}
}
