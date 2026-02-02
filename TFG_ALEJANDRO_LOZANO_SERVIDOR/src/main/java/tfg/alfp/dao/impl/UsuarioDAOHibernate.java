package tfg.alfp.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tfg.alfp.excpt.ChatException;
import tfg.alfp.vo.Usuario;

@Repository
@Transactional
public class UsuarioDAOHibernate {

    @PersistenceContext
    private EntityManager em;
  
    public boolean crear(Usuario entidad) {
        try {
            em.persist(entidad);
            return true;
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_CREACION, "Fallo al crear entidad");
        }
    }

    public Usuario obtenerUsuario(String name) {
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.userName = :name", Usuario.class);
            query.setParameter("name", name);
            Usuario usuario = query.getSingleResult();
            return usuario;
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_BUSQUEDA, "Fallo al obtener entidad");
        }
    }
}

