package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TIPO_EVENTOS database table.
 * 
 */
@Entity
@Table(name="TIPO_EVENTOS")
public class TipoEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SEQ_TIPO_EVENTOS")
	@Column(name="ID_TIPO_EVENTO")
	private Long idTipoEvento;
	
	@Column(nullable = false, unique = true)  // TODO: Esto deberia ser UNIQUE, no?
	private String tipo;

	@Column(nullable = false)
	private Boolean estado;
	
	// TODO: La bi-direccionaldiad es incesaria? No necesito saber cuales
	// Eventos son de que tipo, lo averigua directo del evento?
	@OneToMany(mappedBy="tipoEvento")
	private List<Evento> eventos;

	public TipoEvento() {
	}

	public Long getIdTipoEvento() {
		return this.idTipoEvento;
	}

	public void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setTipoEvento(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setTipoEvento(null);

		return evento;
	}

}