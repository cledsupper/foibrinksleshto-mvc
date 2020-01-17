package br.com.cledson.foibrinks.model.pessoal;

import br.com.cledson.foibrinks.model.ORIException;
import br.com.cledson.foibrinks.model.ORIJaExisteException;

public class PessoaJaExisteException extends ORIJaExisteException {

	public PessoaJaExisteException(Pessoa pessoa, String erro) {
		super(pessoa, erro);
	}

	public PessoaJaExisteException(ORIException e) {
		super(e);
	}

}
