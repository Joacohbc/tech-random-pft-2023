package com.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.entities.enums.EstadoReclamo;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the RECLAMOS database table.
 * 
 */
@Entity
@Table(name="RECLAMOS")
public class Reclamo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SEQ_RECLAMOS")
	@Column(name="ID_RECLAMO")
	private Long idReclamo;

	private String detalle;

	private EstadoReclamo estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_HORA")
	private LocalDateTime fechaHora;

	//bi-directional many-to-one association to AccionReclamo
	@OneToMany(mappedBy="reclamo")
	private List<AccionReclamo> accionReclamos;

	//bi-directional many-to-one association to Estudiante
	@ManyToOne
	@JoinColumn(name="ID_ESTUDIANTE", referencedColumnName = "ID_ESTUDIANTE")
	private Estudiante estudiante;

	//bi-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;

	public Reclamo() {
	}

	public Long getIdReclamo() {
		return this.idReclamo;
	}

	public void setIdReclamo(Long idReclamo) {
		this.idReclamo = idReclamo;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public EstadoReclamo getEstado() {
		return this.estado;
	}

	public void setEstado(EstadoReclamo estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(LocalDateTime localDateTime) {
		this.fechaHora = localDateTime;
	}

	public List<AccionReclamo> getAccionReclamos() {
		return this.accionReclamos;
	}

	public void setAccionReclamos(List<AccionReclamo> accionReclamos) {
		this.accionReclamos = accionReclamos;
	}

	public AccionReclamo addAccionReclamo(AccionReclamo accionReclamo) {
		getAccionReclamos().add(accionReclamo);
		accionReclamo.setReclamo(this);

		return accionReclamo;
	}

	public AccionReclamo removeAccionReclamo(AccionReclamo accionReclamo) {
		getAccionReclamos().remove(accionReclamo);
		accionReclamo.setReclamo(null);

		return accionReclamo;
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