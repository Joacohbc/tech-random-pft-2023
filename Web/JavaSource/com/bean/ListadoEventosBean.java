package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.auth.AuthRenderedControl;
import com.entities.Analista;
import com.entities.Constancia;
import com.entities.Estudiante;
import com.entities.Evento;
import com.entities.TipoConstancia;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.Rol;
import com.services.ConstanciaBean;
import com.services.EventoBean;
import com.services.TipoConstanciaBean;
import com.services.TipoConstanciaBeanRemote;
import com.services.UsuarioBean;

@Named("listadoEventosBean")
@ViewScoped
public class ListadoEventosBean implements Serializable, AuthRenderedControl {

	@EJB
	private EventoBean eventoBean;

	@EJB
	private ConstanciaBean constanciaBean;

	@EJB
	private TipoConstanciaBean tipoConstanciaBean;
	
	@EJB
	private UsuarioBean beanUsuario;

	@Inject
	private AuthJWTBean auth;

	
	
	private List<Evento> eventos;
	private Evento eventoSeleccionado;
	private List<TipoConstancia> tiposDeConstancias;
	private TipoConstancia tiposDeConstancia;

	@PostConstruct
	public void init() {
		this.eventos = new ArrayList<>();
		eventos.addAll(eventoBean.findByEstudianteId(auth.getIdRol()));
		this.tiposDeConstancias = new ArrayList<TipoConstancia>();
		tiposDeConstancias.addAll(tipoConstanciaBean.findAll());

	}

	public void crearSolicitud() {
		System.out.println("ENTRA AL METODO");
		if (!auth.esEstudiante())
			return;
		
        if (eventoSeleccionado == null) {
            // Añade un mensaje de error indicando que ningún evento fue seleccionado
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ningún evento fue seleccionado."));
            return;
        }
        Constancia c = new Constancia();
        Estudiante est = beanUsuario.findById(Estudiante.class,auth.getIdUsuario());
        c.setEstudiante(est);    
        c.setEvento(eventoSeleccionado);
        
        c.setTipoConstancia(tiposDeConstancia);
        c.setDetalle("EL ESTUDIANTE: " +est.getNombres() + "Solicita la cosntacia para el evento: "+eventoSeleccionado.getTitulo());
        
        constanciaBean.solicitar(c);


	}
	

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}


	public Evento getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(Evento eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	public List<TipoConstancia> getTiposDeConstancias() {
		return tiposDeConstancias;
	}

	public void setTiposDeConstancias(List<TipoConstancia> tiposDeConstancias) {
		this.tiposDeConstancias = tiposDeConstancias;
	}
	
	public TipoConstancia getTiposDeConstancia() {
		return tiposDeConstancia;
	}

	public void setTiposDeConstancia(TipoConstancia tiposDeConstancia) {
		this.tiposDeConstancia = tiposDeConstancia;
	}

	@Override
	public void checkUser() throws IOException {
		if (!auth.es(Rol.ESTUDIANTE)) {
			JSFUtils.redirect("/noAuth.xhtml");
		}
	}

}
