package tfg.alfp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tfg.alfp.excpt.ChatException;
import tfg.alfp.vo.Chat;
import tfg.alfp.vo.Mensaje;

@Repository
@Transactional
public class ChatDAOHibernate {

	@PersistenceContext
	private EntityManager em;
	
    public List<Chat> listarChats(String titulo) {
        try {
            TypedQuery<Chat> query = em.createQuery("SELECT c FROM Chat c WHERE c.obra.titulo = :titulo", Chat.class);
            query.setParameter("titulo", titulo);
            return query.getResultList();
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_BUSQUEDA, "Error al obtener lista entidades");
        }
    }

	public boolean crearChat(Chat chatNuevo) {
		try {
            em.persist(chatNuevo);
            return true;
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_CREACION, "Fallo al crear entidad");
        }
	}

	public List<Mensaje> buscarMensajes(int chatId) {
		try {
            TypedQuery<Mensaje> query = em.createQuery("SELECT m FROM Mensaje m WHERE m.chat.id = :chatId", Mensaje.class);
            query.setParameter("chatId", chatId);
            return query.getResultList();
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_BUSQUEDA, "Error al obtener lista entidades");
        }
	}
}
