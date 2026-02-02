package tfg.alfp.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfg.alfp.api.service.SeguimientoService;
import tfg.alfp.vo.Chat;
import tfg.alfp.vo.Seguimiento;
import tfg.alfp.vo.Usuario;

@RestController
@RequestMapping("/api/seguimiento")
public class SeguimientoController {
	
	private final SeguimientoService servicio;
	
	public SeguimientoController(SeguimientoService servicio) {
		this.servicio = servicio;
	}
	
	@PostMapping("/agregar")
	public boolean agregar(@RequestBody Seguimiento seguido) {
		boolean hecho = servicio.agregar(seguido);
		if(hecho) {
			return true;
		}
		return false;
	}
	
	@PostMapping("/eliminar")
	public boolean eliminar(@RequestBody Seguimiento seguido) {
		boolean hecho = servicio.eliminar(seguido);
		if(hecho) {
			return true;
		}
		return false;
	}
	
	@PostMapping("/listar")
	public List<Chat> listar(@RequestBody Usuario usuario) {
		return servicio.listar(usuario.getId());
	}
	

}
