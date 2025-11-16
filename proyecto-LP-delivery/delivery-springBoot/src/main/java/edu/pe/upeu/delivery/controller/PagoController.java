package edu.pe.upeu.delivery.controller;


import edu.pe.upeu.delivery.DTOs.PagoDTO;
import edu.pe.upeu.delivery.service.PagoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PagoDTO> listar() {
        return service.listarPagos();
    }

    @GetMapping("/{id}")
    public PagoDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public PagoDTO registrar(@RequestBody PagoDTO dto) {
        return service.registrarPago(dto);
    }

    @PutMapping("/{id}")
    public PagoDTO actualizar(@PathVariable Long id, @RequestBody PagoDTO dto) {
        return service.actualizarPago(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarPago(id);
    }
}
