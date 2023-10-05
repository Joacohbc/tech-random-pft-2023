package com.rest;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth.TokenWrapper;
import com.dto.JsonDTO;
import com.dto.UsuarioMapper;
import com.entities.Usuario;
import com.services.UsuarioBean;

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
		
		Usuario usuario = usuarioBean.findById(tokenWrapper.getRolClass(), tokenWrapper.getIdUsuario());
		
		return Response.ok(UsuarioMapper.toUsuarioDTO(usuario)).build();
	}
}
