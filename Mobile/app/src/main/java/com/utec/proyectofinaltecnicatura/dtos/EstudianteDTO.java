package com.utec.proyectofinaltecnicatura.dtos;

import java.io.Serializable;

public class EstudianteDTO extends UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idEstudiante;
	private Integer generacion;
	private Boolean estado;

	public EstudianteDTO() {
	}

	public Long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getGeneracion() {
		return this.generacion;
	}

	public void setGeneracion(Integer generacion) {
		this.generacion = generacion;
	}
}