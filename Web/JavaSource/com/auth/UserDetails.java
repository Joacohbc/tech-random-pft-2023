package com.auth;

import com.entities.enums.Rol;

import io.jsonwebtoken.impl.DefaultClaims;

public class UserDetails  {
	
	private Long idUsuario;
	private Long idRol;
	private String nombreUsuario;
	private Rol rol;

	public UserDetails() {
		super();
	}
	
	public UserDetails(Long idUsuario, Long idRol, String nombreUsuario, Rol rol) {
		super();
		this.idUsuario = idUsuario;
		this.idRol = idRol;
		this.nombreUsuario = nombreUsuario;
		this.rol = rol;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	@Override
	public String toString() {
		return "UserDetails [idUsuario=" + idUsuario + ", idRol=" + idRol + ", nombreUsuario=" + nombreUsuario
				+ ", rol=" + rol + "]";
	}
}
