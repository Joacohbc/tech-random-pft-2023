package com.utec.proyectofinaltecnicatura.dtos.enums;

public enum Genero {
	FEMENINO("Femenino"), 
	MASCULINO("Masculino"), 
	NO_BINARIO("No binario");
		
	private String genero;
	
	@Override
	public String toString() {
		return genero;
	}
	
	private Genero(String genero) {
		this.genero = genero;
	}
}
