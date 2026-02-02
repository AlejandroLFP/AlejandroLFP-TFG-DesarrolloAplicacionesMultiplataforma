package tfg.alfp.vo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_seguimiento")
public class Seguimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@ManyToOne
	@JoinColumn(name = "id_chat")
	private Chat chat;
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	public Seguimiento() {
		
	}
	
	public Seguimiento(Usuario usuario, Chat chat) {
		this.usuario = usuario;
		this.chat = chat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
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
		Seguimiento other = (Seguimiento) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Seguimiento [id=" + id + ", chat=" + chat + ", usuario="
				+ usuario + "]";
	}
	
}
