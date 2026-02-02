package tfg.alfp.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfg.alfp.api.service.MensajeService;
import tfg.alfp.vo.Mensaje;

@RestController
@RequestMapping("/api/mensaje")
public class MensajeController {
	
	private final MensajeService servicio;
	
	public MensajeController(MensajeService servicio) {
		this.servicio = servicio;
	}
	
	@PostMapping("/guardar")
	public void guardar(@RequestBody Mensaje mensaje) {
		servicio.guardar(mensaje);
	}
}
