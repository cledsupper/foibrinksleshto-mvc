package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dac.ProdutoDAC;
import br.com.cledson.foibrinks.model.mercado.Dimensao;
import br.com.cledson.foibrinks.model.mercado.Produto;
import br.com.cledson.foibrinks.mvc.Constantes;

public class AtualizaProdutoAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Long codigo = Long.parseLong(req.getParameter("codigo"));

		String nome = req.getParameter("nome");
		String marca = req.getParameter("marca");
		String combo_faixa_etaria = req.getParameter("combo-faixa-etaria");
		String campo_faixa_etaria = req.getParameter("campo-faixa-etaria");
		String altura = req.getParameter("altura");
		String largura = req.getParameter("largura");
		String profundidade = req.getParameter("profundidade");
		String peso = req.getParameter("peso");
		String preco = req.getParameter("preco");

		try {
			Produto produto = Produto.procura(codigo);
			Produto produto_2 = ProdutoDAC.lePorNomeMarca(nome, marca);
			if (produto_2 != null)
				if (produto.getCodigo() != produto_2.getCodigo()) {
					res.getWriter().write("ERRO AO ATUALIZAR DADOS DO PRODUTO: produto com mesmo nome e marca já existe!");
					return;
				}
			produto.setNome(nome);
			produto.setMarca(marca);
			if (combo_faixa_etaria.equals(Constantes.COMBO_VALOR_OUTRO))
				produto.setFaixaEtaria(Short.parseShort(campo_faixa_etaria));
			else
				produto.setFaixaEtaria(Short.parseShort(combo_faixa_etaria));
			produto.setDimensao(Double.parseDouble(altura), Dimensao.Enum.Altura);
			produto.setDimensao(Double.parseDouble(largura), Dimensao.Enum.Largura);
			produto.setDimensao(Double.parseDouble(profundidade), Dimensao.Enum.Profundidade);
			produto.setPeso(Double.parseDouble(peso));
			produto.setPreco(Double.parseDouble(preco));
			produto.salva();
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao salvar dados do produto:\n"
					+ e.getMessage());
			return;
		}

		res.sendRedirect(Constantes.STRING_URI_DETALHA_PRODUTO +
				"?codigo=" + codigo + "&notifica-salvo=true");
	}

}
