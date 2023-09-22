package com.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.auth.AuthRenderedControl;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.Rol;
import com.services.UsuarioBean;

import validation.ValidacionesUsuario;
import validation.ValidacionesUsuario.TipoUsuarioDocumento;
import validation.ValidacionesUsuarioEstudiante;
import validation.ValidacionesUsuarioTutor;
import validation.ValidationObject;

@Named("temporalBean")
@ViewScoped
public class TemporalBean implements Serializable, AuthRenderedControl {

	public TemporalBean() {

	}

	@Inject
	private AuthJWTBean auth;
	
	@Override
	public void checkUser() throws IOException {
		if(!auth.es(Rol.ANALISTA)) {
			JSFUtils.redirect("/noAuth.xhtml");
		}
	}

}
