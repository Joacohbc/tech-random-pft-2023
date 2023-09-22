package com.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.daos.EstudianteDAO;
import com.entities.Asistencia;
import com.entities.Constancia;
import com.entities.Estudiante;

/**
 * Session Bean implementation class EstudianteBean
 */
@Stateless
@LocalBean
public class EstudianteBean implements EstudianteBeanRemote {

	
	@EJB
	private EstudianteDAO dao;
	
    public EstudianteBean() {
    }

	@Override
	public List<Asistencia> getAsistencias(Long id) {
		return dao.getAsistencias(id);
	}

	@Override
	public List<Constancia> getConstancias(Long id) {
		return dao.getConstancias(id);
	}

    
    
    
}
