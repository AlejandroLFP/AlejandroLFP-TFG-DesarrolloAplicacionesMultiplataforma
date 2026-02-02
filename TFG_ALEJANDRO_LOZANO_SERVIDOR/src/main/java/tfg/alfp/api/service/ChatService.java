package tfg.alfp.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.alfp.dao.impl.ChatDAOHibernate;
import tfg.alfp.vo.Chat;
import tfg.alfp.vo.Mensaje;

@Service
public class ChatService {
	
	@Autowired
	private ChatDAOHibernate repositorio;

	public List<Chat> listar(String nombreObra) {
		List<Chat> chats = repositorio.listarChats(nombreObra);
		return chats;
	}

	public boolean crear(Chat chatNuevo) {
		return repositorio.crearChat(chatNuevo);
	}

	public List<Mensaje> buscar(int mensajeId) {
		List<Mensaje> mensajes = repositorio.buscarMensajes(mensajeId);
		return mensajes;
	}
}
