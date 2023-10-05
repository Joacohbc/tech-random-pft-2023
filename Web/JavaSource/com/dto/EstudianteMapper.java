package com.dto;

import com.entities.Estudiante;

public class EstudianteMapper {

    public static EstudianteDTO toEstudianteDTO(Estudiante estudiante) {
        EstudianteDTO estudianteDTO = (EstudianteDTO) UsuarioMapper.toUsuarioDTO(estudiante);
        estudianteDTO.setIdEstudiante(estudiante.getIdEstudiante());
        estudianteDTO.setGeneracion(estudiante.getGeneracion());
        estudianteDTO.setEstado(estudiante.getEstado());
        return estudianteDTO;
    }

    public static Estudiante toEstudiante(EstudianteDTO estudianteDTO) {
        Estudiante estudiante = (Estudiante) UsuarioMapper.toUsuario(estudianteDTO);
        estudiante.setIdEstudiante(estudianteDTO.getIdEstudiante());
        estudiante.setGeneracion(estudianteDTO.getGeneracion());
        estudiante.setEstado(estudianteDTO.getEstado());
        return estudiante;
    }
}
