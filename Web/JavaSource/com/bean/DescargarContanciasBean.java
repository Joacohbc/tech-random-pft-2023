package com.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.auth.AuthRenderedControl;
import com.entities.Constancia;
import com.entities.Evento;
import com.entities.TipoConstancia;
import com.entities.enums.EstadoSolicitudes;
import com.services.ConstanciaBean;

import validation.Formatos;

@Named("descargarContanciasBean")
@ViewScoped
public class DescargarContanciasBean implements Serializable, AuthRenderedControl {

	@Inject
	private AuthJWTBean auth;
	
	@EJB
	private ConstanciaBean bean;
	
	private List<ConstanciaJSF> constancias;
	private ConstanciaJSF constanciaSeleccionada;

	public void eliminar( ) {
		try {
			bean.eliminarConstancia(constanciaSeleccionada.getIdConstancia());
			constancias = constancias.stream().filter(c -> c.getIdConstancia() != constanciaSeleccionada.getIdConstancia()).collect(Collectors.toList());
			
			PrimeFaces.current().ajax().update("form:listaConstancias");
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se eliminio la constancia con exito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	public void editar() {
		try {
			
			
			bean.update(constanciaSeleccionada.toEntity());
			PrimeFaces.current().ajax().update("form:listaConstancias");
			PrimeFaces.current().ajax().update("PF('editarConstanciaDialog').hide()");
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se actualizo la constancia con exito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	@PostConstruct
	private void init() {
		this.constancias = new ArrayList<>();
		constancias = bean.findByIdEstudiante(auth.getIdRol()).stream().map(c -> new ConstanciaJSF(c)).collect(Collectors.toList());
	}
	
	@Override
	public void checkUser() throws IOException {
		if(!auth.esEstudiante()) {
	        JSFUtils.redirect("/noAuth.xhtml");
		}
	}
	
    public List<ConstanciaJSF> getConstancias() {
		return constancias;
	}

	public void setConstancias(List<ConstanciaJSF> constancias) {
		this.constancias = constancias;
	}

	public ConstanciaJSF getConstanciaSeleccionada() {
		return constanciaSeleccionada;
	}

	public void setConstanciaSeleccionada(ConstanciaJSF constanciaSeleccionada) {
		this.constanciaSeleccionada = constanciaSeleccionada;
	}

	public class ConstanciaJSF {
    	private Long idConstancia;
    	private EstadoSolicitudes estado;
    	private StreamedContent contancia;
    	private TipoConstancia tipo;
    	private Evento evento;
    	private String detalle;
    	private LocalDateTime fechaHora;
    	
		public ConstanciaJSF() {
			super();
		}
		
		public ConstanciaJSF(Constancia c) {
			super();
			this.idConstancia = c.getIdConstancia();
			this.estado = c.getEstado();
			this.tipo = c.getTipoConstancia();
			this.evento = c.getEvento();
			this.fechaHora = c.getFechaHora();
			this.detalle = c.getDetalle();

			if(c.getArchivo() != null)
				this.contancia = DefaultStreamedContent.builder()
		    	        .name("constancia.pdf")
		    	        .contentType(JSFUtils.APPLICATION_PDF)
		    	        .stream(() -> new ByteArrayInputStream(c.getArchivo()))
		    	        .build();
			}
		
		public Constancia toEntity() {
			Constancia e = new Constancia();
			e.setIdConstancia(idConstancia);
			e.setEstado(estado);
			e.setTipoConstancia(tipo);
			e.setDetalle(detalle);
			e.setEvento(evento);
			e.setFechaHora(fechaHora);
			return e;
		}

		public String getFormattedDate() {
			return Formatos.ToFormatedString(fechaHora);
		}
		
		public Long getIdConstancia() {
			return idConstancia;
		}

		public void setIdConstancia(Long idConstancia) {
			this.idConstancia = idConstancia;
		}

		public EstadoSolicitudes getEstado() {
			return estado;
		}

		public void setEstado(EstadoSolicitudes estado) {
			this.estado = estado;
		}

		public StreamedContent getContancia() {
			return contancia;
		}

		public void setContancia(StreamedContent contancia) {
			this.contancia = contancia;
		}

		public TipoConstancia getTipo() {
			return tipo;
		}

		public void setTipo(TipoConstancia tipo) {
			this.tipo = tipo;
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
