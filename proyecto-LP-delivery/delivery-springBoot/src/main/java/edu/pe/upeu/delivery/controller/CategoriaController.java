package edu.pe.upeu.delivery.controller;


import edu.pe.upeu.delivery.DTOs.CategoriaDTO;
import edu.pe.upeu.delivery.service.CategoriaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaDTO> listar() {
        return service.listarCategorias();
    }

    @PostMapping
    public CategoriaDTO guardar(@RequestBody CategoriaDTO dto) {
        return service.guardarCategoria(dto);
    }

    @PutMapping("/{id}")
    public CategoriaDTO actualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        return service.actualizarCategoria(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarCategoria(id);
    }
}
