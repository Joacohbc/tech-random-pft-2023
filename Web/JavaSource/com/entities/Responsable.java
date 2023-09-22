package com.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the RESPONSABLES database table.
 * 
 */
@Entity
@Table(name = "RESPONSABLES")
public class Responsable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_RESPONSABLES")
	@Column(name = "ID_RESPONSABLE")
	private Long idResponsable;

	@ManyToOne
	@JoinColumn(name = "ID_EVENTO")
	private Evento evento;

	@ManyToOne
	@JoinColumn(name = "ID_TUTOR", referencedColumnName = "ID_TUTOR")
	private Tutor tutor;

	public Responsable() {
	}

	public Long getIdResponsable() {
		return this.idResponsable;
	}

	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Tutor getTutore() {
		return this.tutor;
	}

	public void setTutore(Tutor tutore) {
		this.tutor = tutore;
	}

}