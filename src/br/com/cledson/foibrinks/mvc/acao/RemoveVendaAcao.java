package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dac.VendaDAC;
import br.com.cledson.foibrinks.model.mercado.Venda;
import br.com.cledson.foibrinks.mvc.Constantes;

public class RemoveVendaAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		long codigo = Long.parseLong(req.getParameter("codigo"));
		
		Venda venda = null;
		try {
			venda = VendaDAC.le(codigo);
			venda.remove();
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao remover venda:\n"
					+ e.getLocalizedMessage());
		}

		res.sendRedirect(Constantes.STRING_URI_LISTA_VENDAS + "?notifica-remocao=true");
	}

}
