package tfg.alfp.dto;

import java.util.Date;

public class UsuarioDTO {
	
	private int id;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String descripcion;
	private String userName;
	private String password;
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(String userName) {
		this.userName = userName;
	}
	
	public UsuarioDTO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public UsuarioDTO(String nombre, String apellidos, Date fechaNacimiento, String descripcion, String userName, String password) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.descripcion = descripcion;
		this.userName = userName;
		this.password = password;
	}

	public UsuarioDTO(int id, String nombre, String apellidos, Date fechaNacimiento, String descripcion, String userName, String password) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.descripcion = descripcion;
		this.userName = userName;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String usuario) {
		this.userName = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
