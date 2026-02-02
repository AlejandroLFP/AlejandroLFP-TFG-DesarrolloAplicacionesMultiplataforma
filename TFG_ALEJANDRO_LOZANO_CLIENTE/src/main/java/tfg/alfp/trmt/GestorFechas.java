package tfg.alfp.trmt;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class GestorFechas {

	private static final String FORMATO_FECHA = "yyyy-MM-dd";

	public static String convertirString(Date fecha) {
		SimpleDateFormat df = new SimpleDateFormat(FORMATO_FECHA);
		return df.format(fecha);
	}

	public static Date convertirDate(String fecha) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(FORMATO_FECHA);
			java.util.Date utilDate = df.parse(fecha);
			java.sql.Date fechaSolu = new java.sql.Date(utilDate.getTime());
			return fechaSolu;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date convertirLocalDate(LocalDate fecha) {
		return Date.valueOf(fecha);
	}
}
