package edu.pe.upeu.delivery.service.impl;

import edu.pe.upeu.delivery.dto.CarritoItemDTO;
import edu.pe.upeu.delivery.service.CarritoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {

    // ✅ Mantiene el carrito en memoria mientras el backend esté activo
    private static final List<CarritoItemDTO> carrito = new ArrayList<>();

    @Override
    public void agregarProducto(CarritoItemDTO item) {
        carrito.add(item);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        carrito.removeIf(p -> p.getIdProducto().equals(idProducto));
    }

    @Override
    public void vaciarCarrito() {
        carrito.clear();
    }

    @Override
    public List<CarritoItemDTO> obtenerCarrito() {
        return carrito;
    }

    @Override
    public BigDecimal calcularTotal() {
        return carrito.stream()
                .map(CarritoItemDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
