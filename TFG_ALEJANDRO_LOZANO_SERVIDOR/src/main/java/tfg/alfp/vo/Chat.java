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
@Table(name = "t_chat")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "titulo", nullable = false, unique = true)
	private String titulo;
	@Column(name = "fechaCreacion", nullable = false)
	private Date fechaCreacion;
	
	@ManyToOne
	@JoinColumn(name = "id_obra")
	private Obra obra;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	public Chat() {
		
	}
	
	public Chat(int id) {
		this.id = id;
	}

	public Chat (int id, String titulo, Date fechaCreacion) {
		this.id = id;
		this.titulo = titulo;
		this.fechaCreacion = fechaCreacion;
	}

	public Chat(int id, String titulo, Date fechaCreacion, Obra obra, Usuario usuario) {
		this.id = id;
		this.titulo = titulo;
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

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Chat other = (Chat) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", titulo=" + titulo + ", fechaCreacion=" + fechaCreacion +
				", obra=" + obra + ", usuario=" + usuario + "]";
	}
}
