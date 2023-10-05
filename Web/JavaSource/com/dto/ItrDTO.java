package com.dto;

import java.io.Serializable;
import javax.persistence.*;

import com.entities.enums.Departamento;

import java.util.List;
import java.util.Objects;

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

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idItr, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItrDTO other = (ItrDTO) obj;
		return Objects.equals(idItr, other.idItr) && Objects.equals(nombre, other.nombre);
	}
}