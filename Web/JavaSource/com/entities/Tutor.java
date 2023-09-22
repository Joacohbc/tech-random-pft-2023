package com.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.entities.enums.TipoTutor;

import java.util.List;

/**
 * The persistent class for the TUTORES database table.
 * 
 */
@Entity
@Table(name = "TUTORES")
public class Tutor extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Las clases Tutor, Estudiante y Analista no posse un ID declarada con @Id
	 * debido a que extienden de Usuario y solo la super clase Usuario puede
	 * tener el ID en una Herencia JOINED
	 * */
	@Column(name = "ID_TUTOR", nullable = false, unique = true)
	private Long idTutor;

	@Column(nullable = false)
	private String area; // TODO: Esto deberia ser un ENUM o una Tabla aparte?

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoTutor tipo;

	@Column(nullable = false)
	private Boolean estado;

	@OneToMany(mappedBy = "tutor")
	private List<Responsable> responsables;

	public Tutor() {
	}

	public Long getIdTutor() {
		return idTutor;
	}

	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public TipoTutor getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoTutor tipo) {
		this.tipo = tipo;
	}

	public List<Responsable> getResponsables() {
		return this.responsables;
	}

	public void setResponsables(List<Responsable> responsables) {
		this.responsables = responsables;
	}

	public Responsable addResponsable(Responsable responsable) {
		getResponsables().add(responsable);
		responsable.setTutore(this);

		return responsable;
	}

	public Responsable removeResponsable(Responsable responsable) {
		getResponsables().remove(responsable);
		responsable.setTutore(null);

		return responsable;
	}

	@Override
	public String toString() {
		return "Tutor [idTutor=" + idTutor + ", area=" + area + ", tipo=" + tipo + ", estado=" + estado
				+ ", getIdUsuario()=" + getIdUsuario() + ", getApellidos()=" + getApellidos() + ", getContrasena()="
				+ getContrasena() + ", getDepartamento()=" + getDepartamento() + ", getDocumento()=" + getDocumento()
				+ ", getEmail()=" + getEmailUtec() + ", getEstadoUsuario()=" + getEstadoUsuario() + ", getFecNacimiento()="
				+ getFecNacimiento() + ", getGenero()=" + getGenero() + ", getLocalidad()=" + getLocalidad()
				+ ", getNombreUsuario()=" + getNombreUsuario() + ", getNombres()=" + getNombres() + ", getTelefono()="
				+ getTelefono() + ", getItr()=" + getItr() + "]";
	}
}