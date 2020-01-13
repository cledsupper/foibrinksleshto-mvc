package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.mercado.Dimensao;
import br.com.cledson.foibrinks.model.mercado.Produto;
import br.com.cledson.foibrinks.mvc.Constantes;

public class CadastraProdutoAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String nome = req.getParameter("nome");
		String marca = req.getParameter("marca");
		String combo_faixa_etaria = req.getParameter("combo-faixa-etaria");
		String campo_faixa_etaria = req.getParameter("campo-faixa-etaria");
		String altura = req.getParameter("altura");
		String largura = req.getParameter("largura");
		String profundidade = req.getParameter("profundidade");
		String peso = req.getParameter("peso");
		String preco = req.getParameter("preco");

		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setMarca(marca);

		short faixa_etaria = Short.parseShort(combo_faixa_etaria.equals(Constantes.COMBO_VALOR_OUTRO) ?
				campo_faixa_etaria : combo_faixa_etaria);
		try {
			produto.setFaixaEtaria(faixa_etaria);

			produto.setDimensao(Double.parseDouble(
					altura.isEmpty() ? "0" : altura), Dimensao.Enum.Altura);
			produto.setDimensao(Double.parseDouble(
					largura.isEmpty() ? "0" : largura), Dimensao.Enum.Largura);
			produto.setDimensao(Double.parseDouble(
					profundidade.isEmpty() ? "0" : profundidade), Dimensao.Enum.Profundidade);

			produto.setPeso(Double.parseDouble(
					peso.isEmpty() ? "0" : peso));
			produto.setPreco(Double.parseDouble(
					preco.isEmpty() ? "0" : preco));
		} catch (ORIValorInvalidoException e) {
			res.getWriter().write("Erro ao configurar os atributos do produto:\n"
					+ e.getMessage());
			return;
		}

		try {
			produto.cadastra();
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao cadastrar produto:\n"
					+ e.getLocalizedMessage());
			return;
		}
	
		res.sendRedirect(Constantes.STRING_URI_LISTA_PRODUTOS);
	}

}
