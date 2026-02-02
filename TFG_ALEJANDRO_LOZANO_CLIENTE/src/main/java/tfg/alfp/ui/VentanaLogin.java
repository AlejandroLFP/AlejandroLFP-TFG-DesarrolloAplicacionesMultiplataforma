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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tfg.alfp.api.AuthApiClient;
import tfg.alfp.api.UsuarioApiClient;
import tfg.alfp.dto.UsuarioDTO;
import tfg.alfp.util.GestorFicheroConfiguracion;

public class VentanaLogin {

	private static final String LOGO_RUTA = GestorFicheroConfiguracion.getRuta("logo");
	private static final UsuarioApiClient apiUsuario = new UsuarioApiClient();
	private AuthApiClient apiAuth = new AuthApiClient();

	public void mostrar(Stage stage) {
		List<Node> nodos = new ArrayList<>();

		// Usuario.
		Label lbUsuario = new Label("Usuario:");
		TextField txArUsuario = new TextField();
		txArUsuario.setMaxSize(100, 25);
		nodos.add(lbUsuario);
		nodos.add(txArUsuario);

		// Contraseña.
		Label lbPassword = new Label("Contraseña:");
		PasswordField txArPassword = new PasswordField();
		txArPassword.setMaxSize(100, 25);
		nodos.add(lbPassword);
		nodos.add(txArPassword);

		// Botón iniciar.
		Button btnLogin = new Button("INCIAR SESIÓN");
		btnLogin.setAlignment(Pos.CENTER);
		btnLogin.setOnAction(e -> {
			String userName = txArUsuario.getText();
			String password = txArPassword.getText();
			ejecutarLogin(stage, userName, password);
		});
		btnLogin.setStyle("-fx-background-color: #59abea");
		nodos.add(btnLogin);

		// Crear Usuario.
		Label lbCrear = new Label("Crear Cuenta");
		lbCrear.setStyle("-fx-text-fill: blue; -fx-underline: true;");
		lbCrear.setOnMouseClicked(event -> {
			new VentanaCreacion().mostrar(stage);
		});
		nodos.add(lbCrear);

		VBox layout = new VBox(10);
		layout.getChildren().addAll(nodos);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color: #f3f3f3");

		Scene scene = new Scene(layout, 400, 250);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.setTitle("Lets Talk");
		stage.getIcons().add(new Image("file:" + LOGO_RUTA));
		stage.show();
	}

	public void ejecutarLogin(Stage stage, String userName, String password) {
		if (userName.isEmpty() || password.isEmpty()) {
			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setContentText("Por favor introduce las credenciales");
			alerta.setTitle("Error");
			alerta.showAndWait();
		}
		new Thread(() -> {
			try {
				boolean respuesta = apiAuth.login(new UsuarioDTO(userName, password));
				System.out.println("Login: " + respuesta);

				if (respuesta) {
					UsuarioDTO usuario = apiUsuario.obtenerCuenta(new UsuarioDTO(userName));
					Platform.runLater(() -> {
						new VentanaPrincipal().mostrar(stage, usuario);
					});
				} else {
					Platform.runLater(() -> {
						Alert alerta = new Alert(Alert.AlertType.ERROR);
						alerta.setHeaderText("Credenciales inválidas");
						alerta.setContentText("Usuario o contraseñas incorrectos");
						alerta.setTitle("Login fallido");
						alerta.showAndWait();
					});
				}
			} catch (IOException ex) {
				Platform.runLater(() -> {
					Alert alerta = new Alert(Alert.AlertType.ERROR);
					alerta.setHeaderText("No se pudo conectar con el servidor");
					alerta.setTitle("Error al conectar");
					alerta.showAndWait();
				});
			}
		}).start();
	}

}
