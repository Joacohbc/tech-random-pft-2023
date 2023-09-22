package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.auth.AuthRenderedControl;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Evento;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.Rol;
import com.services.ConstanciaBean;
import com.services.EventoBean;
import com.services.UsuarioBean;

@Named("listadoEventosBean")
@ViewScoped

public class ListadoEventos implements Serializable, AuthRenderedControl {

	@EJB
	private EventoBean eventoBean;

	@EJB
	private ConstanciaBean constanciaBean;

	@Inject
	private AuthJWTBean auth;

	private List<Evento> eventos;
	private List<Evento> eventosSeleccionados = new ArrayList<>();
	private Evento eventoSeleccionado;

	@PostConstruct
	public void init() {
		this.eventos = new ArrayList<>();
		eventos.addAll(eventoBean.findByEstudianteId(auth.getIdRol()));
	}

	
	
	
	
	
	
	
	
	
	
	
	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Evento> getEventosSeleccionados() {
		return eventosSeleccionados;
	}

	public void setEventosSeleccionados(List<Evento> eventosSeleccionados) {
		this.eventosSeleccionados = eventosSeleccionados;
	}

	public Evento getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(Evento eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	@Override
	public void checkUser() throws IOException {
		if (!auth.es(Rol.ESTUDIANTE)) {
			JSFUtils.redirect("/noAuth.xhtml");
		}
	}

}
