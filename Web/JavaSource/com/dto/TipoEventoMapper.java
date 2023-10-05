package com.dto;

import com.entities.TipoEvento;

public class TipoEventoMapper {

    public static TipoEventoDTO toTipoEventoDTO(TipoEvento tipoEvento) {
        TipoEventoDTO tipoEventoDTO = new TipoEventoDTO();
        tipoEventoDTO.setIdTipoEvento(tipoEvento.getIdTipoEvento());
        tipoEventoDTO.setTipo(tipoEvento.getTipo());
        tipoEventoDTO.setEstado(tipoEvento.getEstado());
        return tipoEventoDTO;
    }

    public static TipoEvento toTipoEvento(TipoEventoDTO tipoEventoDTO) {
        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setIdTipoEvento(tipoEventoDTO.getIdTipoEvento());
        tipoEvento.setTipo(tipoEventoDTO.getTipo());
        tipoEvento.setEstado(tipoEventoDTO.getEstado());
        return tipoEvento;
    }
}
