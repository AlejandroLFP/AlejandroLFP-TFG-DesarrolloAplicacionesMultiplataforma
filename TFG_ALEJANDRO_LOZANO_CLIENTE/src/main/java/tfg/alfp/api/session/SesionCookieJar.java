package tfg.alfp.api.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SesionCookieJar implements CookieJar {
	
	// Almacen para Cookies (Host/cookies asociadas).
	private final Map<String, List<Cookie>> cookieStore = new HashMap<>();
	
	// Guardar Cookie.
	@Override
	public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		cookieStore.put(url.host(), cookies);
		for(Cookie c:cookies) {
			if("JSESSIONID".equals(c.name())) {
//				SesionClient.setSessId(c.value());
				System.out.println("Cookie guardada");
			}
		}
	}
	
	// Buscar Cookie para peticiones.
	@Override
	public List<Cookie> loadForRequest(HttpUrl url){
		return cookieStore.getOrDefault(url.host(), Collections.emptyList());
	}

}
