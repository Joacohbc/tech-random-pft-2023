package com.services;

import java.util.List;

import javax.ejb.Remote;

import com.entities.TipoConstancia;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

@Remote
public interface TipoConstanciaBeanRemote {
	TipoConstancia findById(Long id);
	List<TipoConstancia> findAll();
	TipoConstancia insert(TipoConstancia entity) throws ServiceException, InvalidEntityException;
	TipoConstancia update(TipoConstancia entity) throws ServiceException, NotFoundEntityException, InvalidEntityException;
	TipoConstancia eliminar(Long id) throws ServiceException, NotFoundEntityException;
	TipoConstancia reactivar(Long id) throws ServiceException, NotFoundEntityException;

	byte[] descargarPlantilla(Long id) throws ServiceException, NotFoundEntityException;


}
