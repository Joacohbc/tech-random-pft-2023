package com.daos;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.entities.Constancia;
import com.entities.Evento;
import com.exceptions.DAOException;
import com.exceptions.NotFoundEntityException;

/**
 * Session Bean implementation class Constancia
 */
@Stateless
@LocalBean
public class ConstanciaDAO {

	@PersistenceContext
	private EntityManager em;

	public ConstanciaDAO() {
	}

	public Constancia insert(Constancia entidad) throws DAOException {
		try {
			em.persist(entidad);
			em.flush();
			return entidad;
		} catch (PersistenceException e) {
			throw new DAOException("Ocurrió un error al dar de alta la Constancia: " + e.getMessage());
		}
	}

	public Constancia findById(Long id) {
		return em.find(Constancia.class, id);
	}

	public List<Constancia> findAll() {
		return em.createQuery("Select c FROM Constancia c", Constancia.class).getResultList();
	}

	public List<Constancia> findByFecha(LocalDateTime fechaHora){
		return em.createQuery("SELECT c FROM Constancia c WHERE c.fechaHora = ?1", Constancia.class)
				.setParameter(1, fechaHora)
				.getResultList();
	}
	
	public List<Constancia> findByEvento(Long idEvento){
		return em.createQuery("SELECT c FROM Constancia c WHERE c.evento.idEvento = ?1", Constancia.class)
				.setParameter(1, idEvento)
				.getResultList();
	}
	
	public List<Constancia> findByTipoConstancia(Long idTipoConstancia){
		return em.createQuery("SELECT c FROM Constancia c WHERE c.tipoConstancia.idTipoConstancia = ?1", Constancia.class)
				.setParameter(1, idTipoConstancia)
				.getResultList();
	}
	
	public List<Constancia> findByEstudiante(Long idEstudiante) {
		return em.createQuery("Select c FROM Constancia c.estudiante.idEstudiante = ?1", Constancia.class)
				.setParameter(1, idEstudiante)
				.getResultList();
	}
	
	
	public List<Constancia> sacarConstanciaByIdEstudiante(Long id) {
		return em.createQuery("Select c FROM Constancia c INNER JOIN c.tipoConstancia a where c.estudiante.idEstudiante = ?1", Constancia.class).setParameter(1, id).getResultList();
	}
	
	
	
	
	
	public Constancia findUnique(Constancia constancia) {
		try {
			return em.createQuery("SELECT c FROM Constancia c WHERE "
					+ "TRUNC(c.fechaHora) = TRUNC(?1)"
					+ "AND c.evento.idEvento = ?2 "
					+ "AND c.tipoConstancia.idTipoConstancia = ?3 "
					+ "AND c.estudiante.idEstudiante = ?4", Constancia.class)
			.setParameter(1, constancia.getFechaHora())
			.setParameter(2, constancia.getEvento().getIdEvento())
			.setParameter(3, constancia.getTipoConstancia().getIdTipoConstancia())
			.setParameter(4, constancia.getEstudiante().getIdEstudiante())
			.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	public Constancia update(Constancia entidad) throws DAOException, NotFoundEntityException {
		try {
			entidad = em.merge(entidad);
			em.flush();
			return entidad;
		} catch (Exception e) {
			throw new DAOException("Ocurrio un error al hacer el update de la Constancia ", e);
		}
	}

	public void remove(Constancia entidad) throws DAOException, NotFoundEntityException {
		try {
			em.remove(entidad);
			em.flush();
		} catch (Exception e) {
			throw new DAOException("Ocurrio un error al hacer al eliminar de la Constancia ", e);
		}
	}
}
