package tfg.alfp.chat.server;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import tfg.alfp.vo.Mensaje;

@Controller
public class WebSocketController {

	private final SimpMessagingTemplate messagingTemplate;

	public WebSocketController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/chatroom.{id}.send")
	public void enviarMensaje(@DestinationVariable String id, Mensaje mensaje) {
		System.out.println("Mensaje recibido");
        // Broadcast a todos los suscriptores al topic /topic/chatroom.{id}
        messagingTemplate.convertAndSend("/topic/chatroom." + id, mensaje);
    }
}
