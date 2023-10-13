package com.daos;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.entities.AccionReclamo;
import com.exceptions.DAOException;

@Stateless
@LocalBean
public class AccionReclamoDAO {

	@PersistenceContext
	private EntityManager em;

	public AccionReclamoDAO() {
    }

	public AccionReclamo insert(AccionReclamo accion) throws DAOException {
		try {
			em.persist(accion);
			em.flush();
			return accion;
		} catch (PersistenceException e) {
			throw new DAOException("Ocurrió un error al dar de alta una Alta de una Accion en reclamo: " + e.getMessage());
		}
	}
	
	public AccionReclamo findById(Long id) {
		return em.find(AccionReclamo.class, id);
	}

	public List<AccionReclamo> findAll() {
		return em.createQuery("Select ac FROM AccionReclamo ac", AccionReclamo.class).getResultList();

	}
	
	
}
