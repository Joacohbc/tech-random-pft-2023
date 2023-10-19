package com.daos;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.entities.Reclamo;
import com.exceptions.DAOException;
import com.exceptions.NotFoundEntityException;

/**
 * Session Bean implementation class ReclamoDAO
 */
@Stateless
@LocalBean
public class ReclamoDAO {

	@PersistenceContext
	private EntityManager em;

    public ReclamoDAO() {
        // TODO Auto-generated constructor stub
    }
    
    public Reclamo insert(Reclamo entidad) throws DAOException{
    	try {
    		em.persist(entidad);
        	em.flush();
        	return entidad;
    	}catch(PersistenceException e) {
			throw new DAOException("Ocurrió un error al dar de alta un Reclamo: " + e.getMessage());	
    	}
    }
    public Reclamo findById(Long id) {
    	return em.find(Reclamo.class,id);
    }
    public List<Reclamo> findAll(){
    	return em.createQuery("Select c FROM Reclamo c",Reclamo.class).getResultList();
    }
    public List<Reclamo> findByFecha(LocalDateTime fechaHora){
    	return em.createQuery("SELECT c FROM Reclamo c WHERE c.fechaHora = ?1",Reclamo.class).setParameter(1, fechaHora).getResultList();
    }
    public List<Reclamo> findByEvento(Long idEvento){
    	return em.createQuery("SELECT c FROM Reclamo c WHERE c.evento.idEvento = ?1",Reclamo.class).setParameter(1, idEvento).getResultList();
    }
    public List<Reclamo> findByEstudiante(Long idEstudiante){
    	return em.createQuery("Select c FROM Reclamo c WHERE c.estudiante.idEstudiante = ?1",Reclamo.class).setParameter(1, idEstudiante).getResultList();
    }
    public Reclamo findUnique(Reclamo reclamo) {
    	try {
    		return em.createQuery("SELECT c FROM Reclamo c WHERE "
					+ "TRUNC(c.fechaHora) = TRUNC(?1)"
					+ "AND c.evento.idEvento = ?2 "
					+ "AND c.estudiante.idEstudiante = ?3",Reclamo.class)
    				.setParameter(1, reclamo.getFechaHora())
    				.setParameter(2, reclamo.getEvento().getIdEvento())
    				.setParameter(3, reclamo.getEstudiante().getIdEstudiante()).getSingleResult();
    		}catch(NoResultException e) {
    			return null;
    		
    	}
    }
    public Reclamo update(Reclamo entidad) {
    	try {
    		entidad = em.merge(entidad);
    		em.flush();
    		return entidad;
    		
    	}catch(Exception e) {
			throw new DAOException("Ocurrio un error al hacer el update del Evento ", e);    	}
    }
    public void remove(Reclamo entidad)throws DAOException, NotFoundEntityException {
    	try {
    		em.remove(entidad);
    		em.flush();
    		
    	}catch(Exception e) {
			throw new DAOException("Ocurrio un error al hacer al eliminar del Reclamo ", e);

    	}
    }
    
    
    

}
