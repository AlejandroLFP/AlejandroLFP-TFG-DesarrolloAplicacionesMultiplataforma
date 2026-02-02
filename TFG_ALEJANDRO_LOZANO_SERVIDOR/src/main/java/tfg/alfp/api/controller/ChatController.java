package tfg.alfp.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfg.alfp.api.service.ChatService;
import tfg.alfp.vo.Chat;
import tfg.alfp.vo.Mensaje;
import tfg.alfp.vo.Obra;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
	
	private final ChatService servicio;
	
	public ChatController(ChatService servicio) {
		this.servicio = servicio;
	}
	
	@PostMapping("/crear")
	public boolean crear(@RequestBody Chat chatNuevo) {
		boolean creado = servicio.crear(chatNuevo);
		if (creado) {
			return true;
		}else {
			return false;
		}
	}
	
	@PostMapping("/listar")
	public List<Chat> listar(@RequestBody Obra obra) {
		String tituloObra = obra.getTitulo();
		return servicio.listar(tituloObra);
	}
	
	@PostMapping("/buscar")
	public List<Mensaje> obtenerMensajes(@RequestBody Chat chat){
		return servicio.buscar(chat.getId());
	}
}
