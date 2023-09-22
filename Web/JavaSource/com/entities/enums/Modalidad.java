package com.entities.enums;

public enum Modalidad {
	VIRTUAL("Presencial"),
	PRESENCIAL("Virtual"),
	SEMI_PRESENCIAL("Semipresencial");
	
	private String modalidad;

	public String getModalidad() {
		return modalidad;
	}

	private Modalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	
}
