package com.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.daos.EventosDao;
import com.entities.Evento;
import com.exceptions.DAOException;

/**
 * Session Bean implementation class Estudiante
 */
@Stateless
@LocalBean
public class EventoBean implements EventoBeanRemote {

	

	@EJB
	private EventosDao dao;

    public EventoBean() {

    }
    
    public List<Evento> findByEstudianteId(Long id) {
    	return dao.findByEstudianteId(id);

    	
    }

	@Override
	public Evento findById(Long id) {		
		return dao.findById(id);
	}

    
  
        
}

