package com.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;

import com.daos.ReclamoDAO;
import com.entities.AccionReclamo;
import com.entities.Reclamo;
import com.entities.enums.EstadoReclamo;
import com.entities.enums.EstadoSolicitudes;
import com.exceptions.DAOException;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

import validation.ValidacionesReclamo;
import validation.ValidationObject;

/**
 * Session Bean implementation class ReclamoBean
 */
@Stateless
@LocalBean
public class ReclamoBean implements ReclamoBeanRemote {

	@EJB
	private ReclamoDAO dao;
	
	@EJB
	private AccionReclamoBean arBean;
	
	@EJB
	private MailBean mail;
	
	
    public ReclamoBean() {
    }

	@Override
	public Reclamo solicitar(Reclamo entity) throws ServiceException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(entity, "Al solictar un Reclamo, esta no puede ser nula");

			if (entity.getIdReclamo() != null)
				throw new InvalidEntityException("Al solictar un Reclamo, esta no puede tener un ID asignado");

			entity.setEstado(EstadoReclamo.INGRESADO);
			
			//si se genera algún error por la fecha hay que cambiar en la clase reclamo
			//el tipo de fehca de LocalDateTime al tipo Date 
			// Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()) 
			entity.setFechaHora(LocalDateTime.now());

			ValidationObject valid = ValidacionesReclamo.validarReclamo(entity);
			if (!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());

			if (dao.findUnique(entity) != null)
				throw new InvalidEntityException("Ya existe un reclamo con esos atributos");

			
			return dao.insert(entity);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Reclamo update(Reclamo entity) throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(entity, "Al actualizar un Reclamo, esta no puede ser nula");
			ServicesUtils.checkNull(entity.getIdReclamo(), "Al actualizar un Reclamo, esta debe tener un ID asignado");

			Reclamo actual = findById(entity.getIdReclamo());
			if (actual == null)
				throw new NotFoundEntityException("No existe un reclamo con el ID: " + entity.getIdReclamo());

			if (actual.getEstado() != EstadoReclamo.INGRESADO)
				throw new InvalidEntityException("No se puede modificar un reclamo que ya esta en proceso o finalizada");

			// La Fecha y Hora de emision y el Estado del reclamo no debe cambiado
			entity.setFechaHora(actual.getFechaHora());
			entity.setEstado(actual.getEstado());
			entity.setEstudiante(actual.getEstudiante());
			
			ValidationObject valid = ValidacionesReclamo.validarReclamo(entity);
			if (!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());
			
			// La fecha no se verifica ya que la fecha no cambia
			if (entity.getEvento().getIdEvento() != actual.getEvento().getIdEvento()) {
				
				if (dao.findUnique(entity) != null) 
					throw new InvalidEntityException("Ya existe un reclamo con esos atributos, mismo Estudiante, Evento, Fecha");
			}
			
			return dao.update(entity);			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Reclamo updateEstado(Long id, EstadoReclamo estadoNuevo, AccionReclamo accion)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar un reclamo, esta debe tener un ID asignado");

			Reclamo actual = findById(id);
			if (actual == null)
				throw new NotFoundEntityException("No existe un reclamo con el ID: " + id);

			if (actual.getEstado() == EstadoReclamo.FINALIZADO)
				throw new InvalidEntityException("No se puede modificar un reclamo que ya esta finalizada");

			// Agrego la accion reclamo al reclamo
			
			arBean.addAccionReclamo(accion, actual);

			// Si se finaliza el reclamo agrego la constnacia ya firmada para que el estudiante pueda generarla
			if(estadoNuevo == EstadoReclamo.FINALIZADO) {
				//actual.setArchivo(cargarPlantilla(id));
			}
			actual.setEstado(estadoNuevo);
			actual = dao.update(actual);

			String cuerpo = String.format("El reclamo al evento \"%s\" fue modificada de \"%s\" a \"%s\". Visite la aplicacion para mas informacion", 
					actual.getEvento().getTitulo(),
					actual.getEstado().toString(),
					estadoNuevo.toString());
				         
			mail.enviarConGMail(actual.getEstudiante().getEmailUtec(), "Cambio de estando en su reclamo", cuerpo);
			return actual;
			
			// Se cacha ServiceException porque se utiliza el arBean.addAccionReclamo()
		} catch (DAOException | ServiceException e) {
			throw new ServiceException(e);
		} catch (MessagingException e) {
			throw new ServiceException("El reclamo se actualizo exitosamente pero no se pudo notificar al estudiante");
		}
	}

	@Override
	public Reclamo eliminar(Long id) throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al registra un reclamo el ID no puede ser nulo");
			
			Reclamo actual = dao.findById(id);
			if(actual == null)
				throw new NotFoundEntityException("No existe un reclamo con el ID: " + id);
			
			if(!actual.getAccionReclamos().isEmpty()) {
				throw new ServiceException("No puede dar de baja un reclamo que ya se le aplicaron acciones");
			}
			
			dao.remove(actual);
			return actual;
		}catch (DAOException e) {
			throw new ServiceException(e);
		}
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
