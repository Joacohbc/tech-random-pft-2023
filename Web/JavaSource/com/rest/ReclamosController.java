package com.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth.TokenWrapper;
import com.dto.EstudianteDTO;
import com.dto.EstudianteMapper;
import com.dto.ReclamoDTO;
import com.dto.ReclamoMapper;
import com.entities.Estudiante;
import com.entities.Reclamo;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.services.ReclamoBean;
import com.services.UsuarioBean;

@Path("reclamos")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ReclamosController {
	
	@Context
	private HttpServletRequest httpRequest;
	
	@EJB
	private ReclamoBean reclamoBean;
	
	@EJB
	private UsuarioBean usuarioBean;
	
	@GET
	@Path("/mios")
	public Response getReclamos() {
		try {
			TokenWrapper tokenWrapper = (TokenWrapper) httpRequest.getAttribute("token");

			
			List<ReclamoDTO> reclamos = reclamoBean.findReclamoByIdEstudiante(tokenWrapper.getIdRol()).stream()
					.map(r -> ReclamoMapper.toReclamoDTO(r))
					.collect(Collectors.toList());
			
			return Response.ok(reclamos).build();
			
		} catch (InvalidEntityException | NotFoundEntityException e) {
			return RESTUtils.error(Status.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@POST
	public Response crearReclamo(ReclamoDTO reclamo) {
		try {
			TokenWrapper tokenWrapper = (TokenWrapper) httpRequest.getAttribute("token");
			EstudianteDTO usuarioActual = EstudianteMapper.toEstudianteDTO(usuarioBean.findById(Estudiante.class, tokenWrapper.getIdUsuario()));
			reclamo.setEstudiante(usuarioActual);
			
			Reclamo r = reclamoBean.solicitar(ReclamoMapper.toReclamo(reclamo));
			
			return Response.ok(ReclamoMapper.toReclamoDTO(r)).build();
			
		} catch (InvalidEntityException | NotFoundEntityException e) {
			return RESTUtils.error(Status.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@PUT
	public Response actualizarReclamo(ReclamoDTO reclamo) {
		try {
			TokenWrapper tokenWrapper = (TokenWrapper) httpRequest.getAttribute("token");
			EstudianteDTO usuarioActual = EstudianteMapper.toEstudianteDTO(usuarioBean.findById(Estudiante.class, tokenWrapper.getIdUsuario()));
			reclamo.setEstudiante(usuarioActual);
			
			Reclamo r = reclamoBean.update(ReclamoMapper.toReclamo(reclamo));
			
			return Response.ok(ReclamoMapper.toReclamoDTO(r)).build();
		
		} catch (InvalidEntityException | NotFoundEntityException e) {
			return RESTUtils.error(Status.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response borrarReclamo(@PathParam("id") Long id) {
		try {
			Reclamo r = reclamoBean.eliminar(id);
			return Response.ok(ReclamoMapper.toReclamoDTO(r)).build();
		
		} catch (InvalidEntityException | NotFoundEntityException e) {
			return RESTUtils.error(Status.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
