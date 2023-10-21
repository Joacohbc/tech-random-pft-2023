package com.utec.proyectofinaltecnicatura.dtos.enums;

public enum EstadoUsuario {
	SIN_VALIDAR("Sin Validar"), VALIDADO("Validado"), ELIMINADO("Eliminado");

	private String estado;

	private EstadoUsuario(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {

		return estado;
	}
}
