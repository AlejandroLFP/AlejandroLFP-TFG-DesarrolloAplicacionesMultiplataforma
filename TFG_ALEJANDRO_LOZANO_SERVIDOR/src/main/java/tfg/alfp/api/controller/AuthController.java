package tfg.alfp.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import tfg.alfp.vo.Usuario;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationManager authManager;
	
	public AuthController(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody Usuario usuario, HttpServletRequest peticion){
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUserName(), usuario.getPassword()));
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		peticion.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
		return ResponseEntity.ok().build();
	}

}
