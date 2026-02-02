package tfg.alfp;

import javafx.application.Application;
import tfg.alfp.chat.cliente.WebSocketCliente;
import tfg.alfp.ui.InterfazCliente;

public class ClienteApp {

	public static WebSocketCliente ws;

	public static void main(String[] args) {
		System.out.println(">> COMIENZA EL PROGRAMA");
		Application.launch(InterfazCliente.class);
	}

}