package com.services;

import java.util.List;

import javax.ejb.Remote;

import com.entities.AccionConstancia;
import com.entities.AccionReclamo;
import com.entities.Constancia;
import com.entities.Reclamo;
import com.entities.enums.EstadoReclamo;
import com.entities.enums.EstadoSolicitudes;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

@Remote
public interface ReclamoBeanRemote {
	Reclamo solicitar(Reclamo entity)throws ServiceException, InvalidEntityException;
	Reclamo update(Reclamo entity)throws ServiceException, NotFoundEntityException, InvalidEntityException;
	Reclamo updateEstado(Long id, EstadoReclamo estado, AccionReclamo accion) throws ServiceException, NotFoundEntityException, InvalidEntityException;
	Reclamo eliminar(Long id)throws ServiceException, NotFoundEntityException;
	Reclamo findById(Long id);
	List<Reclamo> findReclamoByIdEstudiante(Long id);
	List<Reclamo> findAll();
	

}
