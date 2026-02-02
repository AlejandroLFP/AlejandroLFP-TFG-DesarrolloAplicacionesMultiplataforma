package tfg.alfp.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfg.alfp.api.service.UsuarioService;
import tfg.alfp.vo.Usuario;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	private final UsuarioService servicio;
	
	public UsuarioController(UsuarioService servicio) {
		this.servicio = servicio;
	}
	
	@PostMapping("/crear")
	public boolean crear(@RequestBody Usuario usuario) {
		boolean creado = servicio.crearUsuario(usuario.getNombre(), usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getDescripcion(), usuario.getUserName(), usuario.getPassword());
		if (creado) {
			return true;
		}else {
			return false;
		}
	}
	
	@PostMapping("/obtener")
	public Usuario obtener(@RequestBody Usuario user) {
		Usuario usuario = servicio.obtenerUsuario(user.getUserName());
		return usuario;
	}

}
