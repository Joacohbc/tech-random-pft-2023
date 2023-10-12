package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	private List<Constancia> constanciasSeleccionadas = new ArrayList<Constancia>();
	
	@PostConstruct
	private void init() {
		this.constancias = new ArrayList<>();
		constancias.addAll(bean.findAll());
	}
	
	public String asginarMetodoDeUpdate(Constancia c) {
		if(c.getEstado() == EstadoSolicitudes.INGRESADO) {
			return "aEnProgreso";
		}
		
		if(c.getEstado() == EstadoSolicitudes.EN_PROCESO) {
			return "aFinalizda";
		}
		
		return "";
	}
	
	public void cambiarEstado() {
		if(constanciaSeleccionada.getEstado() == EstadoSolicitudes.INGRESADO) {
			updateEstado(constanciaSeleccionada, EstadoSolicitudes.EN_PROCESO);
			return;
		}
	
		if(constanciaSeleccionada.getEstado() == EstadoSolicitudes.EN_PROCESO) {
			updateEstado(constanciaSeleccionada, EstadoSolicitudes.FINALIZADO);
			return;
		}
	}
	
	public void seleccionadasAEnPogreso() {
		constanciasSeleccionadas.stream().forEach(t -> {
			updateEstado(t, EstadoSolicitudes.EN_PROCESO);
		});
	}
	
	public void seleccionadasAFinalizado() {
		constanciasSeleccionadas.stream().forEach(t -> {
			updateEstado(t, EstadoSolicitudes.FINALIZADO);
		});
	}
	
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
}
