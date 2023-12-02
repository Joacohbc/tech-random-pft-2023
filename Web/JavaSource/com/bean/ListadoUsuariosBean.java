package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.auth.AuthRenderedControl;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.Rol;
import com.services.ItrBean;
import com.services.UsuarioBean;

import validation.ValidacionesUsuario;
import validation.ValidacionesUsuario.TipoUsuarioDocumento;
import validation.ValidationObject;

@Named("listadoUsuariosBean")
@ViewScoped
public class ListadoUsuariosBean implements Serializable, AuthRenderedControl {

	@EJB
	private UsuarioBean bean;

	@EJB
	private ItrBean itrBean;

	@Inject
	private AuthJWTBean auth;

	private List<Usuario> usuarios;
	private List<Usuario> usuariosSeleccionados = new ArrayList<>();
	private Usuario usuarioSeleccionado;
	private List<Itr> listadoItr;

	@PostConstruct
	public void init() {
		this.usuarios = new ArrayList<>();
		usuarios.addAll(bean.findAll(Estudiante.class));
		if(auth.esAnalista()) {
			usuarios.addAll(bean.findAll(Analista.class));
			usuarios.addAll(bean.findAll(Tutor.class));
		}

		this.listadoItr = itrBean.findAll();
	}

	private void updateEstado(Usuario usuario, Boolean estado) {
		if (!auth.esAnalista())
			return;

		if (usuario instanceof Analista) {
			bean.updateEstadoAnalista(usuario.getIdUsuario(), estado);
			((Analista) usuario).setEstado(estado);
		} else if (usuario instanceof Tutor) {
			bean.updateEstadoTutor(usuario.getIdUsuario(), estado);
			((Tutor) usuario).setEstado(estado);
		} else {
			bean.updateEstadoEstudiante(usuario.getIdUsuario(), estado);
			((Estudiante) usuario).setEstado(estado);
		}

	}

	public void bajaUsuario() {
		if (!auth.esAnalista())
			return;

		try {
			updateEstado(usuarioSeleccionado, false);
			usuarios.set(usuarios.indexOf(usuarioSeleccionado), usuarioSeleccionado);

			PrimeFaces.current().executeScript("PF('bajaUsuarioDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaUsuarios");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
	}

	public void bajaUsuarios() {
		if (!auth.esAnalista())
			return;

		try {
			for (Usuario usu : usuariosSeleccionados) {
				updateEstado(usu, false);
				usuarios.set(usuarios.indexOf(usu), usu);
			}
			PrimeFaces.current().ajax().update("form:listaUsuarios");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
	}

	public String getBotonBajaMensaje() {
		if (!usuariosSeleccionados.isEmpty()) {
			int size = this.usuariosSeleccionados.size();
			return size > 1 ? size + " Usuarios Seleccionados" : "1 Usuario Seleccionado";
		}

		return "Borrar";
	}

	public void altaUsuarios() {
		if (!auth.esAnalista())
			return;

		try {

			for (Usuario usu : usuariosSeleccionados) {
				updateEstado(usu, true);
				usuarios.set(usuarios.indexOf(usu), usu);

			}
			PrimeFaces.current().ajax().update("form:listaUsuarios");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
	}

	public String getBotonAltaMensaje() {
		if (!usuariosSeleccionados.isEmpty()) {
			int size = this.usuariosSeleccionados.size();
			return size > 1 ? size + " Usuarios Seleccionados" : "1 Usuario Seleccionado";
		}

		return "Alta";
	}

	public void altaUsuario() {
		if (!auth.esAnalista())
			return;

		try {
			updateEstado(usuarioSeleccionado, true);
			usuarios.set(usuarios.indexOf(usuarioSeleccionado), usuarioSeleccionado);

			PrimeFaces.current().executeScript("PF('altaUsuarioDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaUsuarios");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
		}
	}

	public void editarUsuario() {
		try {
			if (!auth.esAnalista())
				return;

			ValidationObject error = ValidacionesUsuario.ValidarUsuarioSinContrasenia(usuarioSeleccionado,
					TipoUsuarioDocumento.URUGUAYO);
			if (!error.isValid()) {
				JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, error.getErrorMessage(), null);
				return;
			}

			if (usuarioSeleccionado instanceof Analista) {
				bean.updateAnalista((Analista) usuarioSeleccionado);
			} else if (usuarioSeleccionado instanceof Tutor) {
				bean.updateTutor((Tutor) usuarioSeleccionado);
			} else {
				bean.updateEstudiante((Estudiante) usuarioSeleccionado);
			}

			PrimeFaces.current().executeScript("PF('editarUsuarioDialog').hide()");
			PrimeFaces.current().ajax().update("form:listaUsuarios");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);

		} finally {
			// Esto es para que no se rompa el JSF del front end cuando se modifica un campo
			// y no se guarda en la BD
			Usuario bd = bean.findById(usuarioSeleccionado.getClass(), usuarioSeleccionado.getIdUsuario());
			for (int i = 0; i < usuarios.size(); i++) {
				if (usuarios.get(i).getIdUsuario() == usuarioSeleccionado.getIdUsuario()) {
					usuarios.set(i, bd);
					break;
				}
			}
			PrimeFaces.current().ajax().update("form:listaUsuarios");
		}
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public List<Usuario> getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}

	public void setUsuariosSeleccionados(List<Usuario> usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public void checkUser() throws IOException {
		if (!auth.es(Rol.ANALISTA, Rol.TUTOR)) {
			JSFUtils.redirect("/noAuth.xhtml");
		}
	}

	public List<Itr> getListadoItr() {
		return listadoItr;
	}

	public void setListadoItr(List<Itr> listadoItr) {
		this.listadoItr = listadoItr;
	}
}
