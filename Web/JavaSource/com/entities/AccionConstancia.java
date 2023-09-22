package com.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the ACCION_CONSTANCIAS database table.
 * 
 */
@Entity
@Table(name = "ACCION_CONSTANCIAS")
public class AccionConstancia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ACCION_CONSTANCIAS")
	@Column(name = "ID_ACCION_CONSTANCIA")
	private Long idAccionConstancia;

	@Column(nullable = false)
	private String detalle;

	@Column(nullable = false, name ="FECHA_HORA")
	private LocalDateTime fechaHora;

	@ManyToOne
	@JoinColumn(name = "ID_ANALISTA", referencedColumnName = "ID_ANALISTA")
	private Analista analista;

	@ManyToOne
	@JoinColumn(name = "ID_CONSTANCIA")
	private Constancia constancia;

	public AccionConstancia() {
	}

	public Long getIdAccionConstancia() {
		return this.idAccionConstancia;
	}

	public void setIdAccionConstancia(Long idAccionConstancia) {
		this.idAccionConstancia = idAccionConstancia;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public LocalDateTime getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Analista getAnalista() {
		return this.analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Constancia getConstancia() {
		return this.constancia;
	}

	public void setConstancia(Constancia constancia) {
		this.constancia = constancia;
	}

}