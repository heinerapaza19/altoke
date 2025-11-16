package edu.pe.upeu.delivery.controller;


import edu.pe.upeu.delivery.DTOs.PromocionDTO;
import edu.pe.upeu.delivery.service.PromocionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/promociones")
public class PromocionController {

    private final PromocionService service;

    public PromocionController(PromocionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PromocionDTO> listar() {
        return service.listarPromociones();
    }

    @GetMapping("/{id}")
    public PromocionDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public PromocionDTO guardar(@RequestBody PromocionDTO dto) {
        return service.guardarPromocion(dto);
    }

    @PutMapping("/{id}")
    public PromocionDTO actualizar(@PathVariable Long id, @RequestBody PromocionDTO dto) {
        return service.actualizarPromocion(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarPromocion(id);
    }
}
