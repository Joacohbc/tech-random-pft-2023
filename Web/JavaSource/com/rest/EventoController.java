package com.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth.TokenWrapper;
import com.dto.EventoDTO;
import com.dto.EventoMapper;
import com.services.EventoBean;
import com.services.ReclamoBean;

@Path("eventos")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class EventoController {
	
	@Context
	private HttpServletRequest httpRequest;
	
	@EJB
	private EventoBean eventoBean;
	
	@GET
	public Response getEventos() {
		try {
			TokenWrapper tokenWrapper = (TokenWrapper) httpRequest.getAttribute("token");

			List<EventoDTO> eventos = eventoBean.findByEstudianteId(tokenWrapper.getIdRol()).stream()
					.map(e -> EventoMapper.toEventoDTO(e))
					.collect(Collectors.toList());

			return Response.ok(eventos).build();
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
