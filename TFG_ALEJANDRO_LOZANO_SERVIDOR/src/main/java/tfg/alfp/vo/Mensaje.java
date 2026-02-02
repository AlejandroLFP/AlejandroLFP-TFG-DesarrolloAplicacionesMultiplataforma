package tfg.alfp.vo;

import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_mensaje")
public class Mensaje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(name = "contenido", nullable = false)
	private String contenido;
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name = "id_chat")
	private Chat chat;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	public Mensaje() {
		
	}

	public Mensaje(int id) {
		this.id = id;
	}

	public Mensaje(String contenido, Date fecha, Chat chat, Usuario usuario) {
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
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensaje other = (Mensaje) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", contenido=" + contenido + ", fecha=" + fecha + ", chat=" + chat + ", usuario=" + usuario + "]";
	}	
}
