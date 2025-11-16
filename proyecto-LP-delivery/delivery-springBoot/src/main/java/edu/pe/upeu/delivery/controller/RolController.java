package edu.pe.upeu.delivery.controller;


import edu.pe.upeu.delivery.DTOs.RolDTO;
import edu.pe.upeu.delivery.service.RolService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @GetMapping
    public List<RolDTO> listar() {
        return service.listarRoles();
    }

    @PostMapping
    public RolDTO guardar(@RequestBody RolDTO dto) {
        return service.guardarRol(dto);
    }

    @PutMapping("/{id}")
    public RolDTO actualizar(@PathVariable Long id, @RequestBody RolDTO dto) {
        return service.actualizarRol(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarRol(id);
    }
}
