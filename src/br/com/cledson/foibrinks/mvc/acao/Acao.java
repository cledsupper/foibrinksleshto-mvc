package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Interface de Acao.
 * 
 * @author Cledson Cavalcanti
 *
 */
public interface Acao {
	/** Tal como o Servlet possui um método genérico service(), uma classe que implementa "Acao" possui um método executa().
	 * 
	 * @param req			- veja a documentação de HttpServlet.
	 * @param res			- veja a documentação de HttpServlet.
	 * @throws Exception	- uma exceção vagando por aí, mas vai que ela se manifesta, né?!
	 */
	void executa(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
