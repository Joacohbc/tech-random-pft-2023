package com.services;

import java.util.List;

import javax.ejb.Remote;

import com.entities.Asistencia;
import com.entities.Constancia;

@Remote
public interface EstudianteBeanRemote {
	List<Asistencia> getAsistencias(Long id);
	List<Constancia> getConstancias(Long id);
}
