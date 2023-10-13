package com.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
	
	private String nombreUsuario;
	private String nombreCompleto;
	private Long idUsuario;
	private String nuevaContrasenia;
	private String confirmacionContrasenia;
	
	public void check() throws IOException {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
			String token = request.getParameter("token");
			if(token == "" || token == null) {
				JSFUtils.redirect("restablecerContraseniaFallida.xhtml");
				return;
			}
			
			if(tokenBean.isTokenExpired(token)) {
				JSFUtils.redirect("restablecerContraseniaFallida.xhtml");
				return;
			}
			
			Claims claims = tokenBean.getClaimsFromCustomToken(token);
			if(claims == null) {
				JSFUtils.redirect("restablecerContraseniaFallida.xhtml");
				return;
			}
			
			idUsuario = Long.valueOf((claims.get("id").toString()));
			nombreUsuario = (String) claims.get("nombreUsuario");
			nombreCompleto = (String) claims.get("nombres") + " " + (String) claims.get("apellidos");
		} catch (Exception e) {
			JSFUtils.redirect("restablecerContraseniaFallida.xhtml");
			return;
		}
	}
	
	public void restablecer() {
		try {
			usuarioBean.updateContrasenia(idUsuario, nuevaContrasenia);
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
}
