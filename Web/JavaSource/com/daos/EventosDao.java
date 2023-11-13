package com.daos;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.entities.Evento;
import com.entities.Itr;
import com.entities.Usuario;
import com.exceptions.DAOException;

/**
 * Session Bean implementation class EventosDao
 */
@Stateless
@LocalBean
public class EventosDao {

	@PersistenceContext
	private EntityManager em;

	
    public EventosDao() {
        // TODO Auto-generated constructor stub
    }
    
    
	/**
	 * Periste un Evento en la Base de datos y retorna la Entidad persistida.
	 * 
	 * @exception DAOException Si ocurrio un error al realizar el Perist()
	 */
    
	public <T extends Evento> T insert(T evento) throws DAOException {
		try {
			em.persist(evento);
			em.flush();
			return evento;
		} catch (PersistenceException e) {
			throw new DAOException("Ocurrió un error al dar de alta Usuario: " + e.getMessage());
		}
	}
    
	/**
	 * Realiza la busqueda del Evento con ese ID
	 * 
	 * @param El ID debe ser
	 * @return Si existe el ID retorna el Evento. Sino existe retorna null
	 */
	
	public Evento findById(Long id) {
		return em.find(Evento.class, id);
	}	
	
	
	/*
	 * Retorna todos los Eventos.
	 */
    
	public List<Evento> findAll() {
		return em.createQuery("Select e FROM Eventos e", Evento.class).getResultList();
	}
    
	/*
	 * Retorno todos los Eventos asociados a un ID de Usuario. 
	 */
	
	public List<Evento> findByEstudianteId(Long id) {
		return em.createQuery("Select e FROM Evento e INNER JOIN e.asistencias a where a.estudiante.idEstudiante = ?1", Evento.class).setParameter(1, id).getResultList();
	}
	
	
	
	
	
}
