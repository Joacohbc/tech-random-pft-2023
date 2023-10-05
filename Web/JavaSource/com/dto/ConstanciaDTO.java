package com.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.entities.enums.EstadoSolicitudes;

public class ConstanciaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idConstancia;
    private String detalle;
    private EstadoSolicitudes estado;
    private LocalDateTime fechaHora;
    private byte[] archivo;
    private EstudianteDTO estudiante;
    private EventoDTO evento;
    private TipoConstanciaDTO tipoConstancia;
    
	public Long getIdConstancia() {
		return idConstancia;
	}
	public void setIdConstancia(Long idConstancia) {
		this.idConstancia = idConstancia;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public EstadoSolicitudes getEstado() {
		return estado;
	}
	public void setEstado(EstadoSolicitudes estado) {
		this.estado = estado;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
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
	public TipoConstanciaDTO getTipoConstancia() {
		return tipoConstancia;
	}
	public void setTipoConstancia(TipoConstanciaDTO tipoConstancia) {
		this.tipoConstancia = tipoConstancia;
	}
}
