package br.com.cledson.foibrinks.model.pessoal;

import br.com.cledson.foibrinks.model.ORINaoEncontradoException;

public class PessoaNaoEncontradaException extends ORINaoEncontradoException {

	public PessoaNaoEncontradaException(Pessoa pessoa) {
		super(pessoa, String.format(PessoaConstantes.STRING_ERRO_PESSOA_NAO_ENCONTRADA,
				pessoa.getClass().getSimpleName().toLowerCase()));
	}

	public PessoaNaoEncontradaException(Pessoa pessoa, String erro) {
		super(pessoa, erro);
	}
}
