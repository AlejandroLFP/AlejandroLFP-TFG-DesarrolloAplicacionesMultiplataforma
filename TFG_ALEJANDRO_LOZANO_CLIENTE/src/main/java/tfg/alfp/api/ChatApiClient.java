package tfg.alfp.api;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tfg.alfp.api.session.ApiClientManager;
import tfg.alfp.dto.ChatDTO;
import tfg.alfp.dto.MensajeDTO;
import tfg.alfp.dto.ObraDTO;

public class ChatApiClient {
	
	private static ObjectMapper mpp = new ObjectMapper();
	private final OkHttpClient client;
	private static final String URL = "http://localhost:8080/api/chat";
	
	public ChatApiClient() {
		this.client = ApiClientManager.getInstance().getClient();
	}
	
	public boolean crear(ChatDTO chat) throws IOException {
		String json = mpp.writeValueAsString(chat);
		
		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/crear").post(body).build();
		try(Response respuesta = client.newCall(peticion).execute()) {
			if(!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
			return Boolean.parseBoolean(respuesta.body().string());
		}
	}
	
	public List<MensajeDTO> buscarMensajes(ChatDTO chat) throws IOException {
		String json = mpp.writeValueAsString(chat);

		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/buscar").post(body).build();
		try (Response respuesta = client.newCall(peticion).execute()) {
			if (!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
			String jsonRespuesta = respuesta.body().string();
			return mpp.readValue(jsonRespuesta, new TypeReference<List<MensajeDTO>>() {
			});
		}
	}
	
	public List<ChatDTO> listar(ObraDTO obra) throws IOException {
		String json = mpp.writeValueAsString(obra);
		
		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/listar").post(body).build();
		try(Response respuesta = client.newCall(peticion).execute()) {
			if(!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
			String jsonRespuesta = respuesta.body().string();
			return mpp.readValue(jsonRespuesta, new TypeReference<List<ChatDTO>>() {});
		}
	}
}
