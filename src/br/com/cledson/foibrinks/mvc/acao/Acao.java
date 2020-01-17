package br.com.cledson.foibrinks.mvc.acao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Interface de Acao.
 * 
 * @author Cledson Cavalcanti
 *
 */
public interface Acao {
	/** Tal como o Servlet possui um m�todo gen�rico service(), uma classe que implementa "Acao" possui um m�todo executa().
	 * 
	 * @param req			- veja a documenta��o de HttpServlet.
	 * @param res			- veja a documenta��o de HttpServlet.
	 * @throws Exception	- uma exce��o vagando por a�, mas vai que ela se manifesta, n�?!
	 */
	void executa(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
