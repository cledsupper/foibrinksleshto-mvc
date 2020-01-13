package br.com.cledson.foibrinks.model;

import java.text.ParseException;
import java.util.Calendar;

public class ORIUtilitarios {
	public static Calendar dataHTMLParaData(String data) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ORIConstantes.DATA_FORMATO_HTML.parse(data));
		return cal;
	}

	public static String dataParaStringBr(Calendar data) {
		if (data == null)
			return ORIConstantes.STRING_DATA_NULL;
		return ORIConstantes.DATA_FORMATO_BR.format(data.getTime());
	}

	public static Calendar stringBrParaData(String string) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ORIConstantes.DATA_FORMATO_BR.parse(string));
		return cal;
	}

	public static String dataHoraParaStringBr(Calendar data) {
		if (data == null)
			return ORIConstantes.STRING_DATA_NULL;
		return ORIConstantes.DATA_HORA_FORMATO_BR.format(data.getTime());
	}

	public static Calendar stringBrParaDataHora(String string) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ORIConstantes.DATA_HORA_FORMATO_BR.parse(string));
		return cal;
	}

	public static String dataParaStringHTML(Calendar data) {
		if (data == null)
			return ORIConstantes.STRING_DATA_NULL;
		return ORIConstantes.DATA_FORMATO_HTML.format(data.getTime());
	}
}
