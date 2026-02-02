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
import tfg.alfp.dto.SeguimientoDTO;
import tfg.alfp.dto.UsuarioDTO;

public class SeguimientoApiClient {
	
	private static ObjectMapper mpp = new ObjectMapper();
	private final OkHttpClient client;
	private static final String URL = "http://localhost:8080/api/seguimiento";
	
	public SeguimientoApiClient() {
		this.client = ApiClientManager.getInstance().getClient();
	}
	
	public boolean agregar(SeguimientoDTO seguido) throws IOException {
		String json = mpp.writeValueAsString(seguido);
		
		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/agregar").post(body).build();
		try(Response respuesta = client.newCall(peticion).execute()) {
			if(!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
			return Boolean.parseBoolean(respuesta.body().string());
		}
	}
	
	public boolean eliminar(SeguimientoDTO seguido) throws IOException {
		String json = mpp.writeValueAsString(seguido);
		
		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/eliminar").post(body).build();
		try(Response respuesta = client.newCall(peticion).execute()) {
			if(!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
			return Boolean.parseBoolean(respuesta.body().string());
		}	
	}
	
	public List<ChatDTO> listar(UsuarioDTO usuario) throws IOException {
		String json = mpp.writeValueAsString(usuario);
		
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
