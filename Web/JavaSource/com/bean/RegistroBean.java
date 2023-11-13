package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.TipoTutor;
import com.exceptions.InvalidEntityException;

import com.services.ItrBean;
import com.services.UsuarioBean;

import validation.Validaciones;
import validation.ValidacionesAccionConstancia;
import validation.ValidacionesUsuario;
import validation.ValidacionesUsuario.TipoUsuarioDocumento;
import validation.ValidacionesUsuarioEstudiante;
import validation.ValidationObject;

@Named("register")
@ViewScoped
public class RegistroBean implements Serializable {
	
		@EJB
		private UsuarioBean bean;
		
		@EJB
		private ItrBean itrBean;

		// Datos del usuario
		private Usuario usuario;
				
		// Datos del Tutor
		private String area;
		private TipoTutor tipoTutor;
		
		// Datos del estudiante
		private Integer generacion;
		
		private List<Itr> listadoItr;
		
		private Long itrId;
		
		@PostConstruct
		public void init() {
			usuario = new Usuario();
			this.listadoItr = new ArrayList<>();
			listadoItr.addAll(itrBean.findAll());
		}
		
		private Boolean validarInfoUsuarioBasica() {
			
			if(itrId == null) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "El ITR es obligatorio");
				return false;
			}
			
			usuario.setEstadoUsuario(EstadoUsuario.SIN_VALIDAR);
			usuario.setItr(itrBean.findById(itrId));
			ValidationObject valid = ValidacionesUsuario.ValidarUsuario(usuario, TipoUsuarioDocumento.URUGUAYO);
			if(!valid.isValid()) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, valid.getErrorMessage());
				return false;
			}
			
			return true;
		}
		
		public void crearAnalista() {
			if(!validarInfoUsuarioBasica()) return;
			
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
			a.setEstadoUsuario(usuario.getEstadoUsuario());
			a.setItr(usuario.getItr());
			
			//Harcodeado
			a.setEstado(true);
			
			try {
				bean.register(a, TipoUsuarioDocumento.URUGUAYO);				
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con éxito:", String.format("El Analista %s %s fue creado con éxito", a.getNombres(), a.getApellidos()));
			} catch (InvalidEntityException e) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error de ingreso:", e.getMessage());
			} catch (IOException e) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "No fue posible volver al Login", "Recargue la página");
			}

		}	
		
		public void crearEstudiante() {
			if(!validarInfoUsuarioBasica()) return;
			
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
			e.setEstadoUsuario(usuario.getEstadoUsuario());
			e.setItr(usuario.getItr());
			
			// Datos de Estudiante
			e.setGeneracion(generacion);			
			e.setItr(itrBean.findById(itrId));
			
			// Harcodeado
			e.setEstado(true);
			
			try {
				bean.register(e, TipoUsuarioDocumento.URUGUAYO);
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con éxito:", String.format("El Estudiante %s %s fue creado con éxito", e.getNombres(), e.getApellidos()));				
			} catch (InvalidEntityException ex) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error de ingreso:", ex.getMessage());
			} catch (IOException e1) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "No fue posible volver al Login", "Recargue la página");
			}
			
		}		
		
		
		public void crearTutor() {
			if(!validarInfoUsuarioBasica()) return;
			
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
			t.setEstadoUsuario(usuario.getEstadoUsuario());
			t.setItr(usuario.getItr());

			// Datos de tutor
			t.setArea(getArea());
			t.setTipo(tipoTutor);
			
			// Harcodeado
			t.setEstado(true);
						
			try {
				bean.register(t, TipoUsuarioDocumento.URUGUAYO);
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con éxito:", String.format("El Tutor %s %s fue creado con éxito", t.getNombres(), t.getApellidos()));				
			} catch (InvalidEntityException e) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error de ingreso:", e.getMessage());
			} catch (IOException e) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "No fue posible volver al Login", "Recargue la página");
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

		public List<Itr> getListadoItr() {
			return listadoItr;
		}

		public void setListadoItr(List<Itr> listadoItr) {
			this.listadoItr = listadoItr;
		}

		public Long getItrId() {
			return itrId;
		}

		public void setItrId(Long itrId) {
			this.itrId = itrId;
		}
		
		
	}
