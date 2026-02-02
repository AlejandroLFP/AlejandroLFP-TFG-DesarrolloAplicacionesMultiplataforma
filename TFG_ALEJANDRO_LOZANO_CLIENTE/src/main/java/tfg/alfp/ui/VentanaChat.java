package tfg.alfp.ui;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tfg.alfp.api.ChatApiClient;
import tfg.alfp.api.MensajeApiClient;
import tfg.alfp.api.SeguimientoApiClient;
import tfg.alfp.chat.cliente.WebSocketCliente;
import tfg.alfp.dto.ChatDTO;
import tfg.alfp.dto.MensajeDTO;
import tfg.alfp.dto.ObraDTO;
import tfg.alfp.dto.SeguimientoDTO;
import tfg.alfp.dto.UsuarioDTO;
import tfg.alfp.util.GestorFicheroConfiguracion;

public class VentanaChat {

	private VBox areaMensaje;
	private WebSocketCliente wsCliente;
	private String url = GestorFicheroConfiguracion.getRuta("urlws");
	// Crear Controller y Service.
	private MensajeApiClient apiMensaje = new MensajeApiClient();
	private  UsuarioDTO usuarioChat;
	
	public VentanaChat(UsuarioDTO usuario) {
		this.usuarioChat = usuario;
	}

	public void mostrar(Stage stageObra, ChatDTO chat, ObraDTO obra) {
		BorderPane pane = new BorderPane();
		areaMensaje = new VBox(10);
		areaMensaje.setPadding(new Insets(10));
		ScrollPane scroll = new ScrollPane(areaMensaje);
		scroll.setFitToWidth(true);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		iniciarConexion(url, chat);
		cargarMensajes(chat, usuarioChat);

		// Barra para Escribir.
		TextArea campo = new TextArea();
		campo.setPromptText("Ecribe un mensaje...");
		campo.setWrapText(true);
		campo.setPrefRowCount(3);
		Button btnAgregar = new Button("Agregar");
		btnAgregar.setOnAction(e -> {
			new Thread(() -> {
				agregarChat(chat, usuarioChat);
			}).start();
		});
		Button btnEnviar = new Button("Enviar ➤");
		btnEnviar.setOnAction(e -> {
			String texto = campo.getText();
			if (!texto.isEmpty()) {
				campo.clear();
				Date fecha = new Date();
				enviarMensaje(chat, texto, fecha, usuarioChat);
			}
		});

		HBox barra = new HBox(10, btnAgregar, campo, btnEnviar);
		barra.setPadding(new Insets(10));
		barra.setAlignment(Pos.CENTER);
		HBox.setHgrow(campo, Priority.ALWAYS);
		barra.setMaxWidth(Double.MAX_VALUE);

		pane.setCenter(scroll);
		pane.setBottom(barra);

		Scene scene = new Scene(pane, 600, 500);
		stageObra.setScene(scene);
		stageObra.setTitle(chat.getTitulo() + " - " + obra.getTitulo());
		stageObra.setWidth(700);
		stageObra.setHeight(500);
		stageObra.setMaximized(false);
		stageObra.setResizable(true);
		stageObra.show();
		// Cerrar la ventana cierra ws
//		stageObra.setOnCloseRequest(event -> {
//			if(wsCliente != null && wsCliente.getSession() != null) {
//				try {
//					wsCliente.getSession().close();
//				} catch (IOException ex) {
//					ex.printStackTrace();
//				}
//			}
//		});

		// Platform.runLater(() -> agregarMensaje("MENSAJE DE PRUEBA", false));
		// String urlChat = url + (chat.getId());

	}

	private void agregarChat(ChatDTO chat, UsuarioDTO usuario) {
		SeguimientoApiClient apiSeguir = new SeguimientoApiClient();
		try {
			apiSeguir.agregar(new SeguimientoDTO(chat, usuario));
			Platform.runLater(() -> {
				Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
				alerta.setContentText("Chat agregado");
				alerta.show();
			});
		} catch (IOException e) {
			e.printStackTrace();
			Platform.runLater(() -> {
				Alert alerta = new Alert(Alert.AlertType.ERROR);
				alerta.setContentText("Error al agregar el chat");
				alerta.show();
			});
		}

	}

