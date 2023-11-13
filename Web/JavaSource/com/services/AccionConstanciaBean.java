package com.services;

import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.daos.AccionConstanciaDAO;
import com.entities.AccionConstancia;
import com.entities.Constancia;
import com.exceptions.DAOException;
import com.exceptions.InvalidEntityException;
import com.exceptions.ServiceException;

/**
 * Session Bean implementation class AccionConstanciaBean
 */
@Stateless
@LocalBean
public class AccionConstanciaBean {
	
	@EJB
	private AccionConstanciaDAO dao;

    public AccionConstanciaBean() {
    }

	private void validarAccionConstancia(AccionConstancia entity) throws InvalidEntityException {
		// TODO: Validar los campos
		// TODO: Validar que la Accion Constancia no se repita
	}
	
	public AccionConstancia addAccionConstancia(AccionConstancia entity, Constancia constancia) throws ServiceException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(entity, "Al agregar una Acción a Constancia, ninguna de estas puede ser nula");
			ServicesUtils.checkNull(constancia, "Al agregar una Acció a una Constancia, ninguna de estas puede ser nula");

			if (entity.getIdAccionConstancia() != null)
				throw new InvalidEntityException("Al solictar una Acción Constancia, esta no puede tener un ID asignado");
			
			// Setteo la fecha de creacion y la constancia al a que pertenece
			entity.setFechaHora(LocalDateTime.now());
			entity.setConstancia(constancia);

			validarAccionConstancia(entity);
			
			return dao.insert(entity);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
