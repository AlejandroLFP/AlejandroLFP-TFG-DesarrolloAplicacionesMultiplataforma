package tfg.alfp.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tfg.alfp.excpt.ChatException;
import tfg.alfp.vo.Mensaje;

@Repository
@Transactional
public class MensajeDAOHibernate {
	
	@PersistenceContext
	private EntityManager em;
	
	public boolean guardar(Mensaje mensajeN) {
		try {
			em.persist(mensajeN);
			return true;
		} catch (Exception e) {
			throw new ChatException(e.getMessage(), ChatException.ERROR_CREACION, "Fallo al crear entidad");
		}
	}	

}
