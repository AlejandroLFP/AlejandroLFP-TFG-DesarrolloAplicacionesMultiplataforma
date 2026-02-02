package tfg.alfp.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.alfp.dao.impl.SeguimientoDAOHibernate;
import tfg.alfp.vo.Chat;
import tfg.alfp.vo.Seguimiento;

@Service
public class SeguimientoService {
	
	@Autowired
	private SeguimientoDAOHibernate repositorio;

	public boolean agregar(Seguimiento seguido) {
		return repositorio.agregar(seguido);
	}

	public boolean eliminar(Seguimiento seguido) {
		return repositorio.eliminar(seguido);
	}

	public List<Chat> listar(int usuarioId) {
		List<Chat> chats = repositorio.listar(usuarioId);
		return chats;
	}

}
