package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.DTOs.ProductoDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> listarProductos();
    ProductoDTO obtenerPorId(Long id);
    ProductoDTO guardarProducto(ProductoDTO dto);
    ProductoDTO actualizarProducto(Long id, ProductoDTO dto);
    void eliminarProducto(Long id);
}
