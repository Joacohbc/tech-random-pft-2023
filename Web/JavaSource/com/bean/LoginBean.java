package com.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Tutor;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.Rol;
import com.services.UsuarioBean;

@Named("loginBean")
@ViewScoped
public class LoginBean implements Serializable { 

	@EJB
	private UsuarioBean user;
	
	@Inject
	private EnumJSF enums;
	
	@Inject
	private AuthJWTBean auth;
	
	private String nombreUsuario;
	private String contrasenia;
	private Rol rol;
	
	public LoginBean() {}

	public void restablecerContrasenia() {
		user.olvideContrasenia(nombreUsuario);
	}
	
	public void login() {
		try {
			auth.borrar();
			if(rol == Rol.ANALISTA) {
				Analista usu = user.login(nombreUsuario, contrasenia, Analista.class);
				auth.generar(usu.getIdUsuario(), usu.getIdAnalista(), nombreUsuario, rol, usu);
			} else if(rol == Rol.ESTUDIANTE) {
				Estudiante usu = user.login(nombreUsuario, contrasenia, Estudiante.class);
				auth.generar(usu.getIdUsuario(), usu.getIdEstudiante(), nombreUsuario, rol, usu);
			} else if(rol == Rol.TUTOR) {
				Tutor usu = user.login(nombreUsuario, contrasenia, Tutor.class);
				auth.generar(usu.getIdUsuario(), usu.getIdTutor(), nombreUsuario, rol, usu);
			}

			JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Incio de sesion exitoso", "");
			FacesContext.getCurrentInstance().getExternalContext().redirect("inicio.xhtml");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al inicar sesion:", e.getMessage());
		}
	}
	
	public void register() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
	}
	
	public void info() {
		if(!auth.yaGenerado()) return;
		JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Informacion de Usuario:", auth.getRol().toString() + " " + auth.getNombreUsuario() + " " + auth.getIdUsuario() + " " + auth.getIdRol());
	}
    
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}
