package com.dto;

import java.io.Serializable;
import java.util.List;

public class TipoConstanciaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idTipoConstancia;
    private Boolean estado;
    private byte[] plantilla;
    private String tipo;
    
	public Long getIdTipoConstancia() {
		return idTipoConstancia;
	}
	public void setIdTipoConstancia(Long idTipoConstancia) {
		this.idTipoConstancia = idTipoConstancia;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public byte[] getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(byte[] plantilla) {
		this.plantilla = plantilla;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
