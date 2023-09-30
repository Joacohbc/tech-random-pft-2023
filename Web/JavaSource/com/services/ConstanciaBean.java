package com.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;

import com.daos.ConstanciaDAO;
import com.entities.AccionConstancia;
import com.entities.Constancia;
import com.entities.enums.EstadoSolicitudes;
import com.exceptions.DAOException;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import validation.ValidacionesConstancia;
import validation.ValidationObject;

/**
 * Session Bean implementation class ConstanciaBean
 */
@Stateless
@LocalBean
public class ConstanciaBean implements ConstanciaBeanRemote {

	@EJB
	private ConstanciaDAO dao;

	@EJB
	private AccionConstanciaBean acBean;

	@EJB
	private MailBean mail;
	
	public ConstanciaBean() {
	}

	@Override
	public Constancia solicitar(Constancia entity) throws ServiceException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(entity, "Al solictar una Constancia, esta no puede ser nula");

			if (entity.getIdConstancia() != null)
				throw new InvalidEntityException("Al solictar una Constancia, esta no puede tener un ID asignado");

			entity.setEstado(EstadoSolicitudes.INGRESADO);
			entity.setFechaHora(LocalDateTime.now());

			ValidationObject valid = ValidacionesConstancia.validarConstancia(entity);
			if (!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());

			if (dao.findUnique(entity) != null)
				throw new InvalidEntityException("Ya existe una Contancia con esos atributos");

			
			return dao.insert(entity);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Constancia update(Constancia entity)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(entity, "Al actualizar una Constancia, esta no puede ser nula");
			ServicesUtils.checkNull(entity.getIdConstancia(), "Al actualizar una Constancia, esta debe tener un ID asignado");

			Constancia actual = findById(entity.getIdConstancia());
			if (actual == null)
				throw new NotFoundEntityException("No existe una constancia con el ID: " + entity.getIdConstancia());

			if (actual.getEstado() != EstadoSolicitudes.INGRESADO)
				throw new InvalidEntityException("No se puede modificar una constancia que ya esta en proceso o finalizada");

			// La Fecha y Hora de emision y el Estado de la constancia no debe cambiado
			entity.setFechaHora(actual.getFechaHora());
			entity.setEstado(actual.getEstado());
			entity.setEstudiante(actual.getEstudiante());
			
			ValidationObject valid = ValidacionesConstancia.validarConstancia(entity);
			if (!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());
			
			// La fecha no se verifica ya que la fecha no cambia
			if (entity.getEvento().getIdEvento() != actual.getEvento().getIdEvento()
					|| entity.getTipoConstancia().getIdTipoConstancia() != actual.getTipoConstancia().getIdTipoConstancia()) {
				
				if (dao.findUnique(entity) != null) 
					throw new InvalidEntityException("Ya existe una Contancia con esos atributos, mismo Estudiante, Evento, Fecha y Tipo de Constancia");
			}
			
			return dao.update(entity);			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Constancia findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<Constancia> findAll() {
		return dao.findAll();
	}

	@Override
	public Constancia updateEstado(Long id, EstadoSolicitudes estadoNuevo, AccionConstancia accion)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar una Constancia, esta debe tener un ID asignado");

			Constancia actual = findById(id);
			if (actual == null)
				throw new NotFoundEntityException("No existe una constancia con el ID: " + id);

			if (actual.getEstado() == EstadoSolicitudes.FINALIZADO)
				throw new InvalidEntityException("No se puede modificar una constancia que ya esta finalizada");

			// Agrego la accion constancia a la Constancia
			acBean.addAccionConstancia(accion, actual);

			// Cambio el estado de la constancia
			if(estadoNuevo == EstadoSolicitudes.FINALIZADO) 
				throw new InvalidEntityException("Cuando se actualiza el estado de la constancia a Finalizado se debe agregar el archivo descargable para el estudiante");

			actual.setEstado(estadoNuevo);
			actual = dao.update(actual);

			String cuerpo = String.format("La Constancia de tipo \"%s\" al evento \"%s\" fue modificada de \"%s\" a \"%s\". Visite la aplicacion para mas informacion", 
					actual.getTipoConstancia().getTipo(), 
					actual.getEvento().getTitulo(),
					actual.getEstado().toString(),
					estadoNuevo.toString());
				         
			mail.enviarConGMail(actual.getEstudiante().getEmailUtec(), "Cambio de estando en su Constancia", cuerpo);
			return actual;
			
