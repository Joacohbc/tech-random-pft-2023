package com.bean.mantenimiento.constancia;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.auth.AuthRenderedControl;
import com.bean.AuthJWTBean;
import com.bean.JSFUtils;
import com.entities.TipoConstancia;
import com.itextpdf.text.pdf.codec.TiffWriter.FieldAscii;
import com.services.TipoConstanciaBean;

@Named("mantenimientoConstanciaListadoBean")
@ViewScoped
public class MantenimientoConstanciaListadoBean implements Serializable, AuthRenderedControl {

	@Inject
	private AuthJWTBean auth;
	
	@EJB
	private TipoConstanciaBean bean;
	
	private List<TipoConstanciaDTO> tipoConstancias;
	private List<TipoConstanciaDTO> tipoConstanciasSelecionados = new ArrayList<>();
	private TipoConstanciaDTO tipoConstanciaSeleccionada;
	private byte[] nuevaPlantilla;
	
	public void alta() {
		try {
			TipoConstancia constancia = tipoConstanciaSeleccionada.toEntity();
			bean.reactivar(constancia.getIdTipoConstancia());
			tipoConstanciaSeleccionada.setEstado(true);
			
			PrimeFaces.current().executeScript("PF('altaTipoConstanciaDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaTipoConstancias");
			
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se dio de alta el tipo de constancia con exito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, e.getMessage());
		}
	}
	
	public void baja() {
		try {
			TipoConstancia constancia = tipoConstanciaSeleccionada.toEntity();
			bean.eliminar(constancia.getIdTipoConstancia());
			tipoConstanciaSeleccionada.setEstado(false);
			
			PrimeFaces.current().executeScript("PF('bajaTipoConstanciaDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaTipoConstancias");
			
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se dio de baja el tipo de constancia con exito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, e.getMessage());
		}
	}
	
	public void editar() {
		try {
			TipoConstancia constancia = tipoConstanciaSeleccionada.toEntity();
			
			if(nuevaPlantilla != null) {
				constancia.setPlantilla(nuevaPlantilla);
			} else {
				constancia.setPlantilla(bean.findById(constancia.getIdTipoConstancia()).getPlantilla());
			}
			
			bean.update(constancia);
			
			PrimeFaces.current().ajax().update("form:listaTipoConstancias");
			PrimeFaces.current().ajax().update("PF('editarTipoConstanciaDialog').hide()");
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se actualizo el tipo de constancia con exito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, e.getMessage());
		}
	}
	
	public void nueva() {
		
	}
	
    public void handleFileUpload(FileUploadEvent event) {
        nuevaPlantilla = event.getFile().getContent();
        JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Successful", event.getFile().getFileName() + " is uploaded.");
    }
	
	@PostConstruct
	private void init() {
		this.tipoConstancias = new ArrayList<>();
		bean.findAll().stream().forEach(t -> tipoConstancias.add(new TipoConstanciaDTO(t)));
	}
	
	@Override
	public void checkUser() throws IOException {
		if(!auth.esAnalista()) {
	        JSFUtils.redirect("/noAuth.xhtml");
		}
	}

	public List<TipoConstanciaDTO> getTipoConstancias() {
		return tipoConstancias;
	}

	public void setTipoConstancias(List<TipoConstanciaDTO> tipoConstancias) {
		this.tipoConstancias = tipoConstancias;
	}

	public List<TipoConstanciaDTO> getTipoConstanciasSelecionados() {
		return tipoConstanciasSelecionados;
	}

	public void setTipoConstanciasSelecionados(List<TipoConstanciaDTO> tipoConstanciasSelecionados) {
		this.tipoConstanciasSelecionados = tipoConstanciasSelecionados;
	}
	
	public TipoConstanciaDTO getTipoConstanciaSeleccionada() {
		return tipoConstanciaSeleccionada;
	}

	public void setTipoConstanciaSeleccionada(TipoConstanciaDTO tipoConstanciaSeleccionada) {
		this.tipoConstanciaSeleccionada = tipoConstanciaSeleccionada;
	}

	// Esta clase auxiliar tiene como objetivo poder usar Tipo de Constancia y cargar el archivo de la plantilla
	// dentro de un StreamedContent y asi poder descargar el archivo en cada caso, dentro del PostConstruct se mappean
	// todos los TipoConstancia a TipoConstanciaDTO
    public class TipoConstanciaDTO {
    	private Long idTipoConstancia;
    	private Boolean estado;
    	private StreamedContent plantilla;
    	private String tipo;
    	
		public Long getIdTipoConstancia() {
			return idTipoConstancia;
		}
		public void setIdTipoConstancia(Long idTipoConstancia) {
			this.idTipoConstancia = idTipoConstancia;
		}
		public Boolean getEstado() {
			return estado;
		}
		public void setEstado(Boolean estado) {
			this.estado = estado;
		}
		public StreamedContent getPlantilla() {
			return plantilla;
		}
		public void setPlantilla(StreamedContent plantilla) {
			this.plantilla = plantilla;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		
		public TipoConstanciaDTO() {
			super();
		}
		
		public TipoConstanciaDTO(TipoConstancia t) {
			super();
			this.estado = t.getEstado();
			this.plantilla = DefaultStreamedContent.builder()
	    	        .name(t.getTipo() + ".pdf")
	    	        .contentType("application/pdf")
	    	        .stream(() -> new ByteArrayInputStream(t.getPlantilla()))
	    	        .build();
			this.idTipoConstancia = t.getIdTipoConstancia();
			this.tipo = t.getTipo();
		}
		
		public TipoConstancia toEntity() {
			TipoConstancia e = new TipoConstancia();
			e.setIdTipoConstancia(this.idTipoConstancia);
			e.setEstado(this.estado);
			e.setTipo(this.tipo);
			return e;
		}
    }
}
