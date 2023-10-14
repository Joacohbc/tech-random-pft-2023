package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.auth.AuthRenderedControl;
import com.entities.AccionConstancia;
import com.entities.Analista;
import com.entities.Constancia;
import com.entities.enums.EstadoSolicitudes;
import com.services.ConstanciaBean;
import com.services.UsuarioBean;

@Named("emitirConstanciasBean")
@ViewScoped
public class EmitirConstanciasBean implements Serializable, AuthRenderedControl {
	
	@EJB
	private ConstanciaBean bean;
	
	@EJB
	private UsuarioBean usuarioBean;
	
	@Inject
	private AuthJWTBean auth;

	private List<Constancia> constancias;
	private Constancia constanciaSeleccionada;
	private List<Constancia> constanciasSeleccionadas = new ArrayList<>();
	private Boolean mostrarContanciasFinalizadas = false;
	
	@PostConstruct
	private void init() {
		this.constancias = new ArrayList<>();
		constancias.addAll(bean.findAll().stream().filter(c -> c.getEstado() != EstadoSolicitudes.FINALIZADO).toList());
	}
	
	public void toggleContanciasFinalizadas(AjaxBehaviorEvent event) {
        if (!(event.getComponent() instanceof UIInput)) return;
        
		if(mostrarContanciasFinalizadas) {
			constancias = bean.findAll();
		} else {
			constancias = bean.findAll().stream().filter(c -> c.getEstado() != EstadoSolicitudes.FINALIZADO).toList();
		}
		PrimeFaces.current().ajax().update("form:listaConstancias");
	}
	
	public void cambiarEstado() {
		try {
			if(constanciaSeleccionada.getEstado() == EstadoSolicitudes.INGRESADO) {
				updateEstado(constanciaSeleccionada, EstadoSolicitudes.EN_PROCESO);
				PrimeFaces.current().ajax().update("form:listaConstancias");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se cambio el estado de la constancia exitosamente");
				return;
			}
		
			if(constanciaSeleccionada.getEstado() == EstadoSolicitudes.EN_PROCESO) {
				updateEstado(constanciaSeleccionada, EstadoSolicitudes.FINALIZADO);
				PrimeFaces.current().ajax().update("form:listaConstancias");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se finalizó la constancia con éxito y se generó la constancia para el estudiante exitosamente");
				return;
			}
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	public void eliminarConstancia() {
		try {
			bean.eliminarConstancia(constanciaSeleccionada.getIdConstancia());
			constancias.remove(constanciaSeleccionada);
			JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se eliminó la constancia con éxito exitosamente");
			
			PrimeFaces.current().ajax().update("form:listaConstancias");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
//	public void seleccionadasAEnPogreso() {
//		constanciasSeleccionadas.stream().forEach(t -> {
//			updateEstado(t, EstadoSolicitudes.EN_PROCESO);
//		});
//	}
//	
//	public void seleccionadasAFinalizado() {
//		constanciasSeleccionadas.stream().forEach(t -> {
//			updateEstado(t, EstadoSolicitudes.FINALIZADO);
//		});
//	}
	
	private void updateEstado(Constancia c, EstadoSolicitudes estado) {
		updateEstado(c, estado, "Se actualizo el estado a: " + estado.toString());
		c.setEstado(estado);
	}
	
	private void updateEstado(Constancia c, EstadoSolicitudes estado, String detalle) {
		AccionConstancia ac = new AccionConstancia();
		ac.setConstancia(c);
		ac.setAnalista(usuarioBean.findById(Analista.class, auth.getIdRol()));
		ac.setDetalle(detalle);
		bean.updateEstado(c.getIdConstancia(), estado, ac);
	}
	
	public String getBotonAltaMensaje() {
		if (!constanciasSeleccionadas.isEmpty()) {
			int size = this.constanciasSeleccionadas.size();
			return size > 1 ? size + " Constancias Seleccionadas" : "1 Contancia Seleccionada";
		}

		return "Alta";
	}
	
	public String getBotonBajaMensaje() {
		System.out.println(constanciasSeleccionadas.size() + " DEBUG");
		if (!constanciasSeleccionadas.isEmpty()) {
			int size = this.constanciasSeleccionadas.size();
			return size > 1 ? size + " Constancias Seleccionadas" : "1 Contancia Seleccionada";
		}

		return "Borrar";
	}
	
	@Override
	public void checkUser() throws IOException {
		if(!auth.esAnalista()) {
			JSFUtils.redirect("/noAuth.xhtml");
		}
	}

	public List<Constancia> getConstancias() {
		return constancias;
	}

	public void setConstancias(List<Constancia> constancias) {
		this.constancias = constancias;
	}

	public Constancia getConstanciaSeleccionada() {
		return constanciaSeleccionada;
	}

	public void setConstanciaSeleccionada(Constancia constanciaSeleccionada) {
		this.constanciaSeleccionada = constanciaSeleccionada;
	}

	public List<Constancia> getConstanciasSeleccionadas() {
		return constanciasSeleccionadas;
	}

	public void setConstanciasSeleccionadas(List<Constancia> constanciasSeleccionadas) {
		this.constanciasSeleccionadas = constanciasSeleccionadas;
	}


	public Boolean getMostrarContanciasFinalizadas() {
		return mostrarContanciasFinalizadas;
	}


	public void setMostrarContanciasFinalizadas(Boolean mostrarContanciasFinalizadas) {
		this.mostrarContanciasFinalizadas = mostrarContanciasFinalizadas;
	}
}
