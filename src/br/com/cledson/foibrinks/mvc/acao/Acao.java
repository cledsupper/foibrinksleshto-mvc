package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Acao {
	void executa(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
