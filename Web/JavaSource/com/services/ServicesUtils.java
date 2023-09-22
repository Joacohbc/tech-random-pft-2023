package com.services;

import com.exceptions.InvalidEntityException;
import com.exceptions.ServiceException;

public class ServicesUtils {
	
	/**
	 * Evalua si la Entidad que se le pasa es nulo, y si lo es arroja un ServiceException
	 * con el error que se le indique
	 * 
	 * @param obj 
	 * @param error El texto que contendra la exception
	 * @throws ServiceException Si el obj == null
	 */
	public static void checkNull(Object obj, String error) throws ServiceException {
		if (obj == null)
			throw new ServiceException(error);
	}
}
