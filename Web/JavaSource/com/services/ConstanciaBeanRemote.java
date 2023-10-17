package com.services;

import java.util.List;

import javax.ejb.Remote;

import com.entities.AccionConstancia;
import com.entities.Constancia;
import com.entities.enums.EstadoSolicitudes;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

@Remote
public interface ConstanciaBeanRemote  {
	Constancia solicitar(Constancia entity) throws ServiceException, InvalidEntityException;
	Constancia findById(Long id);
	List<Constancia> findAll();
	Constancia update(Constancia entity) throws ServiceException, NotFoundEntityException, InvalidEntityException;
	Constancia updateEstado(Long id, EstadoSolicitudes estado, AccionConstancia accion) throws ServiceException, NotFoundEntityException, InvalidEntityException;
	Constancia eliminarConstancia(Long id) throws ServiceException, NotFoundEntityException;
	byte[] descargarConstancia(Long id) throws ServiceException, NotFoundEntityException, InvalidEntityException;
	List<Constancia> findByIdEstudiante(Long id);
}