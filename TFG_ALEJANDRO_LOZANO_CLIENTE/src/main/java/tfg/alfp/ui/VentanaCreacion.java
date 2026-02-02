package tfg.alfp.ui;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tfg.alfp.api.UsuarioApiClient;
import tfg.alfp.dto.UsuarioDTO;
import tfg.alfp.trmt.GestorFechas;

public class VentanaCreacion {
	
	private static UsuarioApiClient apiUsuario = new UsuarioApiClient();

	public void mostrar(Stage stage) {
		List<Node> nodos = new ArrayList<>();
		BorderPane root = new BorderPane();

		// Nombre.
		Label lbNombre = new Label("Nombre:");
		TextField txNombre = new TextField();
		txNombre.setMaxSize(100, 25);
		nodos.add(lbNombre);
		nodos.add(txNombre);

		// Apellidos.
		Label lbApellidos = new Label("Apellidos:");
		TextField txApellidos = new TextField();
		txApellidos.setMaxSize(100, 25);
		nodos.add(lbApellidos);
		nodos.add(txApellidos);

		// Fecha Nacimiento.
		Label lbFechaN = new Label("Fecha Nacimiento:");
		DatePicker dateP = new DatePicker();
		LocalDate fecha = dateP.getValue();
		dateP.setValue(fecha);
		txNombre.setMaxSize(100, 25);
		nodos.add(lbFechaN);
		nodos.add(dateP);

		// Descripción.
		Label lbDescripcion = new Label("Descripcion");
		TextField txDescripcion = new TextField();
		txDescripcion.setMaxSize(100, 75);
		nodos.add(lbDescripcion);
		nodos.add(txDescripcion);

		// Usuario.
		Label lbUserName = new Label("Usuario:");
		TextField txUserName = new TextField();
		txUserName.setMaxSize(100, 25);
		nodos.add(lbUserName);
		nodos.add(txUserName);

		// Contraseña.
		Label lbPassword = new Label("Contraseña:");
		PasswordField txArPassword = new PasswordField();
		txArPassword.setMaxSize(100, 25);
		nodos.add(lbPassword);
		nodos.add(txArPassword);

		// Botón Crear.
		Button btnCrear = new Button("Crear Cuenta");
		btnCrear.setAlignment(Pos.CENTER);
		HBox caja = new HBox();
		btnCrear.setOnAction(e -> {
			String nombre = txNombre.getText();
			String apellidos = txApellidos.getText();
			Date fechaN = GestorFechas.convertirLocalDate(dateP.getValue());
			String descripcion = txDescripcion.getText();
			String userName = txUserName.getText();
			String password = txArPassword.getText();
			ejecutarCrear(nombre, apellidos, fechaN, descripcion, userName, password, stage);

		});
		caja.setAlignment(Pos.CENTER);
		caja.getChildren().add(btnCrear);
		nodos.add(caja);
		
		Button btnVolver = new Button("Volver");
		btnVolver.setAlignment(Pos.CENTER);
		HBox cajaVolver = new HBox();
		btnVolver.setOnAction(e -> {
			VentanaLogin ventana = new VentanaLogin();
			ventana.mostrar(stage);
		});
		cajaVolver.setAlignment(Pos.CENTER_RIGHT);
		cajaVolver.getChildren().add(btnVolver);
		nodos.add(cajaVolver);

		VBox layout = new VBox(new Label());
		layout.getChildren().addAll(nodos);
		layout.setStyle("-fx-padding: 20;");

		root.setCenter(layout);
		BorderPane.setAlignment(layout, Pos.CENTER);

		Scene scene = new Scene(root, 600, 450);
		stage.setScene(scene);
		stage.setTitle("Crear Cuenta");
		stage.centerOnScreen();
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.show();
	}

	private void ejecutarCrear(String nombre, String apellidos, Date fechaN, String descripcion, String userName,
			String password, Stage stage) {
		if (nombre.isEmpty() || apellidos.isEmpty() || fechaN == null || descripcion.isEmpty() || userName.isEmpty()
				|| password.isEmpty()) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setTitle("Faltan Campos");
			alerta.setContentText("Por favor, rellene todos los campos");
			alerta.showAndWait();
			return;
		}

		UsuarioDTO usuario = new UsuarioDTO(nombre, apellidos, fechaN, descripcion, userName, password);
		new Thread(() -> {
			try {
				boolean respuesta = apiUsuario.crearCuenta(usuario);
				System.out.println("Respuesta del servidor: " + respuesta);
				Platform.runLater(() -> {
					if (respuesta) {
						Alert ok = new Alert(Alert.AlertType.CONFIRMATION);
						ok.setTitle("Usuario creado");
						ok.setHeaderText("Cuenta creada correctamente");
						ok.showAndWait();
						new VentanaLogin().mostrar(stage);
					}
				});
			} catch (Exception ex) {
				Platform.runLater(() -> {
					Alert alerta = new Alert(Alert.AlertType.ERROR);
					alerta.setTitle("Usuario no válido");
					alerta.setContentText("Usuario existente o no valido");
					alerta.showAndWait();
					ex.printStackTrace();
				});
			}
		}).start();
	}

}
