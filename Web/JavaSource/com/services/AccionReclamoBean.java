package com.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.daos.AccionReclamoDAO;
import com.entities.AccionConstancia;
import com.entities.AccionReclamo;
import com.entities.Constancia;
import com.entities.Reclamo;
import com.exceptions.DAOException;
import com.exceptions.InvalidEntityException;
import com.exceptions.ServiceException;


/**
 * Session Bean implementation class AccionReclamoBean
 */
@Stateless
@LocalBean
public class AccionReclamoBean {

	@EJB
	private AccionReclamoDAO dao;
	
    public AccionReclamoBean() {
        // TODO Auto-generated constructor stub
    }
	private void validarAccionReclamo(AccionReclamo entity) throws InvalidEntityException {
		// TODO: Validar los campos
		// TODO: Validar que la Accion Reclamo no se repita
	}
	public AccionReclamo addAccionReclamo(AccionReclamo entity, Reclamo reclamo) throws ServiceException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(entity, "Al agregar una Accion a una Reclamo, ninguna de estas puede ser nula");
			ServicesUtils.checkNull(reclamo, "Al agregar una Accion a una Reclamo, ninguna de estas puede ser nula");

			if (entity.getIdAccionReclamo() != null)
				throw new InvalidEntityException("Al solictar una Accion Reclamo, esta no puede tener un ID asignado");
			
			// Setteo la fecha de creacion y el reclamoal a que pertenece
			// Convierto LocalDate a Date ya que ese es el tipo de dato que se utiliza en AccionReclamo
			entity.setFechaHora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
			entity.setReclamo(reclamo);
			validarAccionReclamo(entity);
			
			return dao.insert(entity);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
