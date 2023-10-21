package com.utec.proyectofinaltecnicatura.dtos.enums;

public enum Modalidad {
	VIRTUAL("Virtual"),
	PRESENCIAL("Presencial"),
	SEMI_PRESENCIAL("Semi-presencial");
	
	private String modalidad;

	@Override
	public String toString() {
		return modalidad;
	}

	private Modalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	
}
