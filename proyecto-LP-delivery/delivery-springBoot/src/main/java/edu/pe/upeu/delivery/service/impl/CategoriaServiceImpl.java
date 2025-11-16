package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.CategoriaDTO;
import edu.pe.upeu.delivery.mapper.CategoriaMapper;
import edu.pe.upeu.delivery.model.Categoria;
import edu.pe.upeu.delivery.repository.CategoriaRepository;
import edu.pe.upeu.delivery.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repo;
    private final CategoriaMapper mapper;

    public CategoriaServiceImpl(CategoriaRepository repo, CategoriaMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<CategoriaDTO> listarCategorias() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public CategoriaDTO guardarCategoria(CategoriaDTO dto) {
        Categoria entity = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto) {
        Categoria c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());
        return mapper.toDTO(repo.save(c));
    }

    @Override
    public void eliminarCategoria(Long id) {
        repo.deleteById(id);
    }
}
