package tfg.alfp.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tfg.alfp.dao.impl.UsuarioDAOHibernate;
import tfg.alfp.vo.Usuario;

@Service
public class UsuarioDetallesService implements UserDetailsService {
	
	private final UsuarioDAOHibernate repositorio;
	
	public UsuarioDetallesService(UsuarioDAOHibernate repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usuario usuario = repositorio.obtenerUsuario(userName);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return User.builder().username(usuario.getUserName()).password(usuario.getPassword()).roles("USER").build();
	}

}
