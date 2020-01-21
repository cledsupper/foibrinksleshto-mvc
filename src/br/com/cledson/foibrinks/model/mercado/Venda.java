package br.com.cledson.foibrinks.model.mercado;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.cledson.foibrinks.bd.dao.VendaDAO;
import br.com.cledson.foibrinks.model.ORIConstantes;
import br.com.cledson.foibrinks.model.ORI;
import br.com.cledson.foibrinks.model.ORIException;
import br.com.cledson.foibrinks.model.ORIIncadastravelException;
import br.com.cledson.foibrinks.model.ORISomenteLeituraException;
import br.com.cledson.foibrinks.model.ORIUtilitarios;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.pagamento.FormaPagamento;
import br.com.cledson.foibrinks.model.pessoal.Cliente;

/** Classe para cadastros de venda.
 * 
 * @author Cledson Cavalcanti
 *
 */
public class Venda extends ORI {
	private final Calendar DATA_VENDA;
	private final Cliente CLIENTE;

	private ArrayList<ProdutoAdicionador> carrinho;
	private FormaPagamento pagamento;

	/** Construtor SOMENTE para cadastrar vendas.
	 * 
	 * @param cliente - para quem a venda está sendo feita
	 */
	public Venda(Cliente cliente) {
		super();
		DATA_VENDA = null;
		CLIENTE = cliente;
	}

	/** Construtor SOMENTE para ler dados de venda.
	 * 
	 * @param cliente - para quem a venda estÃ¡ sendo feita
	 */
	public Venda(long codigo, Cliente cliente, Calendar data) {
		super(codigo);
		CLIENTE = cliente;
		DATA_VENDA = data;
	}
	
	/** Interface para procuraPorCliente().
	 * 
	 * @param cliente - objeto com dados do cliente.
	 * @return procuraPorCliente(cliente.getCodigo());
	 */
	public static Venda[] procuraPorCliente(Cliente cliente) {
		return VendaDAO.listaPorCliente(cliente);
	}

	/** Registra os dados da venda, ProdutoAdicionador e FormaPagamento no banco de dados.
	 * 
	 * @throws ORIException - veja a documentação associada
	 * @throws ORIIncadastravelException - veja a documentação associada
	 * @throws SQLException 
	 */
	@Override
	public void cadastra() throws ORIIncadastravelException, ORIException, SQLException {
		super.cadastra();
		VendaDAO.registra(this);
	}

	/** Salvaria os dados de pagamento, mas isso não é permitido.
	 * 
	 * @throws ORISomenteLeituraException
	 */
	@Override
	public void salva() throws ORISomenteLeituraException {
		throw new ORISomenteLeituraException(this);
	}

	/** Remove todos os dados cadastrados com a venda.
	 * 
	 * @throws ORISomenteLeituraException
	 */
	@Override
	public void remove() throws ORIException, SQLException {
		VendaDAO.remove(this);
	}

	@Override
	protected String getORIDataString() {
		return this.getClass().getName() + " {"
				+ "\n  CODIGO: " + getCodigo() + ","
				+ "\n  DATA_VENDA: " + getDataVendaStringBr() + ","
				+ "\n  CLIENTE->CODIGO: " + CLIENTE.getCodigo() + ","
				+ "\n  CLIENTE->nomeCompleto: " + CLIENTE.getNomeCompleto()
				+ (pagamento != null ?
						(",\n  pagamento->valorPago: " + pagamento.getValorPago()) : "")
				+ "\n}";
	}

	public void adicionaProduto(Produto produto, int qtd, double valor) throws SQLException, ORISomenteLeituraException {
		if (cadastravel() == false)
			throw new ORISomenteLeituraException(this);
		if (carrinho == null)
			carrinho = new ArrayList<ProdutoAdicionador>();

		ProdutoAdicionador adicao = new ProdutoAdicionador(this, produto);
		adicao.setQtdProdutos(qtd);
		adicao.setValorUnitario(valor);

		carrinho.add(adicao);
	}

	public ArrayList<ProdutoAdicionador> getCarrinho() throws SQLException {
		if (carrinho == null) {
			if (cadastravel())
				return null;
			else
				carrinho = VendaDAO.leCarrinho(this);
		}
		return carrinho;
	}

	public Cliente getCliente() {
		return CLIENTE;
	}

	/** Gera o custo total dos produtos adicionados no carrinho.
	 * 
	 * @return custo total
	 * @throws SQLException 
	 */
	public double geraCustoTotal() throws SQLException {
		double custo = 0;
		if (cadastravel()) {
			if (carrinho == null)
				return 0;

			for (int i=0; i < carrinho.size(); i++) {
				double valorUnitario = carrinho.get(i).getValorUnitario();
				double qtd = carrinho.get(i).getQtdProdutos();
				custo += valorUnitario*qtd;
			}
		}
		else
			custo = VendaDAO.geraCustoTotal(this);
		return custo;
	}

	public FormaPagamento getPagamento() {
		return pagamento;
	}

	public Calendar getDataVenda() {
		return DATA_VENDA;
	}

	public String getDataVendaStringBr() {
		return ORIUtilitarios.dataHoraParaStringBr(DATA_VENDA);
	}

	public void setCarrinho(ArrayList<ProdutoAdicionador> carrinho) throws ORISomenteLeituraException {
		if (carrinho != null)
			throw new ORISomenteLeituraException(this);
		this.carrinho = carrinho;
	}

	public void setPagamento(FormaPagamento pagamento) throws ORISomenteLeituraException {
		if (this.pagamento != null)
			if (!cadastravel())
				throw new ORISomenteLeituraException(this,
						"Tentando alterar a forma de pagamento quando pagamento já foi processado.");

		this.pagamento = pagamento;
	}
}
