package tfg.alfp.dto;

public class ObraDTO {
	
	private int id;
	private String titulo;
	private String genero;
	private int duracion;
	private String descripcion;
	private String portada;
	
	public ObraDTO() {
		
	}
	
	public ObraDTO(String titulo) {
		this.titulo = titulo;
	}

	public ObraDTO(int id, String titulo, String genero, int duracion, String descripcion, String portada) {
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
	
}
