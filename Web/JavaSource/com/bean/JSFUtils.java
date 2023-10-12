package com.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class JSFUtils {
	
	public static final String APPLICATION_PDF = "application/pdf";
	public static final String IMAGE_PNG = "image/png";
	public static final String IMAGE_JPEG = "image/jpeg";
	public static final String IMAGE_JPG = "image/jpg";
	
	public static boolean isImage(String contentType) {
		return contentType.equals(IMAGE_PNG) || contentType.equals(IMAGE_JPEG) || contentType.equals(IMAGE_JPG);
	}
	
    public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public static void addMessage(FacesMessage.Severity severity, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, "", detail));
    }
    
    public static void redirect(String route) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + "/pages/" +route);
    }
    
    public static StreamedContent crearPDF(byte[] plantilla, String nombre) {
    	return DefaultStreamedContent.builder()
				.name(nombre.trim().replace(' ', '_') + ".pdf")
				.contentType(APPLICATION_PDF)
				.stream(() -> new ByteArrayInputStream(plantilla))
				.build();
    }
}
