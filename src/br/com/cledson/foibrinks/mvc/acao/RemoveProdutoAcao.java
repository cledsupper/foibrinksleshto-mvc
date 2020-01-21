package br.com.cledson.foibrinks.mvc.acao;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dao.ProdutoDAO;
import br.com.cledson.foibrinks.model.ORIException;
import br.com.cledson.foibrinks.mvc.Constantes;

public class RemoveProdutoAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Long codigo = Long.parseLong(req.getParameter("codigo"));

		try {
			ProdutoDAO.le(codigo).remove();
		}
		catch (ORIException e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao remover produto:\n" +
					e.getLocalizedMessage());
			return;
		}
		catch (SQLException e) {
			e.printStackTrace();
			res.sendRedirect(
					Constantes.STRING_URI_DETALHA_PRODUTO + "?notifica-erro=true&codigo=" + codigo);
			return;
		}

		res.sendRedirect(Constantes.STRING_URI_LISTA_PRODUTOS + "?notifica-remocao=true");
	}

}
