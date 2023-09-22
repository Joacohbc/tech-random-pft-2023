package com.exceptions;

import javax.ejb.ApplicationException;
import javax.persistence.PersistenceException;

/**
 * <h1>Uso de la Exception</h1> <br>
 * Se arroja antes de aplicar un Remove() o Merge() a una Entidad que no existe
 * en la BD. Para evitar una {@link PersistenceException}.
 * <p>
 * Esta diseñada para ser <b>creada y arrojada</b> dentro un <b>DAO</b>. Y
 * <b>solamente ser arrojada (hacia el siguiente nivel) </b> (ej: hacia un
 * cliente o un servelet) desde un <b>Services (Beans) que haya invocado un
 * metodo de un DAO</b> que haya arrojada la {@link NotFoundEntityException}.
 * <p>
 * La difernecia con {@link DAOException} es que esta Exception es para evitar
 * la {@link PersistenceException} antes de un Remove() y evitar la re-insersion
 * de los datos antes de un Merge() y el {@link DAOException} es para notificar
 * un {@link PersistenceException} que ocurrio por otras causas (ej: Error en el
 * EntityManger, Contraints que no se cumplen, caida de la Base de datos, etc)
 */
@ApplicationException
public class NotFoundEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundEntityException(String message) {
		super(message);
	}
}