			// Se cacha ServiceException porque se utiliza el acBean.addAccionConstancia()
		} catch (DAOException | ServiceException e) {
			throw new ServiceException(e);
		} catch (MessagingException e) {
			throw new ServiceException("La constancia se actualizo exitosamente pero no se pudo notificar al estudiante");
		}
	}


	@Override
	public Constancia updateEstado(Long id, EstadoSolicitudes estadoNuevo, AccionConstancia accion, byte[] archivo)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar una Constancia, esta debe tener un ID asignado");

			Constancia actual = findById(id);
			if (actual == null)
				throw new NotFoundEntityException("No existe una constancia con el ID: " + id);

			if (actual.getEstado() == EstadoSolicitudes.FINALIZADO)
				throw new InvalidEntityException("No se puede modificar una constancia que ya esta finalizada");
			
			if(archivo == null)
				throw new InvalidEntityException("Cuando se carga el la constancia al Estudiante el archivo no puede ser vacio");

			// Agrego la accion constancia a la Constancia
			acBean.addAccionConstancia(accion, actual);
			
			// Cambio el estado de la constancia y cargo el Estado
			EstadoSolicitudes estadoActual = actual.getEstado();
			actual.setEstado(estadoNuevo);
			actual.setArchivo(archivo);
			actual = dao.update(actual);
			
			String cuerpo = String.format("La Constancia de tipo \"%s\" al evento \"%s\" fue modificada de \"%s\" a \"%s\". Visite la aplicacion para mas informacion", 
					actual.getTipoConstancia().getTipo(), 
					actual.getEvento().getTitulo(),
					estadoActual.toString(),
					estadoNuevo.toString());
				         
			mail.enviarConGMail(actual.getEstudiante().getEmailUtec(), "Cambio de estando en su Constancia", cuerpo);
			return actual;
			
			// Se cacha ServiceException porque se utiliza el acBean.addAccionConstancia()
		} catch (DAOException | ServiceException e) {
			throw new ServiceException(e);
		} catch (MessagingException e) {
			throw new ServiceException("La constancia se actualizo exitosamente pero no se pudo notificar al estudiante");
		}
	}

	
	@Override
	public byte[] descargarConstancia(Long id) throws ServiceException, NotFoundEntityException, InvalidEntityException {
		ServicesUtils.checkNull(id, "Al registra una Constancia el ID no puede ser nulo");
		
		Constancia actual = dao.findById(id);
		if(actual == null)
			throw new NotFoundEntityException("No existe una Constancia con el ID: " + id);

		if(actual.getEstado() != EstadoSolicitudes.FINALIZADO) 
			throw new InvalidEntityException("La solicitud de constancia no se puede descargar hasta que este finalizada");
		
		return actual.getArchivo();
	}

	@Override
	public Constancia eliminarConstancia(Long id) throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al registra una Constancia el ID no puede ser nulo");
			
			Constancia actual = dao.findById(id);
			if(actual == null)
				throw new NotFoundEntityException("No existe una Constancia con el ID: " + id);
			
			if(!actual.getAccionConstancias().isEmpty()) {
				throw new ServiceException("No puede dar de baja una Constancia que ya se le aplicaron acciones");
			}
			
			dao.remove(actual);
			return actual;
		}catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Constancia> sacarConstanciaByIdEstudiante(Long id){
		return dao.sacarConstanciaByIdEstudiante(id);
	}
	
	
	public byte[] generarPlantilla(String tituloText, String parrafoTexto, String parrafo2Texto, Integer espacio, byte[] plantilla) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// Creo el documento
			Document documento = new Document();

			// Incio el Writter (Que se encarga de crear el PDF)
			PdfWriter writer = PdfWriter.getInstance(documento, baos);

			documento.open();

			// Agrego el Titulo
			Paragraph titulo = new Paragraph(tituloText);
			titulo.setAlignment(1);
			documento.add(titulo);

			// Agrego el espaciado entr el Titulo y el contenido
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);
			documento.add(Chunk.NEWLINE);

			// Creo la imagen en base a la plantilla
			Image image = Image.getInstance(plantilla);
			image.scaleAbsoluteHeight(PageSize.A4.getHeight());
			image.scaleAbsoluteWidth(PageSize.A4.getWidth());
			image.setAbsolutePosition(0, 0);

			// Agrego la Imagen al documento
			writer.getDirectContentUnder().addImage(image);

			// Genero el primer parrafo
			Paragraph parrafo1 = new Paragraph(parrafoTexto);
			parrafo1.setAlignment(Element.ALIGN_JUSTIFIED);
			documento.add(parrafo1);

			// Agrego la el espaciado entre el primer parrafo y el segundo
			for (int i = 0; i < espacio; i++) {
				documento.add(Chunk.NEWLINE);
			}

			// Genero el segundo parrafo
			Paragraph parrafo2 = new Paragraph(parrafo2Texto);
			parrafo1.setAlignment(Element.ALIGN_JUSTIFIED);
			documento.add(parrafo2);

			// Genero el archivo
			documento.close();

			return baos.toByteArray();

		} catch (IOException | DocumentException e) {
			throw new ServiceException(e);

		}
	}
	
}