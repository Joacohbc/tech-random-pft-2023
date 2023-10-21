package com.utec.proyectofinaltecnicatura.dtos;

import com.utec.proyectofinaltecnicatura.dtos.enums.Departamento;

import java.io.Serializable;

public class ItrDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idItr;
	private Departamento departamento;
	private Boolean estado;
	private String nombre;

	public ItrDTO() {
	}

	public Long getIdItr() {
		return this.idItr;
	}

	public void setIdItr(Long idItr) {
		this.idItr = idItr;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}