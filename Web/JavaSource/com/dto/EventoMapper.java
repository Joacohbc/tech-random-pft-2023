package com.dto;

import com.entities.Evento;

public class EventoMapper {

    public static EventoDTO toEventoDTO(Evento evento) {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setIdEvento(evento.getIdEvento());
        eventoDTO.setTitulo(evento.getTitulo());
        eventoDTO.setFechaInicio(evento.getFechaInicio());
        eventoDTO.setFechaFin(evento.getFechaFin());
        eventoDTO.setEstado(evento.getEstado());
        eventoDTO.setLocalizacion(evento.getLocalizacion());
        eventoDTO.setModalidad(evento.getModalidad());
        eventoDTO.setItr(ItrMapper.toItrDTO(evento.getItr()));
        eventoDTO.setTipoEvento(TipoEventoMapper.toTipoEventoDTO(evento.getTipoEvento()));
        return eventoDTO;
    }

    public static Evento toEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setIdEvento(eventoDTO.getIdEvento());
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        evento.setEstado(eventoDTO.getEstado());
        evento.setLocalizacion(eventoDTO.getLocalizacion());
        evento.setModalidad(eventoDTO.getModalidad());
        evento.setItr(ItrMapper.toItr(eventoDTO.getItr()));
        evento.setTipoEvento(TipoEventoMapper.toTipoEvento(eventoDTO.getTipoEvento()));
        return evento;
    }
}
