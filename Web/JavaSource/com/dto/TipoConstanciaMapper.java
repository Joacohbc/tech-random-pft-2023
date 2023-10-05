package com.dto;

import com.entities.TipoConstancia;

public class TipoConstanciaMapper {

    public static TipoConstanciaDTO toTipoConstanciaDTO(TipoConstancia tipoConstancia) {
        TipoConstanciaDTO tipoConstanciaDTO = new TipoConstanciaDTO();
        tipoConstanciaDTO.setIdTipoConstancia(tipoConstancia.getIdTipoConstancia());
        tipoConstanciaDTO.setEstado(tipoConstancia.getEstado());
        tipoConstanciaDTO.setPlantilla(tipoConstancia.getPlantilla());
        tipoConstanciaDTO.setTipo(tipoConstancia.getTipo());
        return tipoConstanciaDTO;
    }

    public static TipoConstancia toTipoConstancia(TipoConstanciaDTO tipoConstanciaDTO) {
        TipoConstancia tipoConstancia = new TipoConstancia();
        tipoConstancia.setIdTipoConstancia(tipoConstanciaDTO.getIdTipoConstancia());
        tipoConstancia.setEstado(tipoConstanciaDTO.getEstado());
        tipoConstancia.setPlantilla(tipoConstanciaDTO.getPlantilla());
        tipoConstancia.setTipo(tipoConstanciaDTO.getTipo());
        return tipoConstancia;
    }
}
