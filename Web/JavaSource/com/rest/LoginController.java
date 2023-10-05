package com.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dto.UsuarioDTO;

@Path("")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class LoginController {
		
	@POST
	@Path("/login")
	public Response login(UsuarioDTO dto) {	
		return Response.ok(dto, MediaType.APPLICATION_JSON).build();
	}
}
