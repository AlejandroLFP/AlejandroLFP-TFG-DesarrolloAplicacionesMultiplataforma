package tfg.alfp.util;

import java.io.FileReader;
import java.util.Properties;

public class GestorFicheroConfiguracion {
	
	public static String ruta = "config/chat.properties";
	
	public static String getRuta(String val) {
		Properties p = new Properties();
		String devuelve = "";
		try {
			FileReader fr = new FileReader(ruta);
			p.load(fr);
			devuelve = p.getProperty(val, "No existe");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devuelve;
	}

}
