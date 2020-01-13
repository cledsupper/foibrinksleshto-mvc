package br.com.cledson.foibrinks.model.pagamento;

public abstract class FormaPagamentoRestricoes {
	public static final long PRAZO_LEGAL_DIAS = 7;

	public static int getDias(long tempo) {
		final long PARA_DIAS = 86400000; // 24 h * 60 min * 60 s * 1000 ms
		int dias = (int)((System.currentTimeMillis() - tempo)/PARA_DIAS);
		return dias;
	}

	public static boolean dentroDoPrazoLegal(long time) {
		return getDias(time) < PRAZO_LEGAL_DIAS;
	}
}
