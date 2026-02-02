package tfg.alfp.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class InterfazCliente extends Application {

	// Stage(Ventana principal) - Scene(Contenido de la ventana) - Layout(Paneles,
	// organizar) - Elementos

	@Override
	public void start(Stage primaryStage) throws Exception {
		new VentanaLogin().mostrar(primaryStage);
	}

}
