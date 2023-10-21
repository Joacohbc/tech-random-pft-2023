package com.utec.proyectofinaltecnicatura.dtos.enums;

public enum EstadoSolicitudes {
	INGRESADO("Ingresado"),
	EN_PROCESO("En proceso"),
	FINALIZADO("Finalizado");
		
	private String estado;
	
	private EstadoSolicitudes(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return estado;
	}
}
