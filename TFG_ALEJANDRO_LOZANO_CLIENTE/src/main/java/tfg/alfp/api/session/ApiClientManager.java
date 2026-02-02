package tfg.alfp.api.session;

import okhttp3.OkHttpClient;

public class ApiClientManager {

	// Clase Singleton, solo una sesión.
	private static ApiClientManager instancia;
	private final OkHttpClient cliente;
	
	private ApiClientManager() {
		cliente = new OkHttpClient.Builder().cookieJar(new SesionCookieJar()).build();
	}
	
	public static synchronized ApiClientManager getInstance() {
		if(instancia == null) {
			instancia = new ApiClientManager();
		}
		return instancia;
	}
	
	public OkHttpClient getClient() {
		return cliente;
	}
	
}
