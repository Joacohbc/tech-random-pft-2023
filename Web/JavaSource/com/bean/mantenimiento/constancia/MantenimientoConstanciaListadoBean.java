package com.bean.mantenimiento.constancia;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimeType;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;

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
	
	private List<TipoConstanciaJSF> tipoConstancias;
	private List<TipoConstanciaJSF> tipoConstanciasSelecionados = new ArrayList<>();
	private TipoConstanciaJSF tipoConstanciaSeleccionada;
	private byte[] nuevaPlantilla;
	
	public void alta() {
		try {
			TipoConstancia constancia = tipoConstanciaSeleccionada.toEntity();
			bean.reactivar(constancia.getIdTipoConstancia());
			tipoConstanciaSeleccionada.setEstado(true);
			
			PrimeFaces.current().executeScript("PF('altaTipoConstanciaDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaTipoConstancias");
			
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Alta de tipo de constancia exitosa");
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
			
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Baja el tipo de constancia exitosa");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, e.getMessage());
		}
	}
	
	public void editar() {
		try {
			TipoConstancia constancia = tipoConstanciaSeleccionada.toEntity();
			
			if(nuevaPlantilla != null) {
				constancia.setPlantilla(nuevaPlantilla);
				tipoConstanciaSeleccionada.setPlantilla(JSFUtils.crearPDF(nuevaPlantilla, tipoConstanciaSeleccionada.getTipo()));
			} else {
				final byte[] plantillaVieja = bean.findById(constancia.getIdTipoConstancia()).getPlantilla();
				constancia.setPlantilla(plantillaVieja);
				tipoConstanciaSeleccionada.setPlantilla(JSFUtils.crearPDF(plantillaVieja, constancia.getTipo()));
			}
			
			bean.update(constancia);
			
			PrimeFaces.current().ajax().update("form:listaTipoConstancias");
			PrimeFaces.current().ajax().update("PF('editarTipoConstanciaDialog').hide()");
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se actualizé el tipo de constancia con éxito");
		} catch (Exception e) {
		 	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, e.getMessage());
		}
	}
	
	public void nueva() {
		
	}
	
    public void handleFileUpload(FileUploadEvent event) {
    	if(!event.getFile().getContentType().equals(JSFUtils.APPLICATION_PDF)) {
    		JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "El archivo para la plantilla debe ser de tipo PDF");
    		return;
    	}
    	
        nuevaPlantilla = event.getFile().getContent();
    	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "El archivo " + event.getFile().getFileName() + " se subió con éxito");
    }
	
	@PostConstruct
	private void init() {
		this.tipoConstancias = new ArrayList<>();
		bean.findAll().stream().forEach(t -> tipoConstancias.add(new TipoConstanciaJSF(t)));
	}
	
	@Override
	public void checkUser() throws IOException {
		if(!auth.esAnalista()) {
	        JSFUtils.redirect("/noAuth.xhtml");
		}
	}

	public List<TipoConstanciaJSF> getTipoConstancias() {
		return tipoConstancias;
	}

	public void setTipoConstancias(List<TipoConstanciaJSF> tipoConstancias) {
		this.tipoConstancias = tipoConstancias;
	}

	public List<TipoConstanciaJSF> getTipoConstanciasSelecionados() {
		return tipoConstanciasSelecionados;
	}

	public void setTipoConstanciasSelecionados(List<TipoConstanciaJSF> tipoConstanciasSelecionados) {
		this.tipoConstanciasSelecionados = tipoConstanciasSelecionados;
	}
	
	public TipoConstanciaJSF getTipoConstanciaSeleccionada() {
		return tipoConstanciaSeleccionada;
	}

	public void setTipoConstanciaSeleccionada(TipoConstanciaJSF tipoConstanciaSeleccionada) {
		this.tipoConstanciaSeleccionada = tipoConstanciaSeleccionada;
	}

	// Esta clase auxiliar tiene como objetivo poder usar Tipo de Constancia y cargar el archivo de la plantilla
	// dentro de un StreamedContent y asi poder descargar el archivo en cada caso, dentro del PostConstruct se mappean
	// todos los TipoConstancia a TipoConstanciaDTO
    public class TipoConstanciaJSF {
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
		
		public TipoConstanciaJSF() {
			super();
		}
		
		public TipoConstanciaJSF(TipoConstancia t) {
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
