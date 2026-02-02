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
import tfg.alfp.dto.ObraDTO;

public class ObraApiClient {
	
	private static ObjectMapper mpp = new ObjectMapper();
	private final OkHttpClient client;
	private static final String URL = "http://localhost:8080/api/obra";
	
	public ObraApiClient() {
		this.client = ApiClientManager.getInstance().getClient();
	}
	
	public List<ObraDTO> portada(ObraDTO obra) throws IOException {
		String json = mpp.writeValueAsString(obra);
		
		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
		Request peticion = new Request.Builder().url(URL + "/portada").post(body).build();
		
		try(Response respuesta = client.newCall(peticion).execute()) {
			if(!respuesta.isSuccessful()) {
				throw new IOException("Error en la peticón: " + respuesta);
			}
			String jsonRespuesta = respuesta.body().string();
			return mpp.readValue(jsonRespuesta, new TypeReference<List<ObraDTO>>() {});
		}
	}

//	public ObraDTO datos(ObraDTO obra) throws IOException {
//		String json = mpp.writeValueAsString(obra);
//		
//		RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
//		Request peticion = new Request.Builder().url(URL + "/datos").post(body).build();
//		
//		try(Response respuesta = client.newCall(peticion).execute()) {
//			if(!respuesta.isSuccessful()) {
//				throw new IOException("Error en la petición: "+ respuesta);
//			}
//			String jsonRespuesta = respuesta.body().string();
//			return mpp.readValue(jsonRespuesta, ObraDTO.class);
//		}
//	}

}
