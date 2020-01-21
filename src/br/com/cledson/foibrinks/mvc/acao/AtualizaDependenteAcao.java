package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dao.DependenteDAO;
import br.com.cledson.foibrinks.model.pessoal.Dependente;
import br.com.cledson.foibrinks.model.pessoal.PessoaValidador;
import br.com.cledson.foibrinks.mvc.Constantes;

public class AtualizaDependenteAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String codigo_cliente_string = req.getParameter("codigo-cliente");
		String codigo_string = req.getParameter("codigo");
		Long codigo = Long.parseLong(codigo_string);

		String nomeCompleto = req.getParameter("nome");
		String dataNascimentoHTML = req.getParameter("data-nascimento");
		String combo_genero = req.getParameter("combo-genero");
		String campo_genero = req.getParameter("campo-genero");

		Dependente dep;
		try {
			dep = DependenteDAO.le(codigo);
			dep.setNomeCompleto(nomeCompleto);
			dep.setDataNascimentoHTML(dataNascimentoHTML);
			dep.setGenero(PessoaValidador.generoOutro(combo_genero) ?
					campo_genero : combo_genero);
		} catch (Exception e) {
			res.getWriter().write("Erro ao configurar os atributos do dependente:\n"
					+ e.getMessage());
			return;
		}
		
		try {
			dep.salva();
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao salvar dependente:\n"
					+ e.getMessage());
			return;
		}

		res.sendRedirect(Constantes.STRING_URI_DETALHA_DEPENDENTE
				+ "?notifica-salvo=true"
				+ "&codigo-cliente=" + codigo_cliente_string
				+ "&codigo=" + codigo_string);
	}
}
