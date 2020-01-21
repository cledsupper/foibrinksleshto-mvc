package br.com.cledson.foibrinks.mvc.acao;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dao.DependenteDAO;
import br.com.cledson.foibrinks.model.pessoal.PessoaNaoEncontradaException;
import br.com.cledson.foibrinks.mvc.Constantes;

public class RemoveDependenteAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Long codigo_cliente = Long.parseLong(req.getParameter("codigo-cliente"));
		Long codigo = Long.parseLong(req.getParameter("codigo"));

		try {
			DependenteDAO.le(codigo).remove();
		} catch (PessoaNaoEncontradaException | SQLException e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao remover dependente:\n"
					+ e.getLocalizedMessage());
			return;
		}
		res.sendRedirect(Constantes.STRING_URI_LISTA_DEPENDENTES + "?notifica-remocao=true&codigo-cliente=" + codigo_cliente);
	}

}
