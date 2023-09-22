package com.services;

import java.util.List;

import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;

public interface ServiceInterface<T> {
	T save(T entity) throws ServiceException, InvalidEntityException;

	T remove(Long id) throws ServiceException, NotFoundEntityException;

	T update(T entity) throws ServiceException, NotFoundEntityException, InvalidEntityException;

	T findById(Long id);

	List<T> findAll();
}
