package com.dto;

import com.entities.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setDocumento(usuario.getDocumento());
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setEmailUtec(usuario.getEmailUtec());
        usuarioDTO.setEmailPersonal(usuario.getEmailPersonal());
        usuarioDTO.setNombres(usuario.getNombres());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setGenero(usuario.getGenero());
        usuarioDTO.setFecNacimiento(usuario.getFecNacimiento());
        usuarioDTO.setDepartamento(usuario.getDepartamento());
        usuarioDTO.setLocalidad(usuario.getLocalidad());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setEstadoUsuario(usuario.getEstadoUsuario());
        usuarioDTO.setItr(ItrMapper.toItrDTO(usuario.getItr()));

        return usuarioDTO;
    }

    public static Usuario toUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioDTO.getIdUsuario());
        usuario.setDocumento(usuarioDTO.getDocumento());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setEmailUtec(usuarioDTO.getEmailUtec());
        usuario.setEmailPersonal(usuarioDTO.getEmailPersonal());
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setGenero(usuarioDTO.getGenero());
        usuario.setFecNacimiento(usuarioDTO.getFecNacimiento());
        usuario.setDepartamento(usuarioDTO.getDepartamento());
        usuario.setLocalidad(usuarioDTO.getLocalidad());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEstadoUsuario(usuarioDTO.getEstadoUsuario());
        
        // La contrasenia solo es hacia la Entidad, nunca al DTO
        usuario.setContrasena(usuarioDTO.getContrasenia());
        
        // Assuming there's a separate mapper for Itr
        usuario.setItr(ItrMapper.toItr(usuarioDTO.getItr()));

        return usuario;
    }
}
