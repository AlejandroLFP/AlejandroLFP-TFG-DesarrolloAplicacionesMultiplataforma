package tfg.alfp.chat.cliente;

import java.lang.reflect.Type;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javafx.application.Platform;
import tfg.alfp.dto.ChatDTO;
import tfg.alfp.dto.MensajeDTO;
import tfg.alfp.ui.VentanaChat;

public class WebSocketCliente {

	private StompSession sess;

	public void conectar(String url, ChatDTO chat, VentanaChat ventana) {
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompClient.connectAsync(url, new StompSessionHandlerAdapter() {
			@Override
			public void afterConnected(StompSession session, StompHeaders connect) {
				sess = session;
				System.out.println("Conectado al servidor");
				String topic = "/topic/chatroom." + chat.getId();
				sess.subscribe(topic, new StompFrameHandler() {
					@Override
					public Type getPayloadType(StompHeaders headers) {
						return MensajeDTO.class;
					}
					
					@Override
					public void handleFrame(StompHeaders headers, Object msjRecibido) {
						MensajeDTO mensaje = (MensajeDTO) msjRecibido;
						Platform.runLater(() ->{
							System.out.println("Mensaje recibido");
							ventana.escribirEntrada(mensaje.getContenido(), mensaje.getUsuario().getUserName());
						});
					}
				});
			}
		});
	}
	
	public void enviarMensaje(MensajeDTO mensaje) throws Exception {
		System.out.println("Mensaje enviado");
		if(sess != null && sess.isConnected()) {
			int chatId = mensaje.getChat().getId();
			sess.send("/app/chatroom." + chatId  + ".send", mensaje);
		}
	}
}
