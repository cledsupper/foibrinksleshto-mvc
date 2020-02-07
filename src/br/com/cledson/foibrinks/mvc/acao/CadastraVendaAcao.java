package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dao.ClienteDAO;
import br.com.cledson.foibrinks.model.mercado.Produto;
import br.com.cledson.foibrinks.model.mercado.ProdutoValidador;
import br.com.cledson.foibrinks.model.mercado.Venda;
import br.com.cledson.foibrinks.model.pagamento.Cartao;
import br.com.cledson.foibrinks.model.pagamento.Dinheiro;
import br.com.cledson.foibrinks.model.pessoal.Cliente;
import br.com.cledson.foibrinks.mvc.Constantes;

public class CadastraVendaAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String[] produtos_codigos = req.getParameterValues("produto-codigo");
		String[] produtos_qtds = req.getParameterValues("produto-qtd");
		boolean incluiFreteParaLua =
				req.getParameter("inclui-frete-para-lua") != null;
		String cliente_radio = req.getParameter("cliente-radio");
		String forma_radio = req.getParameter("forma-radio");
		String numero_cartao = req.getParameter("numero-cartao");

		Cliente cliente = null;
		try {
			cliente = ClienteDAO.le(Long.parseLong(cliente_radio));
			Venda venda = new Venda(cliente);

			for (int i=0; i < produtos_codigos.length; i++) {
				Produto produto = Produto.procura(Long.parseLong(produtos_codigos[i]));
				int qtd = Integer.parseInt(produtos_qtds[i]);
				if (incluiFreteParaLua) {
					venda.adicionaProduto(
							produto,
							qtd,
							produto.getPreco() + produto.getFreteParaLua());
				}
				else {
					if (ProdutoValidador.produtoNovo(produto))
						venda.adicionaProduto(produto,
								qtd,
								produto.getPrecoComDescontoParaRecentes());
					else
						venda.adicionaProduto(produto,
								qtd,
								produto.getPreco());
				}
			}
			if (forma_radio.charAt(0) == Dinheiro.FORMA) {
				Dinheiro din = new Dinheiro(venda);
				din.setValorPago(venda.geraCustoTotal());
				venda.setPagamento(din);
			} else {
				Cartao car = new Cartao(venda, Long.parseLong(numero_cartao));
				car.setValorPago(venda.geraCustoTotal());
				venda.setPagamento(car);
			}

			venda.cadastra();
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao registrar venda:\n"
					+ e.getLocalizedMessage());
			return;
		}

		res.sendRedirect(Constantes.STRING_URI_LISTA_VENDAS);
	}

}
