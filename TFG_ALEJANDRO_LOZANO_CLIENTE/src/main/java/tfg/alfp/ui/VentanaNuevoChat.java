package tfg.alfp.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tfg.alfp.api.ChatApiClient;
import tfg.alfp.dto.ChatDTO;
import tfg.alfp.dto.ObraDTO;
import tfg.alfp.dto.UsuarioDTO;
import tfg.alfp.trmt.GestorFechas;

public class VentanaNuevoChat {
	
	private static final ChatApiClient apiChat = new ChatApiClient();

	public void mostrar(ObraDTO obra, UsuarioDTO usuario) {
		Stage stage = new Stage();
		List<Node> nodos = new ArrayList<>();
		BorderPane root = new BorderPane();

		// Titulo.
		Label lbTitulo = new Label("Título:");
		TextField txTitulo = new TextField();
		txTitulo.setMaxSize(100, 25);
		nodos.add(lbTitulo);
		nodos.add(txTitulo);

		// Fecha.
		DatePicker fecha = new DatePicker();
		fecha.setValue(LocalDate.now());

		// Botón Crear.
		Button btnCrear = new Button("Crear Chat");
		btnCrear.setAlignment(Pos.CENTER);
		HBox caja = new HBox();
		btnCrear.setOnAction(e -> {
			String titulo = txTitulo.getText();
			Date fechaN = GestorFechas.convertirLocalDate(fecha.getValue());
			ChatDTO chat = new ChatDTO(titulo, fechaN, obra, usuario);
			ejecutarCrear(stage, chat);

		});
		caja.setAlignment(Pos.CENTER);
		caja.getChildren().add(btnCrear);
		nodos.add(caja);

		VBox layout = new VBox(new Label());
		layout.getChildren().addAll(nodos);
		layout.setStyle("-fx-padding: 20;");

		root.setCenter(layout);
		BorderPane.setAlignment(layout, Pos.CENTER);

		Scene scene = new Scene(root, 200, 150);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.setTitle("Chat");
		stage.initStyle(StageStyle.UTILITY);
		stage.show();
	}
	
	private void ejecutarCrear(Stage stage, ChatDTO chat) {
		if (chat.getTitulo().isEmpty() || chat.getFechaCreacion() == null) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setTitle("Faltan Campos");
			alerta.setContentText("Por favor, rellene todos los campos");
			alerta.showAndWait();
			return;
		}

		System.out.println(chat);
		new Thread(() -> {
			try {
				boolean respuesta = apiChat.crear(chat);
				System.out.println("Respuesta del servidor: " + respuesta);
				Platform.runLater(() -> {
					if (respuesta) {
						Alert ok = new Alert(Alert.AlertType.CONFIRMATION);
						ok.setTitle("Chat creado");
						ok.setHeaderText("Chat creado correctamente");
						ok.showAndWait();
						stage.close();
					}
				});
			} catch (Exception ex) {
				Platform.runLater(() -> {
					Alert alerta = new Alert(Alert.AlertType.ERROR);
					alerta.setTitle("Chat existe");
					alerta.setContentText("No se pudo crear el Chat, ya existente");
					alerta.showAndWait();
					ex.printStackTrace();
				});
			}
		}).start();
	}
}
