package br.com.cledson.foibrinks.mvc.acao;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dac.ClienteDAC;
import br.com.cledson.foibrinks.model.pessoal.PessoaNaoEncontradaException;
import br.com.cledson.foibrinks.mvc.Constantes;

public class RemoveClienteAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Long codigo = Long.parseLong(req.getParameter("codigo"));

		try {
			ClienteDAC.le(codigo).remove();
		} catch (PessoaNaoEncontradaException | SQLException e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao remover cliente:\n"
					+ e.getLocalizedMessage());
			return;
		}
		res.sendRedirect(Constantes.STRING_URI_LISTA_CLIENTES + "?notifica-remocao=true");
	}

}
