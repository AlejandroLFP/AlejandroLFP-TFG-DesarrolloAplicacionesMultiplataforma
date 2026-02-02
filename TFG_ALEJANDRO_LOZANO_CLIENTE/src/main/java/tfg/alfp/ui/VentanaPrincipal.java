package tfg.alfp.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tfg.alfp.api.ObraApiClient;
import tfg.alfp.api.SeguimientoApiClient;
import tfg.alfp.dto.ChatDTO;
import tfg.alfp.dto.ObraDTO;
import tfg.alfp.dto.UsuarioDTO;
import tfg.alfp.trmt.GestorImagenes;
import tfg.alfp.util.GestorFicheroConfiguracion;

public class VentanaPrincipal {

	private VBox pIzquierdo;
	private VBox pDerecho;
	private VBox cajaResultados;
	private Map<String, ChatDTO> mapaChats = new HashMap<>();

	private static final String LOGO_RUTA = GestorFicheroConfiguracion.getRuta("portada");
	private static final ObraApiClient apiObra = new ObraApiClient();

	public void mostrar(Stage stage, UsuarioDTO usuario) {
		List<Node> nIzquierda = new ArrayList<>();
		List<Node> nDerecha = new ArrayList<>();
		SplitPane pane = new SplitPane();
		pIzquierdo = new VBox();
		pDerecho = new VBox();

		// Lado Izquierdo.
		ImageView imagenLogo = new ImageView(new Image("file:" + LOGO_RUTA));
		imagenLogo.setFitWidth(300);
		imagenLogo.setFitHeight(200);
		imagenLogo.setPreserveRatio(true);
		HBox cajaImg = new HBox(imagenLogo);
		cajaImg.setAlignment(Pos.CENTER);
		cajaImg.setPadding(new Insets(15));
		nIzquierda.add(cajaImg);

		Label lbPresentacion = new Label("Bienvenido " + usuario.getNombre()
				+ " a LetsTalk, un espacio donde compartir tus opiciones y dudas con más personas."
				+ "Busca tu videojuego favorito y únete a un chat o crea uno nuevo.");
		lbPresentacion.setWrapText(true);
		nIzquierda.add(lbPresentacion);
		Label lbUserDatos = new Label("| DATOS DEL USUARIO |");
		Label lbUserName = new Label("Usuario: " + usuario.getUserName());
		Label lbDescripcion = new Label("Descripción: " + usuario.getDescripcion());
		VBox cajaDatos = new VBox(6);
		cajaDatos.getChildren().addAll(lbUserDatos, lbUserName, lbDescripcion);
		cajaDatos.setAlignment(Pos.CENTER);
		nIzquierda.add(cajaDatos);
		
		// Lista de Chats
		Label lbSeguir = new Label("\nChats que estás siguendo:");
		ListView<String> listaTitulos = new ListView<>();
		List<ChatDTO> listaChats = new ArrayList<>();
		Button btnLista = new Button("Mostrar");
		btnLista.setAlignment(Pos.CENTER);
		btnLista.setOnAction(e -> {
			cargarChats(usuario, listaChats, listaTitulos);
			listaTitulos.setOnMouseClicked(event -> {
				String chatTitulo = listaTitulos.getSelectionModel().getSelectedItem();
			    if (chatTitulo != null && mapaChats.containsKey(chatTitulo)) {
			        ChatDTO chat = mapaChats.get(chatTitulo);
			        new VentanaChat(usuario).nueva(chat);
			    }
			});
		});
		btnLista.setStyle("-fx-background-color: #59abea");
		
		VBox cajaSeguir = new VBox(6);
		cajaSeguir.getChildren().addAll(lbSeguir, listaTitulos, btnLista);
		cajaSeguir.setAlignment(Pos.CENTER);
		cajaSeguir.setMaxHeight(300);
		nIzquierda.add(cajaSeguir);

		// Lado Derecha.
		TextField busqueda = new TextField();
		busqueda.setPromptText("Busca un Videojuego");
		Button btnBuscar = new Button("🔍");
		btnBuscar.setOnAction(e -> {
			String nombreBusqueda = busqueda.getText();
			ejecutarBuscar(nombreBusqueda, stage, usuario);
		});
		HBox barraBusqueda = new HBox(busqueda, btnBuscar);
		HBox.setHgrow(busqueda, Priority.ALWAYS);
		nDerecha.add(barraBusqueda);
		cajaResultados = new VBox(10);
		nDerecha.add(cajaResultados);

		pIzquierdo.getChildren().addAll(nIzquierda);
		pDerecho.getChildren().addAll(nDerecha);
		pane.getItems().addAll(pIzquierdo, pDerecho);
		pane.setDividerPositions(0.5);

		Scene scene = new Scene(pane, 800, 800);
		stage.setScene(scene);
		stage.setTitle("Lets Talk");
		stage.setMaximized(true);
		stage.setResizable(true);
		stage.show();
	}
	
