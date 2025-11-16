package edu.pe.upeu.delivery.controller;


import edu.pe.upeu.delivery.DTOs.RepartidorDTO;
import edu.pe.upeu.delivery.service.RepartidorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/repartidores")
public class RepartidorController {

    private final RepartidorService service;

    public RepartidorController(RepartidorService service) {
        this.service = service;
    }

    @GetMapping
    public List<RepartidorDTO> listar() {
        return service.listarRepartidores();
    }

    @GetMapping("/{id}")
    public RepartidorDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public RepartidorDTO guardar(@RequestBody RepartidorDTO dto) {
        return service.guardarRepartidor(dto);
    }

    @PutMapping("/{id}")
    public RepartidorDTO actualizar(@PathVariable Long id, @RequestBody RepartidorDTO dto) {
        return service.actualizarRepartidor(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarRepartidor(id);
    }
}
