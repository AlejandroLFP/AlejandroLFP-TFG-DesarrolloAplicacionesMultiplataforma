package tfg.alfp.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfg.alfp.api.service.ObraService;
import tfg.alfp.vo.Obra;

@RestController
@RequestMapping("/api/obra")
public class ObraController {
	
	private final ObraService servicio;
	
	public ObraController(ObraService servicio) {
		this.servicio = servicio;
	}
	
	@PostMapping("/portada")
	public List<Obra> portada(@RequestBody Obra obra) {
		return servicio.portada(obra.getTitulo());
	}
	
//	@PostMapping("/datos")
//	public Obra datos(@RequestBody Obra obra) {
//		return servicio.datos(obra.getTitulo());
//	}

}
