package br.com.cledson.foibrinks.model.pessoal;

import br.com.cledson.foibrinks.model.ORIException;
import br.com.cledson.foibrinks.model.ORIIncadastravelException;

public class PessoaIncadastravelException extends ORIIncadastravelException {
	public PessoaIncadastravelException(Pessoa pessoa) {
		super(pessoa, String.format(PessoaConstantes.STRING_ERRO_PESSOA_INCADASTRAVEL,
				pessoa.getClass().getSimpleName().toLowerCase()));
	}

	public PessoaIncadastravelException(ORIException e) {
		super(e);
	}
}
