package com.services;

import java.util.List;

import javax.ejb.Remote;

import com.entities.Evento;

@Remote
public interface EventoBeanRemote {

	List<Evento> findByEstudianteId(Long id);
	
	 Evento findById(Long id);
}
