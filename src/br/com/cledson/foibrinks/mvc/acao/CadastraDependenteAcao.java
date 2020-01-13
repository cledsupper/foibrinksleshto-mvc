package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dac.ClienteDAC;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.pessoal.Cliente;
import br.com.cledson.foibrinks.model.pessoal.Dependente;
import br.com.cledson.foibrinks.model.pessoal.PessoaValidador;
import br.com.cledson.foibrinks.mvc.Constantes;

public class CadastraDependenteAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String codigo_cliente_string = req.getParameter("codigo-cliente");
		String nome = req.getParameter("nome");
		String data_nascimento = req.getParameter("data-nascimento");
		String combo_genero = req.getParameter("combo-genero");
		String campo_genero = req.getParameter("campo-genero");

		Dependente dependente = new Dependente();
		try {
			dependente.setNomeCompleto(nome);
			dependente.setDataNascimentoHTML(data_nascimento);
			dependente.setGenero(PessoaValidador.generoOutro(combo_genero) ?
					campo_genero : combo_genero);
		} catch (ORIValorInvalidoException e) {
			res.getWriter().write("Erro ao configurar os atributos do dependente:\n"
					+ e.getMessage());
			return;
		}
		
		Long codigo_cliente = Long.parseLong(codigo_cliente_string);
		Cliente cliente = null;
		try {
			cliente = ClienteDAC.le(codigo_cliente);
			cliente.adicionaDependente(dependente);
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao cadastrar dependente:\n"
					+ e.getLocalizedMessage());
			return;
		}

		res.sendRedirect(Constantes.STRING_URI_LISTA_DEPENDENTES + "?codigo-cliente=" + codigo_cliente);
	}

}
