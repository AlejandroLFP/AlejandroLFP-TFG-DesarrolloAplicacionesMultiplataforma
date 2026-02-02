package tfg.alfp.dto;

public class SeguimientoDTO {
	
	private int id;
	
	private ChatDTO chat;
	private UsuarioDTO usuario;
	
	public SeguimientoDTO() {
		
	}
	
	public SeguimientoDTO(ChatDTO chat, UsuarioDTO usuario) {
		this.chat = chat;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ChatDTO getChat() {
		return chat;
	}

	public void setChat(ChatDTO chat) {
		this.chat = chat;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
	

}
