package com.entities.enums;

public enum EstadoReclamo {
	INGRESADO("Ingresado"),
	EN_PROCESO("En proceso"),
	FINALIZADO("Finalizado");
	
	private String estado;
	
	private EstadoReclamo(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return estado;
	}

}
