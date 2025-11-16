package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.DTOs.RolDTO;

import java.util.List;

public interface RolService {
    List<RolDTO> listarRoles();
    RolDTO guardarRol(RolDTO dto);
    RolDTO actualizarRol(Long id, RolDTO dto);
    void eliminarRol(Long id);
}
