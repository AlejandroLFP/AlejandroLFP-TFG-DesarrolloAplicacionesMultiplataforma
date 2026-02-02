package tfg.alfp.api.service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.alfp.dao.impl.UsuarioDAOHibernate;
import tfg.alfp.vo.Usuario;

@Service
public class UsuarioService {
	
	private UsuarioDAOHibernate repositorio;
	private final PasswordEncoder encoder;
	
	public UsuarioService(UsuarioDAOHibernate repositorio, PasswordEncoder encoder) {
		this.repositorio = repositorio;
		this.encoder = encoder;
	}
	

	public boolean validarUsuario(String userName, String password) {
		Usuario usuario = repositorio.obtenerUsuario(userName);
		if(usuario == null) {
			return false;
		}
		return encoder.matches(password, usuario.getPassword());
	}

	public boolean crearUsuario(String nombre, String apellidos, Date fechaNacimiento, String descripcion, String userName, String password) {
		String hash = encoder.encode(password);
		Usuario entidad = new Usuario(nombre, apellidos, fechaNacimiento, descripcion, userName, hash);
		return repositorio.crear(entidad);
	}

	public Usuario obtenerUsuario(String userName) {
		Usuario usuario = repositorio.obtenerUsuario(userName);
		return usuario;
	}
}
