package com.daos;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.entities.AccionConstancia;
import com.entities.Constancia;
import com.exceptions.DAOException;
import com.exceptions.NotFoundEntityException;

/**
 * Session Bean implementation class AccionConstanciaDAO
 */
@Stateless
@LocalBean
public class AccionConstanciaDAO {
		
	@PersistenceContext
	private EntityManager em;

	public AccionConstanciaDAO() {
    }

	public AccionConstancia insert(AccionConstancia accion) throws DAOException {
		try {
			em.persist(accion);
			em.flush();
			return accion;
		} catch (PersistenceException e) {
			throw new DAOException("Ocurrió un error al dar de alta una Acción en Constancia: " + e.getMessage());
		}
	}
	
	public AccionConstancia findById(Long id) {
		return em.find(AccionConstancia.class, id);
	}

	public List<AccionConstancia> findAll() {
		return em.createQuery("Select ac FROM AccionConstancia ac", AccionConstancia.class).getResultList();

	}
}
