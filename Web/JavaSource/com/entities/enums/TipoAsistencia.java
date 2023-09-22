package com.entities.enums;

public enum TipoAsistencia {
	ASISTENCIA("Asitencia"), 
	MEDIA_ASISTENCIA_MATUTINA("Media asistencia matutina"),
	MEDIA_ASISTENCIA_VESPERTINA("Media asistencia vespertina"), 
	AUSENCIA("Ausencia"),
	AUSENCIA_JUSTIFICADA("Ausencia justificada");

	private String asistencia;

	private TipoAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}

	@Override
	public String toString() {
		return asistencia;
	}
}
