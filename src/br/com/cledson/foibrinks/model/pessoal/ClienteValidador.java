package br.com.cledson.foibrinks.model.pessoal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.cledson.foibrinks.model.ORIConstantes;
import br.com.cledson.foibrinks.model.ORIValorInvalidoException;

public class ClienteValidador {
	public static final int STRING_CPF_FORMATADO_LENGTH = 14;
	public static final int STRING_CPF_OK_LENGTH = 11;

	private static final Pattern PATTERN_CPF_FORMATADO = Pattern.compile("^(\\d{3})\\.(\\d{3})\\.(\\d{3})-(\\d{2})$");
	private static final Pattern PATTERN_CPF_OK = Pattern.compile("^(\\d{3})(\\d{3})(\\d{3})(\\d{2})$");

	public static String paraCpfFormatado(String cpf) {
		if (cpf.length() == STRING_CPF_FORMATADO_LENGTH) {
			if (PATTERN_CPF_FORMATADO.matcher(cpf).find())
				return cpf;
			return null;
		}

		Matcher match = PATTERN_CPF_OK.matcher(cpf);
		if (!match.find())
			return null;

		String cpfFormatado = String.format("%s.%s.%s-%s",
				match.group(1), match.group(2), match.group(3), match.group(4));
		return cpfFormatado;
	}

	public static String paraCpfOk(String cpf) {
		if (cpf.length() == STRING_CPF_OK_LENGTH) {
			if (PATTERN_CPF_OK.matcher(cpf).find())
				return cpf;
			return null;
		}

		Matcher match = PATTERN_CPF_FORMATADO.matcher(cpf);
		if (!match.find())
			return null;

		return match.group(1) + match.group(2) + match.group(3) + match.group(4);
	}

	public static String paraCpfOk(Cliente cliente, String cpf) throws ORIValorInvalidoException {
		String ok = paraCpfOk(cpf);
		if (ok == null)
			throw new ORIValorInvalidoException(cliente, String.format(
					ORIConstantes.STRING_ERRO_ORI_VALOR_INVALIDO, "cpf"));

		return ok;
	}
}
