package tfg.alfp.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tfg.alfp.api.ChatApiClient;
import tfg.alfp.dto.ChatDTO;
import tfg.alfp.dto.ObraDTO;
import tfg.alfp.dto.UsuarioDTO;
import tfg.alfp.trmt.GestorImagenes;

public class VentanaObra {
	
	private static final ChatApiClient apiChat = new ChatApiClient();

	public void mostrar(Stage stage, ObraDTO obra, UsuarioDTO usuario) {
		Stage stageObra = new Stage();
		stageObra.initOwner(stage);
		List<Node> nIzquierdo = new ArrayList<>();
		List<Node> nDerecho = new ArrayList<>();

		SplitPane pane = new SplitPane();
		VBox cajaIzquierdo = new VBox();
		VBox cajaDerecho = new VBox();
		cajaIzquierdo.setAlignment(Pos.CENTER);
		cajaDerecho.setAlignment(Pos.CENTER);

		// Lado Izquierdo.
		Node portada;
		if(obra.getPortada() != null) {
			ImageView imagen = GestorImagenes.obtenerImagen(obra.getPortada());
			imagen.setFitWidth(120);
			imagen.setFitHeight(210);
			imagen.setPreserveRatio(true);
			portada = imagen;
		}else {
			StackPane silueta = new StackPane();
			silueta.setPrefSize(120, 100);
			silueta.setStyle("-fx-border-color: lightgray; -fx-background-color: #f4f4f4;");
			Label nombre = new Label();
			nombre.setWrapText(true);
			nombre.setMaxWidth(100);
			nombre.setStyle("-fx-text-alignment: center; -fx-font-weight: bold; -fx-text-alignment: center; -fx-font-weight: bold;");
			silueta.getChildren().add(nombre);
			portada = silueta;
		}
		nIzquierdo.add(portada);
		Label lbTitulo = new Label("TÍTULO: " + obra.getTitulo());
		nIzquierdo.add(lbTitulo);
		Label lbGenero = new Label("GÉNERO: " + obra.getGenero());
		nIzquierdo.add(lbGenero);
		Label lbDuracion = new Label("DURACIÓN: " + String.valueOf(obra.getDuracion()));
		nIzquierdo.add(lbDuracion);
		Label lbDescripcion = new Label("DESCRIPCIÓN: " + obra.getDescripcion());
		nIzquierdo.add(lbDescripcion);
		
		// Lado Derecho.
		//Nuevo Chat
		Button btnChatN = new Button("Crear Chat");
		btnChatN.setAlignment(Pos.CENTER);
		btnChatN.setOnAction(e -> {
			new VentanaNuevoChat().mostrar(obra, usuario);
		});
		btnChatN.setStyle("-fx-background-color: #59abea");
		nDerecho.add(btnChatN);
		
		
		// Lista de Chats
		ListView<String> listaTitulos = new ListView<>();
		List<ChatDTO> listaChats = new ArrayList<>();
		Button btnChatL = new Button("Lista Chats");
		btnChatL.setAlignment(Pos.CENTER);
		btnChatL.setOnAction(e -> {
			cargarChats(listaTitulos, obra, listaChats);
			listaTitulos.setOnMouseClicked(event -> {
				String chatTitulo = listaTitulos.getSelectionModel().getSelectedItem();
				ChatDTO chat = new ChatDTO();
				for(ChatDTO c:listaChats) {
					if(c.getTitulo() == chatTitulo) {
						chat = c;
						break;
					}
				}
				new VentanaChat(usuario).mostrar(stageObra, chat, obra);
			});
		});
		nDerecho.add(listaTitulos);
		btnChatL.setStyle("-fx-background-color: #59abea");
		nDerecho.add(btnChatL);


		cajaIzquierdo.getChildren().addAll(nIzquierdo);
		cajaDerecho.getChildren().addAll(nDerecho);
		pane.getItems().addAll(cajaIzquierdo, cajaDerecho);
		pane.setDividerPositions(0.5);
		
		Scene scene = new Scene(pane, 800, 800);
		stageObra.setScene(scene);
		stageObra.setTitle(obra.getTitulo());
		stageObra.setWidth(600);
		stageObra.setHeight(400);
		stageObra.setMaximized(false);
		stageObra.setResizable(true);
		// Revisar poner icono de juego.
//		stage.getIcons().add(new Image(obra.getPortada()));
		stageObra.show();
	}

	private void cargarChats(ListView<String> listaTitulos, ObraDTO obra, List<ChatDTO> listaChats) {
		new Thread(() -> {
			try {
				listaChats.clear();
				listaTitulos.getItems().clear();
				listaChats.addAll(apiChat.listar(obra));
				
				Platform.runLater(() -> {
					for(ChatDTO c:listaChats) {
						listaTitulos.getItems().addAll(c.getTitulo());
					}
				});
			} catch (IOException e) {
				Platform.runLater(()-> {
					Alert alerta = new Alert(Alert.AlertType.ERROR);
					alerta.setTitle("Error");
					alerta.setContentText("No se encontró ningún chat");
					alerta.showAndWait();
				});
			}
		}).start();
	}
}
