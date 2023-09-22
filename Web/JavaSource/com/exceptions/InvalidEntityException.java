package com.exceptions;

import javax.ejb.ApplicationException;

/**
 * <h1>Uso de la Exception</h1> <br>
 * Se arroja para indicar que la entidad no tiene todos sus campos validos, en
 * el mesaje de esta exception se deberia mandar la razon de porque la entidad
 * no es valida.
 * <p>
 * Esta diseñanda para ser utilizada unicamente en los Services (Beans)
 */
@ApplicationException
public class InvalidEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidEntityException(String message) {
		super(message);
	}

}
