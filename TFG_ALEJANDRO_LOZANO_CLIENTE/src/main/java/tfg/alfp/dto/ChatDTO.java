package tfg.alfp.dto;

import java.util.Date;

public class ChatDTO {
	
	private int id;
	private String titulo;
	private Date fechaCreacion;
	
	private ObraDTO obra;
	private UsuarioDTO usuario;
	
	public ChatDTO() {
		
	}
	
	public ChatDTO(String titulo, Date fechaCreacion) {
		this.titulo = titulo;
		this.fechaCreacion = fechaCreacion;
	}

	public ChatDTO(String titutlo, Date fechaCreacion, ObraDTO obra, UsuarioDTO usuario) {
		this.titulo = titutlo;
		this.fechaCreacion = fechaCreacion;
		this.obra = obra;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitutlo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public ObraDTO getObra() {
		return obra;
	}

	public void setObra(ObraDTO obra) {
		this.obra = obra;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
}
