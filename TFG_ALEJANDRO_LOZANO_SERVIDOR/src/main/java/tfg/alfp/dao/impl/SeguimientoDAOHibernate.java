package tfg.alfp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tfg.alfp.excpt.ChatException;
import tfg.alfp.vo.Chat;
import tfg.alfp.vo.Seguimiento;

@Repository
@Transactional
public class SeguimientoDAOHibernate {

	@PersistenceContext
	private EntityManager em;

	public boolean agregar(Seguimiento seguido) {
		try {
            em.persist(seguido);
            return true;
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_CREACION, "Fallo al crear entidad");
        }
	}

	public boolean eliminar(Seguimiento seguido) {
		try {
            em.remove(seguido);
            return true;
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_CREACION, "Fallo al crear entidad");
        }
	}

	public List<Chat> listar(int usuarioId) {
        try {
            TypedQuery<Chat> query = em.createQuery("SELECT s.chat FROM Seguimiento s WHERE s.usuario.id = :usuarioId", Chat.class);
            query.setParameter("usuarioId", usuarioId);
            return query.getResultList();
        } catch (Exception e) {
        	throw new ChatException(e.getMessage(), ChatException.ERROR_BUSQUEDA, "Fallo al obtener entidad");
        }
	}
}
