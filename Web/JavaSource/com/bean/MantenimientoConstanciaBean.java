package com.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.auth.AuthRenderedControl;
import com.entities.enums.Rol;
import com.services.ConstanciaBean;


@Named("mantenimientoConstanciaBean")
@ViewScoped
public class MantenimientoConstanciaBean implements Serializable, AuthRenderedControl {

	@EJB
	private ConstanciaBean bean;
	
	@Inject
	private AuthJWTBean auth;
	
	private String titulo;
	private String parrafo1;
	private String parrafo2;
	
    private UploadedFile file;
    private StreamedContent fileDownloaded;

    public void upload() {
        if (file == null) return;
        
    	ByteArrayInputStream constancia = new ByteArrayInputStream(bean.generarPlantilla(titulo, parrafo1, parrafo2, 1, file.getContent()));
    	fileDownloaded = DefaultStreamedContent.builder()
    	        .name("constancia.pdf")
    	        .contentType("application/pdf")
    	        .stream(() -> constancia)
    	        .build(); 
    	
        JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "El archivo ", file.getFileName() + " se subio con exito");
    }
    
    public String getInfo() {
    	return "Para parametrizar la plantilla puede utilizar las siguientes expresiones\n"
    			+ "					Datos del Estudiante:\n"
    			+ "					- Nombre/s del Estudiante: &amp;nombre&amp;\n"
    			+ "					- Apellidos del Estudiante: &amp;apellido&amp;\n"
    			+ "					- Documento del estudiante: &amp;documento&amp;\n"
    			+ "					- Generacion del Estudiante: &amp;generacion&amp;\n"
    			+ "					\n"
    			+ "					Datos del Evento:\n"
    			+ "					- Nombre del Evento: &amp;evento&amp;\n"
    			+ "					- Fecha de Inicio del Evento: &amp;fechainicio&amp;\n"
    			+ "					- Fecha de Fin del Evento: &amp;fechafin&amp;\n"
    			+ "					- Modalidad del Evento: &amp;modalidad&amp;\n"
    			+ "					- Localización del Evento: &amp;lugar&amp;\"";
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public StreamedContent getFileDownloaded() {
		return fileDownloaded;
	}

	public void setFileDownloaded(StreamedContent fileDownloaded) {
		this.fileDownloaded = fileDownloaded;
	}	
}
