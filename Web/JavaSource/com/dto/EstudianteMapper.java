package com.dto;

import com.entities.Estudiante;

public class EstudianteMapper {

    public static EstudianteDTO toEstudianteDTO(Estudiante estudiante) {
    	EstudianteDTO estudianteDTO = new EstudianteDTO();
        estudianteDTO.setIdUsuario(estudiante.getIdUsuario());
        estudianteDTO.setDocumento(estudiante.getDocumento());
        estudianteDTO.setNombreUsuario(estudiante.getNombreUsuario());
        estudianteDTO.setEmailUtec(estudiante.getEmailUtec());
        estudianteDTO.setEmailPersonal(estudiante.getEmailPersonal());
        estudianteDTO.setNombres(estudiante.getNombres());
        estudianteDTO.setApellidos(estudiante.getApellidos());
        estudianteDTO.setGenero(estudiante.getGenero());
        estudianteDTO.setFecNacimiento(estudiante.getFecNacimiento());
        estudianteDTO.setDepartamento(estudiante.getDepartamento());
        estudianteDTO.setLocalidad(estudiante.getLocalidad());
        estudianteDTO.setTelefono(estudiante.getTelefono());
        estudianteDTO.setEstadoUsuario(estudiante.getEstadoUsuario());        
        estudianteDTO.setIdEstudiante(estudiante.getIdEstudiante());
        estudianteDTO.setGeneracion(estudiante.getGeneracion());
        estudianteDTO.setEstado(estudiante.getEstado());
        
        estudianteDTO.setItr(ItrMapper.toItrDTO(estudiante.getItr()));
        return estudianteDTO;
    }

    public static Estudiante toEstudiante(EstudianteDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setIdUsuario(dto.getIdUsuario());
        estudiante.setDocumento(dto.getDocumento());
        estudiante.setNombreUsuario(dto.getNombreUsuario());
        estudiante.setEmailUtec(dto.getEmailUtec());
        estudiante.setEmailPersonal(dto.getEmailPersonal());
        estudiante.setNombres(dto.getNombres());
        estudiante.setApellidos(dto.getApellidos());
        estudiante.setGenero(dto.getGenero());
        estudiante.setFecNacimiento(dto.getFecNacimiento());
        estudiante.setDepartamento(dto.getDepartamento());
        estudiante.setLocalidad(dto.getLocalidad());
        estudiante.setTelefono(dto.getTelefono());
        estudiante.setEstadoUsuario(dto.getEstadoUsuario());        
        estudiante.setIdEstudiante(dto.getIdEstudiante());
        estudiante.setGeneracion(dto.getGeneracion());
        estudiante.setEstado(dto.getEstado());

        estudiante.setItr(ItrMapper.toItr(dto.getItr()));
        
        // La contrasenia solo es hacia la Entidad, nunca al DTO
        estudiante.setContrasena(dto.getContrasenia());
        
        return estudiante;
    }
}
