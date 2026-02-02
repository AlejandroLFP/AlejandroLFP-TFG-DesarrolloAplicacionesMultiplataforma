package tfg.alfp.trmt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GestorImagenes {
	
	public static ImageView obtenerImagen(String url) {
		if(url == null || url.isBlank()) {
			return null;
		}
		return new ImageView(new Image(url, false));
	}
	
	public static Image parseImage(byte[] datosImagen) {
		InputStream is = new ByteArrayInputStream(datosImagen);
		return new Image(is);
	}

}
