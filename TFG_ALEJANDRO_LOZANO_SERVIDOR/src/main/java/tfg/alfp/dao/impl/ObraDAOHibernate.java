package tfg.alfp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tfg.alfp.excpt.ChatException;
import tfg.alfp.vo.Obra;

@Repository
@Transactional
public class ObraDAOHibernate {

   @PersistenceContext
   private EntityManager em;

//    public Obra obtenerObjeto(String titulo) {
//        try {
//            TypedQuery<Obra> query = em.createQuery("FROM Obra WHERE titulo LIKE :titulo", Obra.class);
//            query.setParameter("titulo", titulo);
//            return query.getSingleResult();
//        } catch (Exception e) {
//            throw new ChatException(e.getMessage(), ChatException.ERROR_BUSQUEDA, "Fallo al obtener entidad");
//        }
//    }

    public List<Obra> buscarNombre(String nombre) {
        try {
            TypedQuery<Obra> query = em.createQuery("FROM Obra WHERE titulo LIKE :nombre", Obra.class);
            query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            return query.getResultList();
        } catch (Exception e) {
            throw new ChatException(e.getMessage(), ChatException.ERROR_BUSQUEDA, "Error al obtener listar entidades");
        }
    }
}
