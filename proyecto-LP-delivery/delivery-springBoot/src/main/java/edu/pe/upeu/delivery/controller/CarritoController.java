package edu.pe.upeu.delivery.controller;

import edu.pe.upeu.delivery.dto.CarritoItemDTO;
import edu.pe.upeu.delivery.service.CarritoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public List<CarritoItemDTO> listar() {
        return carritoService.obtenerCarrito();
    }

    @PostMapping
    public void agregar(@RequestBody CarritoItemDTO item) {
        carritoService.agregarProducto(item);
    }

    @DeleteMapping("/{idProducto}")
    public void eliminar(@PathVariable Long idProducto) {
        carritoService.eliminarProducto(idProducto);
    }

    @DeleteMapping("/vaciar")
    public void vaciar() {
        carritoService.vaciarCarrito();
    }

    @GetMapping("/total")
    public BigDecimal total() {
        return carritoService.calcularTotal();
    }
}
