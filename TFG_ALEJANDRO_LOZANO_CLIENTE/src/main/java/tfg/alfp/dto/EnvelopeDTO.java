package tfg.alfp.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class EnvelopeDTO {
	
	// CHAT/COMANDO
	private String tipo;
	// acciónRealizar
	private String accion;
	// Objeto
	private Object payload;
	
	public EnvelopeDTO() {
		
	}

	public EnvelopeDTO(String tipo, String accion, JsonNode payload) {
		this.tipo = tipo;
		this.accion = accion;
		this.payload = payload;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
