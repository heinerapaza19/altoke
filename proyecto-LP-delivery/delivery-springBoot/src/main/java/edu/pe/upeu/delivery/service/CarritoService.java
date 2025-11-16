package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.dto.CarritoItemDTO;
import java.math.BigDecimal;
import java.util.List;

public interface CarritoService {
    void agregarProducto(CarritoItemDTO item);
    void eliminarProducto(Long idProducto);
    void vaciarCarrito();
    List<CarritoItemDTO> obtenerCarrito();
    BigDecimal calcularTotal();
}