	private void iniciarConexion(String urlChat, ChatDTO chat) {
		wsCliente = new WebSocketCliente();
		try {
			wsCliente.conectar(urlChat, chat, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarMensajes(ChatDTO chat, UsuarioDTO usuario) {
		ChatApiClient apiChat = new ChatApiClient();
		new Thread(() -> {
			try {
				List<MensajeDTO> mensajes = apiChat.buscarMensajes(chat);
				Platform.runLater(() -> {
					for (MensajeDTO m : mensajes) {
						String nombre = m.getUsuario().getUserName();
						boolean mio = nombre.equals(usuario.getUserName());
						agregarMensaje(m.getContenido(), mio, nombre, usuario);
					}
				});
			} catch (Exception e) {
				Platform.runLater(() -> {
					Alert alerta = new Alert(Alert.AlertType.ERROR);
					alerta.setContentText("No se pudieron cargar los mensajes.");
					alerta.show();
				});
			}
		}).start();
	}

	// Método para enviar
	private void enviarMensaje(ChatDTO chat, String mensajeTx, Date fecha, UsuarioDTO usuario) {
		// Primero enviar mensaje.
		escribirMensaje(mensajeTx, usuario.getUserName(), usuario);

		// Guardar Menaje BBDD, pero antes verificar llegada
		MensajeDTO mensaje = new MensajeDTO(mensajeTx, fecha, chat, usuario);
		try {
			wsCliente.enviarMensaje(mensaje);
			new Thread(() -> {
				try {
					apiMensaje.guardar(mensaje);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Mostrar mensaje enviado
	private void escribirMensaje(String mensaje, String nombre, UsuarioDTO usuario) {
		Label lbMensaje;
		HBox caja;
		lbMensaje = new Label("Tu:\n" + mensaje);
		lbMensaje.setStyle(
				"-fx-background-color: #a0c4ff; -fx-text-fill: black; -fx-padding: 8px; -fx-background-radius: 10px;");
		caja = new HBox(lbMensaje);
		caja.setAlignment(Pos.CENTER_RIGHT);
		lbMensaje.setMaxSize(200, 200);
		lbMensaje.setWrapText(true);
		caja.setPadding(new Insets(5, 10, 5, 10));
		areaMensaje.getChildren().add(caja);
	}
	
	// Mostrar mensaje recibido
	public void escribirEntrada(String mensaje, String nombre) {
		Label lbMensaje;
		HBox caja;
		if (!nombre.equals(usuarioChat.getUserName())) {
			lbMensaje = new Label(nombre + ":\n" + mensaje);
			lbMensaje.setStyle(
					"-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-padding: 8px; -fx-background-radius: 10px;");
			caja = new HBox(lbMensaje);
			caja.setAlignment(Pos.CENTER_LEFT);
			lbMensaje.setMaxSize(200, 200);
			lbMensaje.setWrapText(true);
			caja.setPadding(new Insets(5, 10, 5, 10));
			areaMensaje.getChildren().add(caja);
		}
	}

	// Mostrar mensajes cargados
	private void agregarMensaje(String mensaje, boolean mio, String nombre, UsuarioDTO usuario) {
		Label lbMensaje;
		HBox caja;
			if (mio) {
				lbMensaje = new Label("Tu:\n" + mensaje);
				lbMensaje.setStyle(
						"-fx-background-color: #a0c4ff; -fx-text-fill: black; -fx-padding: 8px; -fx-background-radius: 10px;");
				caja = new HBox(lbMensaje);
				caja.setAlignment(Pos.CENTER_RIGHT);
			} else {
				lbMensaje = new Label(nombre + ":\n" + mensaje);
				lbMensaje.setStyle(
						"-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-padding: 8px; -fx-background-radius: 10px;");
				caja = new HBox(lbMensaje);
				caja.setAlignment(Pos.CENTER_LEFT);
			}
			lbMensaje.setMaxSize(200, 200);
			lbMensaje.setWrapText(true);
			caja.setPadding(new Insets(5, 10, 5, 10));
			areaMensaje.getChildren().add(caja);
	}

	public void nueva(ChatDTO chat) {
		Stage stageObra = new Stage();
		ObraDTO obra = chat.getObra();
		mostrar(stageObra, chat, obra);
	}

}
