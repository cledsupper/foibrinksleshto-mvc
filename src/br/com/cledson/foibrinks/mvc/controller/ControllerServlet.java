package br.com.cledson.foibrinks.mvc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cledson.foibrinks.mvc.acao.Acao;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String acaoNome = req.getParameter("acao");
		String nomeDaClasse = "br.com.cledson.foibrinks.mvc.acao." + acaoNome;

		try {
			Class classeAcao = Class.forName(nomeDaClasse);
			Acao acao = (Acao) classeAcao.newInstance();
			acao.executa(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Erro ao realizar a ação " + acaoNome + "\n");
		}
	}

}
