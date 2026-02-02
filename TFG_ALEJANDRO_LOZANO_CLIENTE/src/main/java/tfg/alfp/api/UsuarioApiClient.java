package tfg.alfp.api;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tfg.alfp.api.session.ApiClientManager;
import tfg.alfp.dto.UsuarioDTO;

public class UsuarioApiClient {

	private static ObjectMapper mpp = new ObjectMapper();
	private final OkHttpClient client;
	private static final String URL = "http://localhost:8080/api/usuario";
	
	public UsuarioApiClient() {
		this.client = ApiClientManager.getInstance().getClient();
	}

//	public boolean login(UsuarioDTO usuario) throws IOException {
//		String json = mpp.writeValueAsString(usuario);
//		
//		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
//		Request peticion = new Request.Builder().url(URL + "/login").post(body).build();
//		try(Response respuesta = client.newCall(peticion).execute()) {
//			if(!respuesta.isSuccessful()) {
//				throw new IOException("Error inseperado: " + respuesta);
//			}
//			return Boolean.parseBoolean(respuesta.body().string());
//		}
//	}

	public boolean crearCuenta(UsuarioDTO usuario) throws IOException {
		String json = mpp.writeValueAsString(usuario);
		
		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/crear").post(body).build();
		try(Response respuesta = client.newCall(peticion).execute()) {
			if(!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
			return Boolean.parseBoolean(respuesta.body().string());
		}
	}

	public UsuarioDTO obtenerCuenta(UsuarioDTO userName) throws IOException {
		String json = mpp.writeValueAsString(userName);
		
		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/obtener").post(body).build();
		try(Response respuesta = client.newCall(peticion).execute()) {
			if(!respuesta.isSuccessful()) {
				throw new IOException("Error: " + respuesta);
			}
			String jsonRespuesta = respuesta.body().string();
			return mpp.readValue(jsonRespuesta, UsuarioDTO.class);
		}
	}

}
