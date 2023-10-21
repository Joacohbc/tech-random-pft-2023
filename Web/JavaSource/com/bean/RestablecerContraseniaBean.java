package com.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;

import com.auth.TokenManagmentBean;
import com.services.UsuarioBean;

import io.jsonwebtoken.Claims;

@Named("restablecerContrasenia")
@ViewScoped
public class RestablecerContraseniaBean implements Serializable {

	@EJB
	private TokenManagmentBean tokenBean;
	
	@EJB
	private UsuarioBean usuarioBean;
	
	private Long id;
	private String token;
	private String nuevaContrasenia;
	private String confirmacionContrasenia;
	
	private Boolean validate(String token) throws IOException {
		if(token == "" || token == null) {
			JSFUtils.redirect("restablecerContraseniaFallida.xhtml");
			return false;
		}
		
		if(tokenBean.isTokenExpired(token)) {
			JSFUtils.redirect("restablecerContraseniaFallida.xhtml");
			return false;
		}
		
		return true;
	}
	
	public void check() throws IOException {
		// Esto es porque al darle a restablecer se reinicia y se pirede el Token de la request. 
		// Por tanto, si ya hay un token no volver a checkearlo
		if(this.token != null) return;
		
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
			String token = request.getParameter("token");
			validate(token);
			
			id = Long.parseLong(tokenBean.getTokenValue(token).toString()); 
			this.token = token;
		} catch (Exception e) {
			JSFUtils.redirect("restablecerContraseniaFallida.xhtml");
			return;
		}
	}
	
	public void restablecer() {
		try {	
			validate(token);
			
			usuarioBean.updateContrasenia(id, nuevaContrasenia);
			tokenBean.removeToken(token);
			JSFUtils.redirect("login.xhtml");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	public String getNuevaContrasenia() {
		return nuevaContrasenia;
	}

	public void setNuevaContrasenia(String nuevaContrasenia) {
		this.nuevaContrasenia = nuevaContrasenia;
	}

	public String getConfirmacionContrasenia() {
		return confirmacionContrasenia;
	}

	public void setConfirmacionContrasenia(String confirmacionContrasenia) {
		this.confirmacionContrasenia = confirmacionContrasenia;
	}
}
