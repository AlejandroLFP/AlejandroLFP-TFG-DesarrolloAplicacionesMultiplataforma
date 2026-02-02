package tfg.alfp.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfg.alfp.dao.impl.ObraDAOHibernate;
import tfg.alfp.vo.Obra;

@Service
public class ObraService {
	
	@Autowired
	private ObraDAOHibernate repositorio;

	public List<Obra> portada(String nombre) {
		List<Obra> listaObras = repositorio.buscarNombre(nombre);
		return listaObras;
	}
	
//	public Obra datos(String titulo) {
//		Obra obra = repositorio.obtenerObjeto(titulo);
//		return obra;
//	}

}
