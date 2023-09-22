package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACCION_JUSTIFICACIONES database table.
 * 
 */
@Entity
@Table(name="ACCION_JUSTIFICACIONES")
public class AccionJustificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SEQ_ACCION_JUSTIFICACIONES")
	@Column(name="ID_ACCION_JUSTIFICACIONES")
	private Long idAccionJustificaciones;

	private String detalle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_HORA")
	private Date fechaHora;

	//bi-directional many-to-one association to Analista
	@ManyToOne
	@JoinColumn(name="ID_ANALISTA")
	private Analista analista;

	//bi-directional many-to-one association to Justificacione
	@ManyToOne
	@JoinColumn(name="ID_JUSTIFICACION")
	private Justificacion justificacion;

	public AccionJustificacion() {
	}

	public Long getIdAccionJustificaciones() {
		return this.idAccionJustificaciones;
	}

	public void setIdAccionJustificaciones(Long idAccionJustificaciones) {
		this.idAccionJustificaciones = idAccionJustificaciones;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Analista getAnalista() {
		return this.analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Justificacion getJustificacione() {
		return this.justificacion;
	}

	public void setJustificacione(Justificacion justificacione) {
		this.justificacion = justificacione;
	}

}