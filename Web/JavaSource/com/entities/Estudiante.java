package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ESTUDIANTES database table.
 * 
 */
@Entity
@Table(name = "ESTUDIANTES")
public class Estudiante extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * Las clases Tutor, Estudiante y Analista no posse un ID declarada con @Id
	 * debido a que extienden de Usuario y solo la super clase Usuario puede
	 * tener el ID en una Herencia JOINED.
	 * 
	 * Por tanto, se le agrega el ID de Estudiante (en este caso) como UNIQUE y 
	 * NOT NULL y se le asigna un valor con un Trigger desde la Base de Datos.
	 * 
	 * Hibernate puede leer el IdEstudiante desde la base de datos pero no escribe
	 * sobre el ya que lo considera un atributo no primario, por tanto tenemos
	 * que escribirlo nosotros.
	 * */
	@Column(name = "ID_ESTUDIANTE", nullable = false, unique = true)
	private Long idEstudiante;

	@Column(nullable = false)
	private Integer generacion;

	@Column(nullable = false)
	private Boolean estado;

	@OneToMany(mappedBy = "estudiante")
	private List<Asistencia> asistencias;

	@OneToMany(mappedBy = "estudiante")
	private List<Constancia> constancias;

	@OneToMany(mappedBy = "estudiante")
	private List<Justificacion> justificaciones;

	@OneToMany(mappedBy = "estudiante")
	private List<Reclamo> reclamos;

	public Estudiante() {
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

	public List<Asistencia> getAsistencias() {
		return this.asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public Asistencia addAsistencia(Asistencia asistencia) {
		getAsistencias().add(asistencia);
		asistencia.setEstudiante(this);

		return asistencia;
	}

	public Asistencia removeAsistencia(Asistencia asistencia) {
		getAsistencias().remove(asistencia);
		asistencia.setEstudiante(null);

		return asistencia;
	}

	public List<Constancia> getConstancias() {
		return this.constancias;
	}

	public void setConstancias(List<Constancia> constancias) {
		this.constancias = constancias;
	}

	public Constancia addConstancia(Constancia constancia) {
		getConstancias().add(constancia);
		constancia.setEstudiante(this);

		return constancia;
	}

	public Constancia removeConstancia(Constancia constancia) {
		getConstancias().remove(constancia);
		constancia.setEstudiante(null);

		return constancia;
	}

	public List<Justificacion> getJustificaciones() {
		return this.justificaciones;
	}

	public void setJustificaciones(List<Justificacion> justificaciones) {
		this.justificaciones = justificaciones;
	}

	public Justificacion addJustificacione(Justificacion justificacione) {
		getJustificaciones().add(justificacione);
		justificacione.setEstudiante(this);

		return justificacione;
	}

	public Justificacion removeJustificacione(Justificacion justificacione) {
		getJustificaciones().remove(justificacione);
		justificacione.setEstudiante(null);

		return justificacione;
	}

	public List<Reclamo> getReclamos() {
		return this.reclamos;
	}

	public void setReclamos(List<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	public Reclamo addReclamo(Reclamo reclamo) {
		getReclamos().add(reclamo);
		reclamo.setEstudiante(this);

		return reclamo;
	}

	public Reclamo removeReclamo(Reclamo reclamo) {
		getReclamos().remove(reclamo);
		reclamo.setEstudiante(null);

		return reclamo;
	}

	@Override
	public String toString() {
		return getNombres() + " " + getApellidos() + " - " + getDocumento();
	}
}