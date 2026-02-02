package tfg.alfp.vo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_obra")
public class Obra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "titulo")
	private String titulo;
	@Column(name = "genero")
	private String genero;
	@Column(name = "duracion")
	private int duracion;
	@Column(name = "descripcion")
	private String descripcion;
	// URL de la imagen
	@Column(name = "portada")
	private String portada;

	public Obra() {
		
	}
	
	public Obra(int id) {
		this.id = id;
	}

	public Obra(String titulo, String genero, int duracion,
			String descripcion, String portada) {
		this.titulo = titulo;
		this.genero = genero;
		this.duracion = duracion;
		this.descripcion = descripcion;
		this.portada = portada;
	}

	public Obra(int id, String titulo, String genero, int duracion, String descripcion, String portada) {
		this.id = id;
		this.titulo = titulo;
		this.genero = genero;
		this.duracion = duracion;
		this.descripcion = descripcion;
		this.portada = portada;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
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
		Obra other = (Obra) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Obra [id=" + id + ", titulo=" + titulo + ", genero=" + genero
				+ ", duracion=" + duracion + ", descripcion=" + descripcion
				+ ", portadaURL=" + portada + "]";
	}
	
}
