package com.rest;

import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth.TokenManagmentBean;
import com.dto.EstudianteDTO;
import com.dto.EstudianteMapper;
import com.dto.JsonDTO;
import com.entities.Estudiante;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.Rol;
import com.exceptions.InvalidEntityException;
import com.services.UsuarioBean;

import validation.ValidacionesUsuario.TipoUsuarioDocumento;

@Path("")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class LoginController {
	
	@EJB
	private UsuarioBean bean;
	
	@EJB
	private TokenManagmentBean jwt;
	
	@POST
	@Path("/register")
	public Response register(EstudianteDTO dto) {	
		try {
			dto.setEstado(true);
			dto.setEstadoUsuario(EstadoUsuario.SIN_VALIDAR);
			
			Estudiante est = bean.register(EstudianteMapper.toEstudiante(dto), TipoUsuarioDocumento.URUGUAYO);
			return Response.ok(est, MediaType.APPLICATION_JSON).build();
		} catch (InvalidEntityException e) {
			return RESTUtils.error(Status.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@POST
	@Path("/login")
	public Response login(Map<String, String> body) {	
		try {
			String nombreUsuario = body.get("nombreUsuario");
			String contrasenia = body.get("contrasenia");
			Estudiante est = bean.login(nombreUsuario, contrasenia, Estudiante.class);
			String token = jwt.generarToken(est.getIdUsuario(), est.getIdEstudiante(), nombreUsuario, Rol.ESTUDIANTE);
			
			return Response.ok(new JsonDTO()
						.put("token", token)
						.build())
					.build();
		} catch (InvalidEntityException e) {
			return RESTUtils.error(Status.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
