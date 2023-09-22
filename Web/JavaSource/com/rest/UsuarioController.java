package com.rest;

import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.auth.TokenWrapper;
import com.dto.JsonWrapper;
import com.entities.Usuario;
import com.services.UsuarioBean;

@Path("/users")
@Produces({ "application/json" })
public class UsuarioController {

	@Context
	private HttpServletRequest httpRequest;

	@EJB
	private UsuarioBean usuarioBean;
	
	@GET
	@Path("/me")
	public Map<String, Object> getMyInfo() {
		TokenWrapper tokenWrapper = (TokenWrapper) httpRequest.getAttribute("token");
		
		Usuario usuario = usuarioBean.findById(tokenWrapper.getRolClass(), tokenWrapper.getIdUsuario());
		
		return new JsonWrapper()
				.put("Nombre completo", usuario.getNombres() + " " + usuario.getApellidos())
				.put("Email UTEC", usuario.getEmailUtec())
				.put("Email Personal", usuario.getEmailPersonal())
				.put("Documento", usuario.getDocumento())
				.build();
	}
}
