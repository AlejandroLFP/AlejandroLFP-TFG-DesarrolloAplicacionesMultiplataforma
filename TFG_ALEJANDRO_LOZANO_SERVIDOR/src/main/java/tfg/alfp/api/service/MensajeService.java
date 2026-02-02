package tfg.alfp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.alfp.dao.impl.MensajeDAOHibernate;
import tfg.alfp.vo.Mensaje;

@Service
public class MensajeService {
	
	@Autowired
	private MensajeDAOHibernate repositorio;
	
	public MensajeService(MensajeDAOHibernate repositorio) {
		this.repositorio = repositorio;
	}

	public void guardar(Mensaje mensaje) {
		repositorio.guardar(mensaje);
		
	}
}
