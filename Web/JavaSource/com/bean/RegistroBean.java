package com.bean;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.TipoTutor;
import com.exceptions.InvalidEntityException;
import com.services.UsuarioBean;

import validation.ValidacionesUsuario.TipoUsuarioDocumento;

@Named("register")
@ViewScoped
public class RegistroBean implements Serializable {
	
		@EJB
		private UsuarioBean bean;

		// Datos del usuario
		private Usuario usuario;
				
		// Datos del Tutor
		private String area;
		private TipoTutor tipoTutor;
		
		// Datos del estudiante
		private Integer generacion;
		
		@PostConstruct
		public void init() {
			usuario = new Usuario();
		}
		
		public void crearAnalista() {
			Analista a = new Analista();
			a.setDocumento(usuario.getDocumento());
			a.setNombres(usuario.getNombres());
			a.setApellidos(usuario.getApellidos());
			a.setEmailPersonal(usuario.getEmailPersonal());
			a.setEmailUtec(usuario.getEmailUtec());
			a.setTelefono(usuario.getTelefono());
			a.setFecNacimiento(usuario.getFecNacimiento());
			a.setLocalidad(usuario.getLocalidad());
			a.setContrasena(usuario.getContrasena());
			a.setDepartamento(usuario.getDepartamento());
			a.setGenero(usuario.getGenero());

			//Harcodeado
			a.setEstado(true);
			a.setEstadoUsuario(EstadoUsuario.SIN_VALIDAR);

			Itr itr = new Itr();
			itr.setIdItr(1l);
			
			//Implementar para que se seleccione el ITR.
			a.setItr(itr);
			
			try {
				bean.register(a, TipoUsuarioDocumento.URUGUAYO);				
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con exito:", String.format("El Analista %s %s fue creado con exito", a.getNombres(), a.getApellidos()));
			} catch (InvalidEntityException e) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error de ingreso:", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "No fue posible volver al Login", "Recague la pagina");
			}

		}	
		
		public void crearEstudiante() {
			Estudiante e = new Estudiante();
			e.setDocumento(usuario.getDocumento());
			e.setNombres(usuario.getNombres());
			e.setApellidos(usuario.getApellidos());
			e.setEmailPersonal(usuario.getEmailPersonal());
			e.setEmailUtec(usuario.getEmailUtec());
			e.setTelefono(usuario.getTelefono());
			e.setFecNacimiento(usuario.getFecNacimiento());
			e.setLocalidad(usuario.getLocalidad());
			e.setContrasena(usuario.getContrasena());
			e.setDepartamento(usuario.getDepartamento());
			e.setGenero(usuario.getGenero());
 
			// Datos de Estudiante
			e.setGeneracion(generacion);
			
			// Harcodeado
			e.setEstado(true);
			e.setEstadoUsuario(EstadoUsuario.SIN_VALIDAR);
			
			Itr itr = new Itr();
			itr.setIdItr(1l);
			e.setItr(itr);
			
			try {
				bean.register(e, TipoUsuarioDocumento.URUGUAYO);
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con exito:", String.format("El Estudiante %s %s fue creado con exito", e.getNombres(), e.getApellidos()));				
			} catch (InvalidEntityException ex) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error de ingreso:", ex.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "No fue posible volver al Login", "Recague la pagina");
			}
			
		}		
		
		
		public void crearTutor() {
			Tutor t = new Tutor();
			t.setDocumento(usuario.getDocumento());
			t.setNombres(usuario.getNombres());
			t.setApellidos(usuario.getApellidos());
			t.setEmailPersonal(usuario.getEmailPersonal());
			t.setEmailUtec(usuario.getEmailUtec());
			t.setTelefono(usuario.getTelefono());
			t.setFecNacimiento(usuario.getFecNacimiento());
			t.setLocalidad(usuario.getLocalidad());
			t.setContrasena(usuario.getContrasena());
			t.setDepartamento(usuario.getDepartamento());
			t.setGenero(usuario.getGenero());

			// Datos de tutor
			t.setArea(getArea());
			t.setTipo(tipoTutor);
			
			// Harcodeado
			t.setEstado(true);
			t.setEstadoUsuario(EstadoUsuario.SIN_VALIDAR);
			
			Itr itr = new Itr();
			itr.setIdItr(1l);
			t.setItr(itr);
			
			try {
				bean.register(t, TipoUsuarioDocumento.URUGUAYO);
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con exito:", String.format("El Tutor %s %s fue creado con exito", t.getNombres(), t.getApellidos()));				
			} catch (InvalidEntityException e) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error de ingreso:", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "No fue posible volver al Login", "Recague la pagina");
			}
		}
		
		
		public UsuarioBean getBean() {
			return bean;
		}

		public void setBean(UsuarioBean bean) {
			this.bean = bean;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}


		public TipoTutor getTipoTutor() {
			return tipoTutor;
		}

		public void setTipoTutor(TipoTutor tipoTutor) {
			this.tipoTutor = tipoTutor;
		}

		public Integer getGeneracion() {
			return generacion;
		}

		public void setGeneracion(Integer generacion) {
			this.generacion = generacion;
		}
		
	}
