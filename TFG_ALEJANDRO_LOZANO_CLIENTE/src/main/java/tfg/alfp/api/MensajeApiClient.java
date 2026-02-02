package tfg.alfp.api;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tfg.alfp.api.session.ApiClientManager;
import tfg.alfp.dto.MensajeDTO;

public class MensajeApiClient {

	private static ObjectMapper mpp = new ObjectMapper();
	private final OkHttpClient client;
	private static final String URL = "http://localhost:8080/api/mensaje";

	public MensajeApiClient() {
		this.client = ApiClientManager.getInstance().getClient();
	}

	public void guardar(MensajeDTO mensaje) throws IOException {
		String json = mpp.writeValueAsString(mensaje);

		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/guardar").post(body).build();
		try (Response respuesta = client.newCall(peticion).execute()) {
			if (!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
		}
	}

}
