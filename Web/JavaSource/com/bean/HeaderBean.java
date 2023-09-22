package com.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("headerBean")
@ViewScoped
public class HeaderBean implements Serializable{
	
	@Inject
	private AuthJWTBean auth;
	
	public String logout() {
		auth.borrar();
		return "login.xhtml";
	}
	
	public String getNombreCompleto() {
		return auth.getUser().getNombres() + " " + auth.getUser().getApellidos();
	}
	
	public String getIniciales() {
		return String.valueOf(auth.getUser().getNombres().charAt(0)).toUpperCase() + String.valueOf(auth.getUser().getApellidos().charAt(0)).toUpperCase();
	}
	
	public String getRol() {
		return auth.getRol().toString();
	}
	
	public void verPerfil() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("perfil.xhtml");
	}
}
