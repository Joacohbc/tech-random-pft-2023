package com.auth;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import com.entities.enums.Rol;

import io.jsonwebtoken.Claims;

@Singleton
@LocalBean
public class TokenManagmentBean {

    public TokenManagmentBean() {}
    
	@EJB
	private JWTUtils jwt;
	
	public String generarCustomToken(Map<String, Object> claims, String subject, Long duration) {
		return jwt.doGenerateToken(claims, subject, duration);
	}
	
	public Claims getClaimsFromCustomToken(String token) {
		try {
			return jwt.getAllClaimsFromToken(token);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String generarToken(Long idUsuario, Long idRol, String nombreUsuario, Rol rol) {
		UserDetails userInfo = new UserDetails();
		userInfo.setIdUsuario(idUsuario);
		userInfo.setIdRol(idRol);
		userInfo.setNombreUsuario(nombreUsuario);
		userInfo.setRol(rol);
		return jwt.generateToken(userInfo);
	}
	
	public boolean isTokenExpired(String token) {
		if(token == null || token.isBlank()) return false;
		return jwt.isTokenExpired(token);
	}
	
	public UserDetails getTokenInfo(String token) {
		if(token == null || token.isBlank()) return null;
		
		try {
			return jwt.getUserDetails(token);
		} catch (Exception e) {
			return null;
		}
	}

}
