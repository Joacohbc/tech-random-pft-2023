package com.auth;

import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.Rol;


public class TokenWrapper {
	
	private String token;
	private UserDetails userInfo;

	public TokenWrapper() {
		this.token = null;
		this.userInfo = null;
	}
	
	public TokenWrapper(String token, UserDetails userInfo) {
		this.token = token;
		this.userInfo = userInfo;
	}
		
	public UserDetails getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserDetails userInfo) {
		this.userInfo = userInfo;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public boolean esTutor() {
 		return userInfo.getRol() == Rol.TUTOR;
	}
	
	public boolean esAnalista() {
 		return userInfo.getRol() == Rol.ANALISTA;
	}
	
	public boolean esEstudiante() {
 		return userInfo.getRol() == Rol.ESTUDIANTE;
	}
	
	public boolean es(Rol ...roles) {
		for (Rol rol : roles) {
			if(userInfo.getRol() == rol) return true;
		}
		return false;
	}
	
	public Rol getRol() {
		return userInfo.getRol();
	}
	
	public Class<? extends Usuario> getRolClass() {
		if(userInfo.getRol() == Rol.TUTOR)
			return Tutor.class;
		
		if(userInfo.getRol() == Rol.ESTUDIANTE)
			return Estudiante.class;
		
		return Analista.class;
	}
	
	public Long getIdUsuario() {
		return userInfo.getIdUsuario();
	}
	
	public Long getIdRol() {
		return userInfo.getIdRol();
	}
	
	public String getNombreUsuario() {
		return userInfo.getNombreUsuario();
	}

	@Override
	public String toString() {
		return "TokenWrapper [token=" + token + ", userInfo=" + userInfo + ", getUserInfo()=" + getUserInfo()
				+ ", getToken()=" + getToken() + ", esTutor()=" + esTutor() + ", esAnalista()=" + esAnalista()
				+ ", esEstudiante()=" + esEstudiante() + ", getRol()=" + getRol() + ", getIdUsuario()=" + getIdUsuario()
				+ ", getIdRol()=" + getIdRol() + ", getNombreUsuario()=" + getNombreUsuario() + "]";
	}
}
