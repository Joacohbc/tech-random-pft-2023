package com.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.auth.TokenManagmentBean;
import com.auth.TokenWrapper;
import com.auth.UserDetails;
import com.entities.Usuario;
import com.entities.enums.Rol;

@SessionScoped
@Named("authBean")
public class AuthJWTBean implements Serializable {
	
	@EJB
	private TokenManagmentBean jwt;
	
	public AuthJWTBean() {}
		
	private TokenWrapper token;
	private Usuario user;
	
	public void generar(Long idUsuario, Long idRol, String nombreUsuario, Rol rol, Usuario user) {
		if(token != null) return;
		this.token = new TokenWrapper();
		token.setToken(jwt.generarToken(idUsuario, idRol, nombreUsuario, rol));
		token.setUserInfo(new UserDetails(idUsuario, idRol, nombreUsuario, rol));
		this.user = user;
	}
	
	public void renovar() {
		if(token == null) return;
		this.token = new TokenWrapper();
		token.setToken(jwt.generarToken(token.getIdUsuario(), token.getIdRol(), token.getNombreUsuario(), token.getRol()));
		token.setUserInfo(new UserDetails(token.getIdUsuario(), token.getIdRol(), token.getNombreUsuario(), token.getRol()));	
	}
	
	public boolean yaGenerado() {
		return token != null && !jwt.isTokenExpired(token.getToken());
	}
	
	public void borrar() {
		this.token = null;
		this.user = null;
	}
		
	public boolean esTutor() {
 		return !jwt.isTokenExpired(token.getToken()) ? token.esTutor() : null;
	}
	
	public boolean esAnalista() {
 		return !jwt.isTokenExpired(token.getToken()) ? token.esAnalista() : null;
	}
	
	public boolean esEstudiante() {
 		return !jwt.isTokenExpired(token.getToken()) ? token.esEstudiante() : null;
	}
	
	public boolean es(Rol ...roles) {
 		return !jwt.isTokenExpired(token.getToken()) ? token.es(roles) : null;
	}
	
	public Rol getRol() {
 		return !jwt.isTokenExpired(token.getToken()) ? token.getRol() : null;
	}
	
	public Long getIdUsuario() {
 		return !jwt.isTokenExpired(token.getToken()) ? token.getIdUsuario() : null;
	}
	
	public Long getIdRol() {
 		return !jwt.isTokenExpired(token.getToken()) ? token.getIdRol() : null;
	}
	
	public String getNombreUsuario() {
 		return !jwt.isTokenExpired(token.getToken()) ? token.getNombreUsuario() : null;
	}

	public Usuario getUser() {
		return user;
	}
}
