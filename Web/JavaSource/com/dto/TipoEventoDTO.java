package com.dto;

import java.io.Serializable;
import java.util.List;

public class TipoEventoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idTipoEvento;
    private String tipo;
    private Boolean estado;
    
	public Long getIdTipoEvento() {
		return idTipoEvento;
	}
	public void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
