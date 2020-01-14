package br.com.cledson.foibrinks.bd.dac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.ConnectionFactory;
import br.com.cledson.foibrinks.model.mercado.Produto;
import br.com.cledson.foibrinks.model.mercado.ProdutoAdicionador;
import br.com.cledson.foibrinks.model.mercado.Venda;
import br.com.cledson.foibrinks.model.pagamento.Cartao;
import br.com.cledson.foibrinks.model.pagamento.Dinheiro;
import br.com.cledson.foibrinks.model.pagamento.FormaPagamentoValorInvalidoException;
import br.com.cledson.foibrinks.model.pessoal.Cliente;

public class VendaDAC {
	public static Venda le(long codigo) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM vendas WHERE codigo=?");
		stmt.setLong(1, codigo);
		
		ResultSet rs = stmt.executeQuery();

		Venda venda = null;
		if (rs.next())
			venda = resultSetParaVenda(rs);

		rs.close();
		stmt.close();
		conn.close();
		return venda;
	}

	public static Venda leUltima() throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM vendas ORDER BY data_venda DESC LIMIT 1");
		
		ResultSet rs = stmt.executeQuery();

		Venda venda = null;
		if (rs.next())
			venda = resultSetParaVenda(rs);

		rs.close();
		stmt.close();
		conn.close();
		return venda;
	}

	public static ArrayList<Venda> listaVendas() throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM vendas ORDER BY data_venda DESC");
		
		ResultSet rs = stmt.executeQuery();

		ArrayList<Venda> vendas = new ArrayList<Venda>();
		while (rs.next())
			vendas.add(resultSetParaVenda(rs));

		rs.close();
		stmt.close();
		conn.close();
		return vendas;
	}

	public static Venda[] listaPorCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<ProdutoAdicionador> leCarrinho(Venda venda) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM adicoes_produtos WHERE codigo_venda=?");
		stmt.setLong(1, venda.getCodigo());
		
		ResultSet rs = stmt.executeQuery();

		ArrayList<ProdutoAdicionador> carrinho = new ArrayList<ProdutoAdicionador>();
		while (rs.next())
			carrinho.add(resultSetParaProdutoAdicionador(venda, rs));

		rs.close();
		stmt.close();
		conn.close();
		return carrinho;
	}

	public static double geraCustoTotal(Venda venda) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("CALL gera_custo_total(" + venda.getCodigo() + ")");
		ResultSet rs = stmt.executeQuery();
		double custoTotal = 0;
		if (rs.next())
			custoTotal = rs.getDouble("custo_total");
		rs.close();
		stmt.close();
		conn.close();
		return custoTotal;
	}

	public static void registra(Venda venda) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		String sql = "INSERT INTO vendas(codigo_cliente, ";
		PreparedStatement stmt = null;
		if (venda.getPagamento().getForma() == 'D') {
			sql += "forma_pagamento, valor_pago) VALUES(?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, venda.getCliente().getCodigo());
			stmt.setString(2, "D");
			stmt.setDouble(3, venda.getPagamento().getValorPago());
		}
		else {
			sql += "forma_pagamento, numero_cartao, valor_pago) VALUES(?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, venda.getCliente().getCodigo());
			stmt.setString(2, "C");
			stmt.setLong(3, ((Cartao)venda.getPagamento()).getNumeroCartao());
			stmt.setDouble(4, venda.getPagamento().getValorPago());
		}

		stmt.executeUpdate();
		stmt.close();

		ArrayList<ProdutoAdicionador> carrinho = venda.getCarrinho();
		venda = leUltima();
		for (int i=0; i < carrinho.size(); i++) {
			sql = "INSERT INTO adicoes_produtos(codigo_venda, codigo_produto, qtd_produtos, valor_unitario) VALUES(?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			ProdutoAdicionador adicao = carrinho.get(i);
			stmt.setLong(1, venda.getCodigo());
			stmt.setLong(2, adicao.getProduto().getCodigo());
			stmt.setInt(3, adicao.getQtdProdutos());
			stmt.setDouble(4, adicao.getValorUnitario());
			stmt.executeUpdate();
			stmt.close();
		}

		stmt.close();
		conn.close();
	}

	public static boolean remove(Venda venda) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM vendas WHERE codigo=?");
		stmt.setLong(1, venda.getCodigo());
		int row_count = stmt.executeUpdate();
		
		stmt.close();
		conn.close();
		return row_count > 0;
	}

	private static ProdutoAdicionador resultSetParaProdutoAdicionador(
			Venda venda, ResultSet rs) throws SQLException {
		long codigo_produto = rs.getLong("codigo_produto");
		Produto produto = ProdutoDAC.le(codigo_produto);

		ProdutoAdicionador adicao = new ProdutoAdicionador(venda, produto);
		adicao.setQtdProdutos(rs.getInt("qtd_produtos"));
		adicao.setValorUnitario(rs.getDouble("valor_unitario"));
		return adicao;
	}

	private static Venda resultSetParaVenda(ResultSet rs) throws SQLException {
		long codigo_cliente = rs.getLong("codigo_cliente");
		long codigo = rs.getLong("codigo");
		Cliente cliente = ClienteDAC.le(codigo_cliente);
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getDate("data_venda"));

		Venda venda = new Venda(codigo, cliente, cal);
		String forma = rs.getString("forma_pagamento");
		double valor = rs.getDouble("valor_pago");
		if (forma.charAt(0) == Dinheiro.FORMA) {
			Dinheiro pagamento = new Dinheiro(venda);
			try {
				venda.setPagamento(pagamento);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			long numero_cartao = rs.getLong("numero_cartao");
			try {
				Cartao pagamento = new Cartao(venda, numero_cartao);
				venda.setPagamento(pagamento);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			venda.getPagamento().setValorPago(valor);
		} catch (FormaPagamentoValorInvalidoException e) {
			e.printStackTrace();
		}
		return venda;
	}
}
