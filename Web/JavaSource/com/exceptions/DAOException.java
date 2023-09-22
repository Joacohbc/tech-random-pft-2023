package com.exceptions;

import javax.ejb.ApplicationException;
import javax.persistence.PersistenceException;

/**
 * <h1>Uso de Exception</h1> <br>
 * Se arroja cuando se atrapa (en el catch) una {@link PersistenceException}, se
 * debe indicar de manera "friendly" cual es el error (ej: "Ocurrio un error al
 * dar de alta X: ") y adjuntar la causa dentro del mensaje.
 * <p>
 * Esta diseñada para ser arrojada unicamente dentro de un DAO.
 */
@ApplicationException
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable e) {
		super(message + " " + e.getMessage());
	}
}
