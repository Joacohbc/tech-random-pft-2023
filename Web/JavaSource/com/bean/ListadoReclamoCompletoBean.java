package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.auth.AuthRenderedControl;
import com.entities.Estudiante;
import com.entities.Evento;
import com.entities.Reclamo;
import com.entities.enums.EstadoReclamo;
import com.entities.enums.Rol;
import com.services.EventoBean;
import com.services.ReclamoBean;
import com.services.UsuarioBean;

import validation.Formatos;

@Named("listadoReclamoCompletoBean")
@ViewScoped
public class ListadoReclamoCompletoBean implements Serializable, AuthRenderedControl {

    /**
     * Default constructor. 
     */
    public ListadoReclamoCompletoBean() {
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
	
	private List<ReclamoJSF> reclamos;
	private ReclamoJSF reclamoSeleccionado;
	
	
	@PostConstruct
	public void init() {
		this.eventos = new ArrayList<>();
		eventos.addAll(eventoBean.findByEstudianteId(auth.getIdRol()));
		reclamos = reclamoBean.findReclamoByIdEstudiante(auth.getIdRol()).stream().map(c -> new ReclamoJSF(c)).collect(Collectors.toList());
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
	
	public void eliminar( ) {
		try {
			reclamoBean.eliminar(reclamoSeleccionado.getIdReclamo());
			reclamos = reclamos.stream().filter(c -> c.getIdReclamo() != reclamoSeleccionado.getIdReclamo()).collect(Collectors.toList());
			
			PrimeFaces.current().ajax().update("form:listaReclamos");
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se eliminó el reclamo con éxito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	public void editar() {
		try {
			
			
			reclamoBean.update(reclamoSeleccionado.toEntity());
			PrimeFaces.current().ajax().update("form:listaReclamos");
			PrimeFaces.current().ajax().update("PF('editarReclamoDialog').hide()");
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se actualizó el reclamo con éxito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
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
	
	public List<ReclamoJSF> getReclamos() {
		return reclamos;
	}

	public void setReclamos(List<ReclamoJSF> reclamos) {
		this.reclamos = reclamos;
	}

	public ReclamoJSF getReclamoSeleccionado() {
		return reclamoSeleccionado;
	}

	public void setReclamoSeleccionado(ReclamoJSF reclamoSeleccionado) {
		this.reclamoSeleccionado = reclamoSeleccionado;
	}



	public class ReclamoJSF {
    	private Long idReclamo;
    	private EstadoReclamo estado;
    	private Evento evento;
    	private String detalle;
    	private LocalDateTime fechaHora;
    	
		public ReclamoJSF() {
			super();
		}
		
		public ReclamoJSF(Reclamo r) {
			super();
			this.idReclamo = r.getIdReclamo();
			this.estado = r.getEstado();
			this.evento = r.getEvento();
			this.fechaHora = r.getFechaHora();
			this.detalle = r.getDetalle();
			}
		
		public Reclamo toEntity() {
			Reclamo e = new Reclamo();
			e.setIdReclamo(idReclamo);
			e.setEstado(estado);
			e.setDetalle(detalle);
			e.setEvento(evento);
			e.setFechaHora(fechaHora);
			return e;
		}

		public String getFormattedDate() {
			return Formatos.ToFormatedString(fechaHora);
		}
		
		public Long getIdReclamo() {
			return idReclamo;
		}

		public void setIdReclamo(Long idReclamo) {
			this.idReclamo = idReclamo;
		}

		public EstadoReclamo getEstado() {
			return estado;
		}

		public void setEstado(EstadoReclamo estado) {
			this.estado = estado;
		}

		public Evento getEvento() {
			return evento;
		}

		public void setEvento(Evento evento) {
			this.evento = evento;
		}

		public String getDetalle() {
			return detalle;
		}

		public void setDetalle(String detalle) {
			this.detalle = detalle;
		}

		public LocalDateTime getFechaHora() {
			return fechaHora;
		}

		public void setFechaHora(LocalDateTime fechaHora) {
			this.fechaHora = fechaHora;
		}
    }
	
	
    

}
