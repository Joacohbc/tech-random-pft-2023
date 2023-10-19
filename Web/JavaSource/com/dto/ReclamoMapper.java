package com.dto;

import com.entities.Reclamo;

public class ReclamoMapper {

    public static ReclamoDTO toReclamoDTO(Reclamo reclamo) {
        ReclamoDTO reclamoDTO = new ReclamoDTO();
        reclamoDTO.setIdReclamo(reclamo.getIdReclamo());
        reclamoDTO.setDetalle(reclamo.getDetalle());
        reclamoDTO.setEstado(reclamo.getEstado());
        reclamoDTO.setFechaHora(reclamo.getFechaHora());
        
        // Mapea el Estudiante
        reclamoDTO.setEstudiante(EstudianteMapper.toEstudianteDTO(reclamo.getEstudiante()));
        // Mapea el Evento
        reclamoDTO.setEvento(EventoMapper.toEventoDTO(reclamo.getEvento()));
        return reclamoDTO;
    }

    public static Reclamo toReclamo(ReclamoDTO reclamoDTO) {
        Reclamo reclamo = new Reclamo();
        reclamo.setIdReclamo(reclamoDTO.getIdReclamo());
        reclamo.setDetalle(reclamoDTO.getDetalle());
        reclamo.setEstado(reclamoDTO.getEstado());
        reclamo.setFechaHora(reclamoDTO.getFechaHora());
        
        // Mapea el Estudiante
        reclamo.setEstudiante(EstudianteMapper.toEstudiante(reclamoDTO.getEstudiante()));
        
        // Mapea el Evento
        reclamo.setEvento(EventoMapper.toEvento(reclamoDTO.getEvento()));
        return reclamo;
    }
}