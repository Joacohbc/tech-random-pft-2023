package com.daos;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.entities.Constancia;
import com.entities.TipoConstancia;
import com.exceptions.DAOException;
import com.exceptions.NotFoundEntityException;

/**
 * Session Bean implementation class TipoConstanciaDAO
 */
@Stateless
@LocalBean
public class TipoConstanciaDAO {
	
	@PersistenceContext
	private EntityManager em;


    /**
     * Default constructor. 
     */
    public TipoConstanciaDAO() {
    }
    
	public TipoConstancia insert(TipoConstancia entidad) throws DAOException {
		try {
			em.persist(entidad);
			em.flush();
			return entidad;
		} catch (PersistenceException e) {
			throw new DAOException("Ocurrió un error al dar de alta Evento: " + e.getMessage());
		}
	}

	public TipoConstancia findById(Long id) {
		return em.find(TipoConstancia.class, id);
	}

	public List<TipoConstancia> findAll() {
		return em.createQuery("Select i FROM TipoConstancia i", TipoConstancia.class).getResultList();
	}
	
	public TipoConstancia findByTipo(String tipo) {
		try {
			return em.createQuery("SELECT i FROM TipoConstancia i WHERE i.tipo = ?1", TipoConstancia.class)
					.setParameter(1, tipo)
					.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}

	public TipoConstancia update(TipoConstancia entidad) throws DAOException, NotFoundEntityException {
		try {
			entidad = em.merge(entidad);
			em.flush();
			return entidad;
		} catch (Exception e) {
			throw new DAOException("Ocurrió un error al modificar Tipo de Constancia ", e);
		}

	}
}
