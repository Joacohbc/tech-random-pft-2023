package com.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the GESTORES database table.
 * 
 */
@Entity
@Table(name="GESTORES")
@NamedQuery(name="Gestor.findAll", query="SELECT g FROM Gestor g")
public class Gestor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator = "SEQ_GESTORES")
	@Column(name="ID_GESTOR")
	private Long idGestor;

	@ManyToOne
	@JoinColumn(name="ID_ANALISTA", referencedColumnName = "ID_ANALISTA")
	private Analista analista;

	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;

	public Gestor() {
	}

	public Long getIdGestor() {
		return this.idGestor;
	}

	public void setIdGestor(Long idGestor) {
		this.idGestor = idGestor;
	}

	public Analista getAnalista() {
		return this.analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}