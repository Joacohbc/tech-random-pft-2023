package com.services;

import javax.ejb.Remote;

import com.entities.Itr;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

@Remote
public interface ItrBeanRemote extends ServiceInterface<Itr> {
	Itr findByName(String nombre);
	Itr reactivar(Long id) throws ServiceException, NotFoundEntityException;
}
