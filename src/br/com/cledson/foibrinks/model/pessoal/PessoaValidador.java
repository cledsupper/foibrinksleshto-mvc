package br.com.cledson.foibrinks.model.pessoal;

import java.util.Calendar;

import br.com.cledson.foibrinks.model.ORIConstantes;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;

public class PessoaValidador {
	/* As constantes de tamanho mínimo devem estar em conformidade com as páginas JSP. */
	public static final int NOME_COMPLETO_MIN_LENGTH = 1;
	public static final int NOME_COMPLETO_MAX_LENGTH = 255;

	public static final int GENERO_MIN_LENGTH = 1;
	public static final int GENERO_MAX_LENGTH = 15;

	public static void validaNomeCompleto(Pessoa pessoa, String nomeCompleto) throws ORIValorInvalidoException {
		if (nomeCompleto != null)
			if (nomeCompleto.length() >= NOME_COMPLETO_MIN_LENGTH
					&& nomeCompleto.length() <= NOME_COMPLETO_MAX_LENGTH)
				return;

		throw new ORIValorInvalidoException(pessoa, String.format(
				ORIConstantes.STRING_ERRO_ORI_VALOR_INVALIDO, "nomeCompleto"));
	}

	public static void validaDataNascimento(Pessoa pessoa, Calendar dataNascimento) throws ORIValorInvalidoException {
		// TODO precisa melhorar, por exemplo: impedir pessoas com idade negativa.
		if (dataNascimento == null)
			throw new ORIValorInvalidoException(pessoa, String.format(
					ORIConstantes.STRING_ERRO_ORI_VALOR_INVALIDO, "dataNascimento"));
	}
	
	public static void validaGenero(Pessoa pessoa, String genero) throws ORIValorInvalidoException {
		if (genero != null)
			if (genero.length() >= GENERO_MIN_LENGTH
					&& genero.length() <= GENERO_MAX_LENGTH)
				return;
		throw new ORIValorInvalidoException(pessoa, String.format(
				ORIConstantes.STRING_ERRO_ORI_VALOR_INVALIDO, "genero"));
	}

	public static boolean generoOutro(String genero) {
		if (genero.equals(PessoaConstantes.STRING_GENERO_OUTRO))
			return true;
		if (genero.equals(PessoaConstantes.STRING_GENERO_MASCULINO))
			return false;
		if (genero.equals(PessoaConstantes.STRING_GENERO_FEMININO))
			return false;
		if (genero.equals(PessoaConstantes.STRING_GENERO_NAO_ESPECIFICAR))
			return false;
		return true;
	}
}
