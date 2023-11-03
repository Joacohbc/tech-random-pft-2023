package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.auth.AuthRenderedControl;
import com.bean.DescargarContanciasBean.ConstanciaJSF;
import com.entities.Estudiante;
import com.entities.Evento;
import com.entities.Reclamo;

import com.entities.enums.Rol;
import com.services.EventoBean;
import com.services.ReclamoBean;
import com.services.UsuarioBean;

import validation.Formatos;

@Named("listadoReclamoBean")
@ViewScoped
public class ListadoReclamoBean implements Serializable, AuthRenderedControl {

    /**
     * Default constructor. 
     */
    public ListadoReclamoBean() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB
    private ReclamoBean reclamoBean;
    @EJB
    private EventoBean eventoBean;
    @EJB
    private UsuarioBean usuarioBean;
    
    
    
    @Inject
	private AuthJWTBean auth;
    
    private List<Evento> eventos;
	private Evento eventoSeleccionado;
	private String detalle;
	
	
	@PostConstruct
	public void init() {
		this.eventos = new ArrayList<>();
		eventos.addAll(eventoBean.findByEstudianteId(auth.getIdRol()));
	}
	
	public void ingresarReclamo() {
		if (!auth.esEstudiante())
			return;
		
        if (eventoSeleccionado == null) {
        	JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Ningún evento fue seleccionado.");
            return;
        }
        
        try {
        	Reclamo r = new Reclamo();
        	Estudiante est = usuarioBean.findById(Estudiante.class,auth.getIdUsuario());
        	r.setEstudiante(est);
        	r.setEvento(eventoSeleccionado);
        	r.setDetalle(detalle);
        	reclamoBean.solicitar(r);   	
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se ingresó correctamente el reclamo");
        } catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage());
		}
	}

	@Override
	public void checkUser() throws IOException {
		if (!auth.es(Rol.ESTUDIANTE)) {
			JSFUtils.redirect("/noAuth.xhtml");
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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	
    

}
