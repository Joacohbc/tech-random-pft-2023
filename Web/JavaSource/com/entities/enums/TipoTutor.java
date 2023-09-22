package com.entities.enums;

import java.io.Serializable;

public enum TipoTutor implements Serializable {
	ENCARGADO, TUTOR;

	@Override
	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
