package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.auth.AuthRenderedControl;
import com.entities.Analista;
import com.entities.Constancia;
import com.entities.Estudiante;
import com.entities.Evento;
import com.entities.TipoConstancia;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.Rol;
import com.services.ConstanciaBean;
import com.services.EventoBean;
import com.services.TipoConstanciaBean;
import com.services.TipoConstanciaBeanRemote;
import com.services.UsuarioBean;

import validation.Formatos;

@Named("listadoEventosBean")
@ViewScoped
public class ListadoEventosBean implements Serializable, AuthRenderedControl {

	@EJB
	private EventoBean eventoBean;

	@EJB
	private ConstanciaBean constanciaBean;

	@EJB
	private TipoConstanciaBean tipoConstanciaBean;
	
	@EJB
	private UsuarioBean beanUsuario;

	@Inject
	private AuthJWTBean auth;
	
	private List<Evento> eventos;
	private Evento eventoSeleccionado;
	private List<TipoConstancia> tiposDeConstancias;
	private Long tiposDeConstanciaId;

	@PostConstruct
	public void init() {
		this.eventos = new ArrayList<>();
		eventos.addAll(eventoBean.findByEstudianteId(auth.getIdRol()));
		this.tiposDeConstancias = new ArrayList<TipoConstancia>();
		tiposDeConstancias.addAll(tipoConstanciaBean.findAll());

	}

	public void crearSolicitud() {
		if (!auth.esEstudiante())
			return;
		
        if (eventoSeleccionado == null) {
        	JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Ningún evento fue seleccionado.");
            return;
        }
        
        if(tiposDeConstanciaId == null) {
        	JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Ningún tipo de constancia fue seleccionado.");
            return;
        }
        
        try {
        	Constancia c = new Constancia();
        	Estudiante est = beanUsuario.findById(Estudiante.class,auth.getIdUsuario());
        	c.setEstudiante(est);    
        	c.setEvento(eventoSeleccionado);
        	c.setTipoConstancia(tipoConstanciaBean.findById(tiposDeConstanciaId));
        	c.setDetalle("Constancia solicitada: " + Formatos.ToFormatedString(LocalDate.now()));
        	constanciaBean.solicitar(c);        	
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se realizo la solictud correctamente");
        } catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage());
		}
	}
	

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}


	public Evento getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(Evento eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	public List<TipoConstancia> getTiposDeConstancias() {
		return tiposDeConstancias;
	}

	public void setTiposDeConstancias(List<TipoConstancia> tiposDeConstancias) {
		this.tiposDeConstancias = tiposDeConstancias;
	}
	
	public Long getTiposDeConstanciaId() {
		return tiposDeConstanciaId;
	}

	public void setTiposDeConstanciaId(Long tiposDeConstanciaId) {
		this.tiposDeConstanciaId = tiposDeConstanciaId;
	}

	@Override
	public void checkUser() throws IOException {
		if (!auth.es(Rol.ESTUDIANTE)) {
			JSFUtils.redirect("/noAuth.xhtml");
		}
	}

}
