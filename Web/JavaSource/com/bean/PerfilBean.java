package com.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Tutor;
import com.entities.Usuario;
import com.services.UsuarioBean;

import validation.ValidacionesUsuario;
import validation.ValidacionesUsuario.TipoUsuarioDocumento;
import validation.ValidacionesUsuarioEstudiante;
import validation.ValidacionesUsuarioTutor;
import validation.ValidationObject;

@Named("perfilBean")
@ViewScoped
public class PerfilBean implements Serializable {

	public PerfilBean() {

	}

	private Usuario usuario;

	@EJB
	private UsuarioBean usuariobean;

	@Inject
	private AuthJWTBean auth;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void init() {
		if (auth.esAnalista()) {
			usuario = usuariobean.findById(Analista.class, auth.getIdUsuario());
		} else {
			if (auth.esTutor()) {
				usuario = usuariobean.findById(Tutor.class, auth.getIdUsuario());
			} else {
				usuario = usuariobean.findById(Estudiante.class, auth.getIdUsuario());
			}
		}
	}
	
	public void guardarDatos() {
		try {
			if (auth.esAnalista()) {
				ValidationObject validation = ValidacionesUsuario.ValidarUsuarioSinContrasenia(usuario,TipoUsuarioDocumento.URUGUAYO);
				if (!validation.isValid()) {
					JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error", validation.getErrorMessage());
					return;
				}
				usuariobean.updateAnalista((Analista) usuario);
			} else if (auth.esTutor()) {
				ValidationObject validation = ValidacionesUsuarioTutor.validarTutorSinContrasenia((Tutor) usuario,TipoUsuarioDocumento.URUGUAYO);
				if (!validation.isValid()) {
					JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error", validation.getErrorMessage());
					return;
				}
				usuariobean.updateTutor((Tutor) usuario);
			} else {
				ValidationObject validation = ValidacionesUsuarioEstudiante.validarEstudianteSinContrasenia((Estudiante) usuario,TipoUsuarioDocumento.URUGUAYO);
				if (!validation.isValid()) {
					JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error", validation.getErrorMessage());
					return;
				}
				usuariobean.updateEstudiante((Estudiante) usuario);
			}
			
			JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Usuario modificado con exito", "");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
		}
	}

}
