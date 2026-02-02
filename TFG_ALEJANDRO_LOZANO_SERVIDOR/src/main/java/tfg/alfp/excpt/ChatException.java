package tfg.alfp.excpt;

public class ChatException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public static final long ERROR_BUSQUEDA = 0;
	public static final long ERROR_CREACION = 1;
	public static final long ERROR_ACTUALIZACION = 2;
	public static final long ERROR_ELIMINACION = 3;
	public static final long ERROR_OTRO = 4;

	private final long codigoError;
	private final String nombreClase;
	
	
	// Constructores
	public ChatException() {
		super("Ha ocurrido un error relacionado con el ProyectoIES.");
		this.codigoError = -1;
		this.nombreClase = "Desconocida";
	}
	
	// Este es el que usaré normalmente
	public ChatException(String mensaje, long tipoError, String nombreClase) {
		super(mensaje);
		this.codigoError = tipoError;
		this.nombreClase = nombreClase;
	}

	public ChatException(Throwable causa) {
		super(causa);
		this.codigoError = -1;
		this.nombreClase = "Desconocida";
	}

	
	// Getter para el código de error
	public long getCodigoError() {
		return codigoError;
	}

	// Getter para el nombre de la clase
	public String getNombreClase() {
		return nombreClase;
	}
	
	// Método para obtener una descripción del código de error
	public String getDescripcionError() {
		switch ((int) codigoError) {
		case (int) ERROR_BUSQUEDA:
			return "Error durante la búsqueda.";
		case (int) ERROR_CREACION:
			return "Error durante la creación.";
		case (int) ERROR_ACTUALIZACION:
			return "Error durante la actualización.";
		case (int) ERROR_ELIMINACION:
			return "Error durante la eliminación.";
		case (int) ERROR_OTRO:
			return "Error raro, pero no desconocido.";
		default:
			return "Error desconocido.";
		}
	}

	// ToString
	@Override
	public String toString() {
		return "ProyectoIES Exception {\n" + "Código = " + codigoError + "\nMensaje = " + getMessage()
				+ "\nDescripcion = " + getDescripcionError() + "\nClase = " + nombreClase + "\n}";
	}

}
