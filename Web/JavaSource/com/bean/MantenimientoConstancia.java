package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.auth.AuthRenderedControl;
import com.entities.Constancia;
import com.entities.Usuario;
import com.entities.enums.Rol;
import com.services.ConstanciaBean;

public class MantenimientoConstancia implements Serializable, AuthRenderedControl {

	
	@EJB
	private ConstanciaBean bean;
	
	@Inject
	private AuthJWTBean auth;
	
	private List<Constancia> constancias;
	private List<Constancia> constanciasSelecionadas = new ArrayList<>();
	private Constancia constanciaSeleccionada;
	
	


	
	@Override
	public void checkUser() throws IOException {
		if(!auth.es(Rol.ANALISTA, Rol.TUTOR)) {
	        JSFUtils.redirect("/noAuth.xhtml");
		}
	}
}
