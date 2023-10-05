package com.dto;

import com.dto.ItrDTO;
import com.entities.Itr;

public class ItrMapper {

    public static ItrDTO toItrDTO(Itr itr) {
        ItrDTO itrDTO = new ItrDTO();
        itrDTO.setIdItr(itr.getIdItr());
        itrDTO.setDepartamento(itr.getDepartamento());
        itrDTO.setEstado(itr.getEstado());
        itrDTO.setNombre(itr.getNombre());
        return itrDTO;
    }

    public static Itr toItr(ItrDTO itrDTO) {
        Itr itr = new Itr();
        itr.setIdItr(itrDTO.getIdItr());
        itr.setDepartamento(itrDTO.getDepartamento());
        itr.setEstado(itrDTO.getEstado());
        itr.setNombre(itrDTO.getNombre());
        return itr;
    }
}
