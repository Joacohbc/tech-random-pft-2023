package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.auth.AuthRenderedControl;
import com.entities.AccionReclamo;
import com.entities.Analista;
import com.entities.Reclamo;
import com.entities.enums.EstadoReclamo;
import com.entities.enums.EstadoReclamo;
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
    
	private String detalle;
    private List<Reclamo> reclamos;
	private Reclamo reclamoSeleccionado;
	private List<Reclamo> reclamosSeleccionados = new ArrayList<>();
	private Boolean mostrarReclamosFinalizados = false;
	

	
	
	@PostConstruct
	public void init() {
		this.reclamos = new ArrayList<>();
		reclamos.addAll(reclamoBean.findAll().stream().filter(c -> c.getEstado() != EstadoReclamo.FINALIZADO).collect(Collectors.toList()));
	}
	public void toggleReclamosFinalizados(AjaxBehaviorEvent event) {
        if (!(event.getComponent() instanceof UIInput)) return;
        
		if(mostrarReclamosFinalizados) {
			reclamos = reclamoBean.findAll();
		} else {
			reclamos = reclamoBean.findAll().stream().filter(c -> c.getEstado() != EstadoReclamo.FINALIZADO).collect(Collectors.toList());
		}
		PrimeFaces.current().ajax().update("form:listaReclamos");
	}

	public void cambiarEstado() {
		try {
			if(reclamoSeleccionado.getEstado() == EstadoReclamo.INGRESADO) {
				updateEstado(reclamoSeleccionado, EstadoReclamo.EN_PROCESO);
				PrimeFaces.current().ajax().update("form:listaReclamos");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Cambió el estado del reclamo exitosamente");
				return;
			}
		
			if(reclamoSeleccionado.getEstado() == EstadoReclamo.EN_PROCESO) {
				updateEstado(reclamoSeleccionado, EstadoReclamo.FINALIZADO);
				PrimeFaces.current().ajax().update("form:listaReclamos");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Finalizó el reclamo con éxito");
				return;
			}
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	private void updateEstado(Reclamo c, EstadoReclamo estado) {
		updateEstado(c, estado, "Se actualizo el estado a: " + estado.toString());
		c.setEstado(estado);
	}
	private void updateEstado(Reclamo c, EstadoReclamo estado, String detalle) {
		AccionReclamo ar = new AccionReclamo();
		ar.setReclamo(c);
		ar.setAnalista(usuarioBean.findById(Analista.class, auth.getIdRol()));
		ar.setDetalle(detalle);
		reclamoBean.updateEstado(c.getIdReclamo(), estado, ar);
	}
	
	public void eliminarReclamo() {
		try {
			reclamoBean.eliminar(reclamoSeleccionado.getIdReclamo());
			reclamos.remove(reclamoSeleccionado);
			JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se eliminó el reclamo con éxito");
			
			PrimeFaces.current().ajax().update("form:listaReclamos");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	public String getBotonAltaMensaje() {
		if (!reclamosSeleccionados.isEmpty()) {
			int size = this.reclamosSeleccionados.size();
			return size > 1 ? size + " Reclamos Seleccionados" : "1 Reclamo Seleccionado";
		}

		return "Alta";
	}
	public String getBotonBajaMensaje() {
		System.out.println(reclamosSeleccionados.size() + " DEBUG");
		if (!reclamosSeleccionados.isEmpty()) {
			int size = this.reclamosSeleccionados.size();
			return size > 1 ? size + " Reclamos Seleccionados" : "1 Reclamo Seleccionado";
		}

		return "Borrar";
	}
	@Override
	public void checkUser() throws IOException {
		if (!auth.es(Rol.ANALISTA)) {
			JSFUtils.redirect("/noAuth.xhtml");
		}
		
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public List<Reclamo> getReclamos() {
		return reclamos;
	}
	public void setReclamos(List<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}
	public Reclamo getReclamoSeleccionado() {
		return reclamoSeleccionado;
	}
	public void setReclamoSeleccionado(Reclamo reclamoSeleccionado) {
		this.reclamoSeleccionado = reclamoSeleccionado;
	}
	public List<Reclamo> getReclamosSeleccionados() {
		return reclamosSeleccionados;
	}
	public void setReclamosSeleccionados(List<Reclamo> reclamosSeleccionados) {
		this.reclamosSeleccionados = reclamosSeleccionados;
	}
	public Boolean getMostrarReclamosFinalizados() {
		return mostrarReclamosFinalizados;
	}
	public void setMostrarReclamosFinalizados(Boolean mostrarReclamosFinalizadas) {
		this.mostrarReclamosFinalizados = mostrarReclamosFinalizadas;
	}
	
	
	
    

}
