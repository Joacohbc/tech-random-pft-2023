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
        
    	ByteArrayInputStream constancia = new ByteArrayInputStream(bean.generarPlantilla(titulo, parrafo1, parrafo2, 3, file.getContent()));
    	fileDownloaded = DefaultStreamedContent.builder()
    	        .name("constancia.pdf")
    	        .contentType("document/pdf")
    	        .stream(() -> constancia)
    	        .build();
    	
        FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
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
