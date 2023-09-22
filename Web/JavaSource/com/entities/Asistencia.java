package com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.entities.enums.TipoAsistencia;

/**
 * The persistent class for the ASISTENCIAS database table.
 * 
 */
@Entity
@Table(name = "ASISTENCIAS")
public class Asistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ANALISTAS")
	@Column(name = "ID_ASISTENCIA")
	private Long idAsistencia;

	@Enumerated(EnumType.STRING)
	@Column(unique = false)
	private TipoAsistencia asistencia;

	@Column(nullable = true)
	private Integer calificacion;

	@ManyToOne
	@JoinColumn(name = "ID_ESTUDIANTE", referencedColumnName = "ID_ESTUDIANTE")
	private Estudiante estudiante;

	@ManyToOne
	@JoinColumn(name = "ID_EVENTO")
	private Evento evento;

	public Asistencia() {
	}

	public Long getIdAsistencia() {
		return this.idAsistencia;
	}

	public void setIdAsistencia(Long idAsistencia) {
		this.idAsistencia = idAsistencia;
	}

	public TipoAsistencia getAsistencia() {
		return this.asistencia;
	}

	public void setAsistencia(TipoAsistencia asistencia) {
		this.asistencia = asistencia;
	}

	public Integer getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	public Estudiante getEstudiante() {
		return this.estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}