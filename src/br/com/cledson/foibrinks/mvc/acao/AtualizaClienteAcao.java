package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.bd.dac.ClienteDAC;
import br.com.cledson.foibrinks.model.ORIUtilitarios;
import br.com.cledson.foibrinks.model.pessoal.Cliente;
import br.com.cledson.foibrinks.mvc.Constantes;

public class AtualizaClienteAcao implements Acao {

	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		Long codigo = Long.parseLong(req.getParameter("codigo"));

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

		try {
			Cliente cliente = ClienteDAC.le(codigo);

			/* Verificação de duplicidade */
			Cliente cliente_2 = ClienteDAC.lePorCpf(cpf);
			if (cliente_2 != null)
				if (cliente.getCodigo() != cliente_2.getCodigo()) {
					res.getWriter().write("Erro ao atualizar dados do cliente: um cliente com o mesmo CPF já existe!");
					return;
				}
			cliente_2 = ClienteDAC.lePorNomeData(nome, ORIUtilitarios.dataHTMLParaData(data_nascimento));
			if (cliente_2 != null)
				if (cliente.getCodigo() != cliente_2.getCodigo()) {
					res.getWriter().write("Erro ao atualizar dados do cliente: um cliente com o mesmo nome e data de nascimento já existe!");
					return;
				}

			cliente.setNomeCompleto(nome);
			cliente.setDataNascimentoHTML(data_nascimento);
			cliente.setGenero(combo_genero.equals(Constantes.COMBO_VALOR_OUTRO) ?
					campo_genero : combo_genero);
			cliente.setCpf(cpf);
			cliente.setEstadoCivil(estado_civil);
			cliente.setRua(rua);
			cliente.setBairro(bairro);
			cliente.setCep(cep);
			cliente.setEstado(estado);
			cliente.setCidade(cidade);
			cliente.salva();
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("Erro ao atualizar cliente:\n"
					+ e.getLocalizedMessage());
			return;
		}

		res.sendRedirect(Constantes.STRING_URI_DETALHA_CLIENTE +
				"?codigo=" + codigo + "&notifica-salvo=true");
	}

}
