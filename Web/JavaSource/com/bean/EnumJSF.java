package com.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.entities.Itr;
import com.entities.enums.Departamento;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.Genero;
import com.entities.enums.Rol;
import com.entities.enums.TipoTutor;

import validation.ValidacionesUsuario.TipoUsuarioDocumento;

@Named("enumBean")
@ApplicationScoped
public class EnumJSF {

	public EnumJSF() {
	}

	public Departamento[] getDepartamentos() {
		return Departamento.values();
	}

	public Genero[] getGeneros() {
		return Genero.values();
	}

	public TipoUsuarioDocumento[] getTiposDocumentos() {
		return TipoUsuarioDocumento.values();
	}

	public TipoTutor[] getTipoTutores() {
		return TipoTutor.values();
	}

	public Rol[] getRoles() {
		return Rol.values();
	}
	
	public EstadoUsuario[] getEstadoUsuario() {
		return EstadoUsuario.values();
	}
	
	
}
