package com.exceptions;

import java.security.NoSuchAlgorithmException;

import javax.ejb.ApplicationException;

/**
 * <h1>Uso de la Exception</h1> <br>
 * Se arroja cuando ocurre un error ajeno al Servicio que no pudo ser validado
 * (ej: un {@link DAOException}, un {@link NoSuchAlgorithmException} o cualquier
 * otra {@link Exception}.
 * <p>
 * Esta diseñada unicamente para ser arrojada desde un Services (Bean).
 */
@ApplicationException
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable e) {
		super(message + " " + e.getMessage());
	}

	public ServiceException(Exception e) {
		super("Error desconocido: " + e.getMessage());
	}
}
