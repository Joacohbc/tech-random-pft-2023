package com.rest;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth.TokenWrapper;
import com.dto.EstudianteDTO;
import com.dto.EstudianteMapper;
import com.dto.JsonDTO;
import com.dto.ReclamoMapper;
import com.dto.UsuarioMapper;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Reclamo;
import com.entities.Tutor;
import com.entities.Usuario;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.services.UsuarioBean;

import validation.ValidacionesUsuario;
import validation.ValidacionesUsuarioEstudiante;
import validation.ValidacionesUsuarioTutor;
import validation.ValidationObject;
import validation.ValidacionesUsuario.TipoUsuarioDocumento;

@Path("/users")
@Produces({ MediaType.APPLICATION_JSON })
public class UsuarioController {

	@Context
	private HttpServletRequest httpRequest;

	@EJB
	private UsuarioBean usuarioBean;
	
	@GET
	@Path("/me")
	public Response getMyInfo() {
		TokenWrapper tokenWrapper = (TokenWrapper) httpRequest.getAttribute("token");
		
		Estudiante usuarioActual = usuarioBean.findById(Estudiante.class, tokenWrapper.getIdUsuario());
		
		return Response.ok(EstudianteMapper.toEstudianteDTO(usuarioActual)).build();
	}
	
	@PUT
	@Path("/me")
	public Response updateMyInfo(EstudianteDTO usuarioActualizado) {
		try {
			TokenWrapper tokenWrapper = (TokenWrapper) httpRequest.getAttribute("token");
			
			Estudiante usuarioActual = usuarioBean.findById(Estudiante.class, tokenWrapper.getIdUsuario());
			usuarioActual.setNombres(usuarioActualizado.getNombres());
			usuarioActual.setApellidos(usuarioActualizado.getApellidos());
			usuarioActual.setEmailPersonal(usuarioActualizado.getEmailPersonal());
			usuarioActual.setTelefono(usuarioActualizado.getTelefono());
			usuarioActual.setFecNacimiento(usuarioActualizado.getFecNacimiento());
			usuarioActual.setGenero(usuarioActualizado.getGenero());
			usuarioActual.setDepartamento(usuarioActualizado.getDepartamento());
			
			ValidationObject error = ValidacionesUsuarioEstudiante.validarEstudianteSinContrasenia(usuarioActual, TipoUsuarioDocumento.URUGUAYO);
			if (!error.isValid())
				throw new InvalidEntityException(error.getErrorMessage());
			
			
			System.out.println(">>>>>>>>>> AQUI LLEGUE");
			EstudianteDTO dto = EstudianteMapper.toEstudianteDTO(usuarioBean.updateEstudiante(usuarioActual));
			return Response.ok(dto).build();
		} catch (InvalidEntityException | NotFoundEntityException e) {
			return RESTUtils.error(Status.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
