package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.daos.ReclamoDAO;
import com.entities.AccionReclamo;
import com.entities.Reclamo;
import com.entities.enums.EstadoReclamo;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

/**
 * Session Bean implementation class ReclamoBean
 */
@Stateless
@LocalBean
public class ReclamoBean implements ReclamoBeanRemote {

	@EJB
	private ReclamoDAO dao;
	
	@EJB
	private AccionReclamo arBean;
	
	
    public ReclamoBean() {
    }

	@Override
	public Reclamo solicitar(Reclamo entity) throws ServiceException, InvalidEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reclamo update(Reclamo entity) throws ServiceException, NotFoundEntityException, InvalidEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reclamo updateEstado(Long id, EstadoReclamo estado, AccionReclamo accion)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reclamo eliminar(Long id) throws ServiceException, NotFoundEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reclamo findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<Reclamo> findReclamoByIdEstudiante(Long id) {
		return dao.findByEstudiante(id);
	}

	@Override
	public List<Reclamo> findAll() {
		return dao.findAll();
	}

}
