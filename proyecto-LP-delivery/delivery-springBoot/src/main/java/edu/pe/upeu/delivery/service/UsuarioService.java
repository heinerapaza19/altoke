package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.DTOs.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO obtenerPorId(Long id);
    UsuarioDTO guardarUsuario(UsuarioDTO dto);
    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto);
    void eliminarUsuario(Long id);
    UsuarioDTO buscarPorUsername(String username);
}
