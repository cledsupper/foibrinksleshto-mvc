package br.com.cledson.foibrinks.mvc.acao;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.model.ORIValorInvalidoException;
import br.com.cledson.foibrinks.model.pessoal.Cliente;
import br.com.cledson.foibrinks.model.pessoal.PessoaIncadastravelException;
import br.com.cledson.foibrinks.model.pessoal.PessoaValidador;
import br.com.cledson.foibrinks.mvc.Constantes;

public class CadastraClienteAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String nome = req.getParameter("nome");
		String data_nascimento = req.getParameter("data-nascimento");
		String combo_genero = req.getParameter("combo-genero");
		String campo_genero = req.getParameter("campo-genero");
		String cpf = req.getParameter("cpf");
		String estado_civil = req.getParameter("estado-civil");
		String rua = req.getParameter("rua");
		String bairro = req.getParameter("bairro");
		String cep = req.getParameter("cep");
		String cidade = req.getParameter("cidade");
		String estado = req.getParameter("estado");

		Cliente cliente = new Cliente();
		try {
			cliente.setNomeCompleto(nome);
			cliente.setDataNascimentoHTML(data_nascimento);
			cliente.setGenero(PessoaValidador.generoOutro(combo_genero) ?
					campo_genero : combo_genero);
			cliente.setCpf(cpf);
		} catch (ORIValorInvalidoException e) {
			res.getWriter().write("Erro com atributo do cliente:\n"
					+ e.getMessage());
			return;
		}

		cliente.setEstadoCivil(estado_civil);
		cliente.setRua(rua);
		cliente.setBairro(bairro);
		cliente.setCep(cep);
		cliente.setCidade(cidade);
		cliente.setEstado(estado);

		try {
			cliente.cadastra();
		} catch (PessoaIncadastravelException | SQLException e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao cadastrar cliente:\n"
					+ e.getMessage());
			return;
		}

		res.sendRedirect(Constantes.STRING_URI_LISTA_CLIENTES);
	}

}
