package com.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.entities.enums.Modalidad;

public class EventoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idEvento;
    private String titulo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean estado;
    private String localizacion;
    private Modalidad modalidad;
    private ItrDTO itr;
    private TipoEventoDTO tipoEvento;
    
	public Long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public String getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public ItrDTO getItr() {
		return itr;
	}
	public void setItr(ItrDTO itr) {
		this.itr = itr;
	}
	public TipoEventoDTO getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(TipoEventoDTO tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
}
