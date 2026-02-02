package tfg.alfp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServidorApp {
	
	public static void main(String[] args) {
		// Inicia el chat Server.
		SpringApplication.run(ServidorApp.class, args);
		System.out.println(">> COMIENZA EL PROGRAMA");
	}
	
}
