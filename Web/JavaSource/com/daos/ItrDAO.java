package com.daos;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.entities.Itr;
import com.exceptions.DAOException;

/**
 * Session Bean implementation class ItrDAO
 */
@Stateless
@LocalBean
public class ItrDAO {

	@PersistenceContext
	private EntityManager em;

	public ItrDAO() {}

/**
 * Periste un Itr en la Base de datos y retorna la Entidad persistida.
 * @param itr
 * @return
 * @throws DAOException Si ocurre un error al persistir
 */
	public Itr insert(Itr itr) throws DAOException {
		try {
			em.persist(itr);
			em.flush();
			return itr;
		} catch (PersistenceException e) {
			throw new DAOException("Ocurrió un error al dar de alta Itr: " + e.getMessage());
		}
	}

	/**
	 * Realiza la busqueda del ITR con ese ID
	 * 
	 * @param El ID debe ser
	 * @return Si existe el ID retorna el ITR. Sino existe retorna null
	 */
	public Itr findById(Long id) {
		return em.find(Itr.class, id);
	}

	/*
	 * Retorna todos los ITR.
	 */
	public List<Itr> findAll() {
		return em.createQuery("Select i FROM Itr i", Itr.class).getResultList();
	}
	
	/**
	 * Se realiza el Update de la entidad luego de checkear que este managed (ID que exista)
	 * 
	 * @param ITR que se queire realizar el cambio
	 * @return El ITR luego de los cambios aplicados
	 * @throws DAOException Si ocurrio un error la dar realizar el Update
	 */
	public Itr update(Itr itr) throws DAOException {
		try {
			itr = em.merge(itr);
			em.flush();
			return itr;
		} catch (PersistenceException e) {
			throw new DAOException("Ocurrio un error al hacer el update del Itr ", e);
		}
	}

	public Itr findByName(String nombre) {
		try {
			return em.createQuery("SELECT i FROM Itr i WHERE i.nombre = ?1", Itr.class)
					.setParameter(1, nombre)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
}
