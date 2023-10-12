package com.bean.mantenimiento.constancia;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.auth.AuthRenderedControl;
import com.bean.AuthJWTBean;
import com.bean.JSFUtils;
import com.entities.TipoConstancia;
import com.entities.enums.Rol;
import com.services.ConstanciaBean;
import com.services.TipoConstanciaBean;


@Named("mantenimientoConstanciaCrearBean")
@ViewScoped
public class MantenimientoConstanciaCrearBean implements Serializable, AuthRenderedControl {

	@EJB
	private TipoConstanciaBean bean;
	
	@EJB
	private ConstanciaBean bean2;
	
	@Inject
	private AuthJWTBean auth;
	
	private String titulo;
	private String parrafo1;
	private String parrafo2;
    private StreamedContent fileDownloaded;
    private byte[] plantillaDeConstancia;
    private byte[] plantillaBase;
    
    public void handleFileUpload(FileUploadEvent event) { 
    	try {
        	if(!JSFUtils.isImage(event.getFile().getContentType())) {
        		JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "El archivo para la plantilla debe ser de tipo JPG/PNG/JPEG");
        		return;
        	}
        	
        	plantillaBase = event.getFile().getContent();
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "El archivo " + event.getFile().getFileName() + " se subio con exito");
    	} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar el archivo: " + e.getMessage());
		}
    }
    
    public void generarPlantilla() {
    	try {
    		
    		if(titulo.trim().isBlank() && (parrafo1.trim().isBlank() || parrafo2.trim().isBlank())) {
    			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos, de titulo o alguno de los parrafos");
    	    	return;		
    		}
    		
    		if(plantillaBase == null) {	
    			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Debe cargar una la base antes de generar la plantilla");
    	    	return;		
    		}
    		
    		plantillaDeConstancia = bean.generarPlantilla(titulo, parrafo1, parrafo2, 1, plantillaBase.clone());
        	fileDownloaded = JSFUtils.crearPDF(plantillaDeConstancia, titulo);
        	
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Ya se genero la plantilla, puede dar de la alta o previsualizarla");
    	} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al generar la plantilla: " + e.getMessage());
		}

    }
    
    public void alta() {
    	if(plantillaDeConstancia == null) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Cargue la plantilla y verifique que sea correcta antes de darla de alta");
			return;
    	}
    	
    	try {
        	TipoConstancia tp = new TipoConstancia();
        	tp.setEstado(true);
        	tp.setPlantilla(plantillaDeConstancia);
        	tp.setTipo(titulo);
        	bean.insert(tp);
        	
        	JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Se creo con exito la nueva plantilla de constancia: " + tp.getTipo()); 
    	} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
    }
  
    public Map<String, List<String>> getInfoParseada() {
        Map<String, List<String>> infoParseada = new HashMap<>();
        
        List<String> datosEstudiante = new ArrayList<>();
        datosEstudiante.add("Nombre/s del Estudiante: &nombre&");
        datosEstudiante.add("Apellidos del Estudiante: &apellido&");
        datosEstudiante.add("Documento del estudiante: &documento&");
        datosEstudiante.add("Generacion del Estudiante: &generacion&");
        infoParseada.put("Datos del Estudiante", datosEstudiante);
        
        List<String> datosEvento = new ArrayList<>();
        datosEvento.add("Nombre del Evento: &evento&");
        datosEvento.add("Fecha de Inicio del Evento: &fechainicio&");
        datosEvento.add("Fecha de Fin del Evento: &fechafin&");
        datosEvento.add("Modalidad del Evento: &modalidad&");
        datosEvento.add("Localización del Evento: &lugar&");
        infoParseada.put("Datos del Evento", datosEvento);
        
        return infoParseada;
    }
    
	@Override
	public void checkUser() throws IOException {
		if(!auth.es(Rol.ANALISTA, Rol.TUTOR)) {
	        JSFUtils.redirect("/noAuth.xhtml");
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getParrafo1() {
		return parrafo1;
	}

	public void setParrafo1(String parrafo1) {
		this.parrafo1 = parrafo1;
	}

	public String getParrafo2() {
		return parrafo2;
	}

	public void setParrafo2(String parrafo2) {
		this.parrafo2 = parrafo2;
	}

	public StreamedContent getFileDownloaded() {
		return fileDownloaded;
	}

	public void setFileDownloaded(StreamedContent fileDownloaded) {
		this.fileDownloaded = fileDownloaded;
	}	
}
