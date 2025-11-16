package edu.pe.upeu.delivery.service;


import edu.pe.upeu.delivery.DTOs.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDTO> listarCategorias();
    CategoriaDTO guardarCategoria(CategoriaDTO dto);
    CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto);
    void eliminarCategoria(Long id);
}
