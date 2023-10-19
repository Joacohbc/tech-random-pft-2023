package com.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.entities.enums.EstadoReclamo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class ReclamoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idReclamo;
    private String detalle;
    private EstadoReclamo estado;
    
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)  
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime fechaHora;
    private EstudianteDTO estudiante;
    private EventoDTO evento;
    
	public Long getIdReclamo() {
		return idReclamo;
	}
	public void setIdReclamo(Long idReclamo) {
		this.idReclamo = idReclamo;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public EstadoReclamo getEstado() {
		return estado;
	}
	public void setEstado(EstadoReclamo estado) {
		this.estado = estado;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public EstudianteDTO getEstudiante() {
		return estudiante;
	}
	public void setEstudiante(EstudianteDTO estudiante) {
		this.estudiante = estudiante;
	}
	public EventoDTO getEvento() {
		return evento;
	}
	public void setEvento(EventoDTO evento) {
		this.evento = evento;
	}   
}