	private void cargarChats(UsuarioDTO usuario, List<ChatDTO> listaChats, ListView<String>listaTitulos) {
		SeguimientoApiClient apiSeguimiento = new SeguimientoApiClient();
		new Thread(() -> {
			try {
				listaChats.clear();
				Platform.runLater(() -> {
					listaTitulos.getItems().clear();
					mapaChats.clear();
				});
				listaChats.addAll(apiSeguimiento.listar(usuario));
				
				Platform.runLater(() -> {
					for(ChatDTO c:listaChats) {
						String titulo = c.getTitulo() + " - " + c.getObra().getTitulo();
						listaTitulos.getItems().addAll(titulo);
						mapaChats.put(titulo, c);
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
	
	private void ejecutarBuscar(String nombreBusqueda, Stage stage, UsuarioDTO usuario) {
		if (nombreBusqueda.isEmpty() || nombreBusqueda == null) {
			return;
		}
		new Thread(() -> {
			try {
				List<ObraDTO> listaObras = apiObra.portada(new ObraDTO(nombreBusqueda));
				Platform.runLater(() -> {
					mostrarPortadas(listaObras, stage, usuario);
				});
			} catch (Exception ex) {
				Platform.runLater(() -> {
					Alert alerta = new Alert(Alert.AlertType.ERROR);
					alerta.setContentText("Videojuego no encontrado");
					alerta.setTitle("Error de búsqueda");
					alerta.showAndWait();
				});
			}
		}).start();
	}

	private void mostrarPortadas(List<ObraDTO> listaObras, Stage stage, UsuarioDTO usuario) {
		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(20));

		int col = 0, fil = 0;
		int max = 4;

		for (int i=0; i<listaObras.size(); i++) {
			StackPane celda = new StackPane();
			celda.setPrefSize(120, 180);
			celda.setStyle("-fx-border-color: lightgray; -fx-background-color: #f4f4f4; -fx-alignment: center;");
			ObraDTO obra = listaObras.get(i);

			ImageView img = GestorImagenes.obtenerImagen(obra.getPortada());
			System.out.println("¡¡" + img);
			if (img != null) {
				img.setFitWidth(120);
				img.setFitHeight(180);
				img.setPreserveRatio(true);
				celda.getChildren().add(img);
			} else {
				Label titulo = new Label(obra.getTitulo());
				titulo.setWrapText(true);
				titulo.setMaxWidth(100);
				titulo.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
				celda.getChildren().add(titulo);
			}
			
			celda.setOnMouseClicked(event -> {
				try {
					Platform.runLater(() -> {
						new VentanaObra().mostrar(stage, obra, usuario);
					});
				} catch (Exception e) {
					Platform.runLater(() -> {
						Alert alerta = new Alert(Alert.AlertType.ERROR);
						alerta.setContentText("Error al cargar elemento");
						alerta.showAndWait();
					});
				}
			});

			grid.add(celda, col, fil);
			col++;
			if (col == max) {
				col = 0;
				fil++;
			}
		}
		ScrollPane scroll = new ScrollPane(grid);
		scroll.setFitToWidth(true);
		scroll.setPannable(true);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		cajaResultados.getChildren().clear();
		cajaResultados.getChildren().add(scroll);
	}
}
