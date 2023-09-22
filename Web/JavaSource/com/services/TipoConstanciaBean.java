package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.daos.TipoConstanciaDAO;
import com.entities.Constancia;
import com.entities.TipoConstancia;
import com.exceptions.DAOException;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

import validation.Validaciones;
import validation.ValidacionesTipoConstancia;
import validation.ValidationObject;

/**
 * Session Bean implementation class TipoConstanciaBean
 */
@Stateless
public class TipoConstanciaBean implements TipoConstanciaBeanRemote {

	public TipoConstanciaBean() {
	}

	@EJB
	private TipoConstanciaDAO dao;

	@Override
	public TipoConstancia findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public TipoConstancia insert(TipoConstancia entity) {
		try {
			ServicesUtils.checkNull(entity, "Al actualizar un Tipo de Constancia el ID no puede ser nulo");
			
			if(entity.getIdTipoConstancia() != null)
				throw new NotFoundEntityException("Al registrar una nueva Tipo de Constancia, esta no puede tener un ID asignado");
			
			ValidationObject valid = ValidacionesTipoConstancia.validarTipoContancia(entity);
			if(!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());
				
			if(dao.findByTipo(entity.getTipo()) != null) 
				throw new InvalidEntityException("Ya existe un Tipo de Constancia con ese nombre");

			dao.insert(entity);
			return entity;
		}catch(DAOException e){
			throw new ServiceException(e);
		}
		
	}

	@Override
	public TipoConstancia update(TipoConstancia entity)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(entity, "Al actualizar un Tipo de Constancia el ID no puede ser nulo");
			
			TipoConstancia actual = dao.findById(entity.getIdTipoConstancia());
			if(actual == null)
				throw new NotFoundEntityException("No existe un Tipo Constancia con el ID: " + entity.getIdTipoConstancia());

			ValidationObject valid = ValidacionesTipoConstancia.validarTipoContancia(entity);
			if(!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());
				
			if(!actual.getTipo().equals(entity.getTipo())) {
				if(dao.findByTipo(entity.getTipo()) != null) 
					throw new InvalidEntityException("Ya existe un Tipo de Constancia con ese nombre");
			}
			
			entity.setEstado(actual.getEstado());
			entity = dao.update(entity);
			return entity;
		}catch(DAOException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public TipoConstancia eliminar(Long id) throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al registra un TipoConstancia el ID no puede ser nulo");
		
			TipoConstancia actual = dao.findById(id);
			if(actual == null)
				throw new NotFoundEntityException("No existe un TipoConstancia con el ID: " + id);
		
			actual.setEstado(false);
			actual = dao.update(actual);
			return actual;
		}catch(DAOException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public TipoConstancia reactivar(Long id) throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al registra un TipoConstancia el ID no puede ser nulo");
		
			TipoConstancia actual = dao.findById(id);
			if(actual == null)
				throw new NotFoundEntityException("No existe un Tipo de Constancia con el ID: " + id);
		
			actual.setEstado(true);
			actual = dao.update(actual);
			return actual;
		}catch(DAOException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public byte[] descargarPlantilla(Long id) throws ServiceException, NotFoundEntityException {
		return dao.findById(id).getPlantilla();
	}

	@Override
	public List<TipoConstancia> findAll() {
		return dao.findAll();
	}

}
