package com.bean.mantenimiento.itr;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.auth.AuthRenderedControl;
import com.bean.AuthJWTBean;
import com.bean.JSFUtils;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.Rol;
import com.services.ItrBean;

import validation.ValidacionesItr;
import validation.ValidacionesUsuario;
import validation.ValidationObject;
import validation.ValidacionesUsuario.TipoUsuarioDocumento;

@Named("mantenimientoItr")
@ViewScoped
public class MantenimientoItr implements Serializable, AuthRenderedControl {
	
	@Inject
	private AuthJWTBean auth;
	
	@EJB
	private ItrBean bean;
	
	private List<Itr> itrs;
	private List<Itr> itrSeleccionados = new ArrayList<>();
	private Itr itrSeleccionado;
	
	@PostConstruct
	public void init() {
		this.itrs = new ArrayList<>();
		itrs.addAll(bean.findAll());
	}
	
	private void updateEstado(Itr itr, Boolean estado) {
		
		if (!auth.esAnalista())
			return;
		
		if(!estado) {
			bean.remove(itr.getIdItr());
			itr.setEstado(estado);
		}else {
			bean.reactivar(itr.getIdItr());
			itr.setEstado(estado);
		}

	}
	
	public void bajaitr() {
		if (!auth.esAnalista())
			return;
		
		try {
			updateEstado(itrSeleccionado, false);
			itrs.set(itrs.indexOf(itrSeleccionado), itrSeleccionado);

			PrimeFaces.current().executeScript("PF('bajaItrDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaItrs");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
	}
	
	public void bajaItrs() {
		if (!auth.esAnalista())
			return;
		
		try {
			for (Itr itr : itrSeleccionados) {
				updateEstado(itr, false);
			}
			PrimeFaces.current().ajax().update("form:listaItrs");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
	}
	
	public String getBotonBajaMensaje() {
		if (!itrSeleccionados.isEmpty()) {
			int size = this.itrSeleccionados.size();
			return size > 1 ? size + " Itrs Seleccionados" : "1 Itr Seleccionado";
		}

		return "Borrar";
	}
	
	public void altaItrs() {
		if (!auth.esAnalista())
			return;
		
		try {

			for (Itr itr : itrSeleccionados) {
				updateEstado(itr, true);
				

			}
			PrimeFaces.current().ajax().update("form:listaItrs");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
		
	}
	
	public String getBotonAltaMensaje() {
		if (!itrSeleccionados.isEmpty()) {
			int size = this.itrSeleccionados.size();
			return size > 1 ? size + " Itrs Seleccionados" : "1 Itr Seleccionado";
		}

		return "Alta";
	}
	
	public void altaitr() {
		if (!auth.esAnalista())
			return;
		
		try {
			updateEstado(itrSeleccionado, true);
			itrs.set(itrs.indexOf(itrSeleccionado), itrSeleccionado);

			PrimeFaces.current().executeScript("PF('altaItrDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaItrs");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
	}
	
	public void editaritr() {
		try {
			if (!auth.esAnalista())
				return;
			
			ValidationObject error = ValidacionesItr.validarItr(itrSeleccionado);
			if (!error.isValid()) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, error.getErrorMessage(), null);
				return;
			}
			
			bean.update(itrSeleccionado);

			PrimeFaces.current().executeScript("PF('editarItrDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaItrs");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		
		} finally {
			// Esto es para que no se rompa el JSF del front end cuando se modifica un campo y no se guarda en la BD
			Itr bd = bean.findById(itrSeleccionado.getIdItr());
			for (int i = 0; i < itrs.size(); i++) {
				if(itrs.get(i).getIdItr() == itrSeleccionado.getIdItr()) {
					itrs.set(i, bd);
					break;
				}
			}
			PrimeFaces.current().ajax().update("form:listaItrs");
		}
	}
	

	public List<Itr> getItrs() {
		return itrs;
	}

	public void setItrs(List<Itr> itrs) {
		this.itrs = itrs;
	}

	public List<Itr> getItrSeleccionados() {
		return itrSeleccionados;
	}

	public void setItrSeleccionados(List<Itr> itrSeleccionados) {
		this.itrSeleccionados = itrSeleccionados;
	}

	public Itr getItrSeleccionado() {
		return itrSeleccionado;
	}

	public void setItrSeleccionado(Itr itrSeleccionado) {
		this.itrSeleccionado = itrSeleccionado;
	}

	@Override
	public void checkUser() throws IOException {
		if(!auth.es(Rol.ANALISTA, Rol.TUTOR)) {
	        JSFUtils.redirect("/noAuth.xhtml");
		}
	}
	
	

}
