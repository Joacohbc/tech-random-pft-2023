package com.dto;

import com.entities.Constancia;

public class ConstanciaMapper {

    public static ConstanciaDTO toConstanciaDTO(Constancia constancia) {
        ConstanciaDTO constanciaDTO = new ConstanciaDTO();
        constanciaDTO.setIdConstancia(constancia.getIdConstancia());
        constanciaDTO.setDetalle(constancia.getDetalle());
        constanciaDTO.setEstado(constancia.getEstado());
        constanciaDTO.setFechaHora(constancia.getFechaHora());
        constanciaDTO.setArchivo(constancia.getArchivo());
        constanciaDTO.setEstudiante(EstudianteMapper.toEstudianteDTO(constancia.getEstudiante()));
        constanciaDTO.setEvento(EventoMapper.toEventoDTO(constancia.getEvento()));
        constanciaDTO.setTipoConstancia(TipoConstanciaMapper.toTipoConstanciaDTO(constancia.getTipoConstancia()));
        return constanciaDTO;
    }

    public static Constancia toConstancia(ConstanciaDTO constanciaDTO) {
        Constancia constancia = new Constancia();
        constancia.setIdConstancia(constanciaDTO.getIdConstancia());
        constancia.setDetalle(constanciaDTO.getDetalle());
        constancia.setEstado(constanciaDTO.getEstado());
        constancia.setFechaHora(constanciaDTO.getFechaHora());
        constancia.setArchivo(constanciaDTO.getArchivo());
        constancia.setEstudiante(EstudianteMapper.toEstudiante(constanciaDTO.getEstudiante()));
        constancia.setEvento(EventoMapper.toEvento(constanciaDTO.getEvento()));
        constancia.setTipoConstancia(TipoConstanciaMapper.toTipoConstancia(constanciaDTO.getTipoConstancia()));
        return constancia;
    }
}
