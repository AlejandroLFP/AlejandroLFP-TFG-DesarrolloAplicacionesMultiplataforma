package tfg.alfp.dto;

import java.util.Date;

public class MensajeDTO {
	
	private int id;
	private String contenido;
	private Date fecha;
	
	private ChatDTO chat;
	private UsuarioDTO usuario;
	
	public MensajeDTO() {
		
	}

	public MensajeDTO(String contenido, Date fecha, ChatDTO chat, UsuarioDTO usuario) {
		this.contenido = contenido;
		this.fecha = fecha;
		this.chat = chat;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public ChatDTO getChat() {
		return chat;
	}

	public void setChat(ChatDTO chat) {
		this.chat = chat;
	}
	
}
