package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.ProductoDTO;
import edu.pe.upeu.delivery.mapper.ProductoMapper;
import edu.pe.upeu.delivery.model.Categoria;
import edu.pe.upeu.delivery.model.Producto;
import edu.pe.upeu.delivery.repository.CategoriaRepository;
import edu.pe.upeu.delivery.repository.ProductoRepository;
import edu.pe.upeu.delivery.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;
    private final CategoriaRepository catRepo;
    private final ProductoMapper mapper;

    public ProductoServiceImpl(ProductoRepository repo, CategoriaRepository catRepo, ProductoMapper mapper) {
        this.repo = repo;
        this.catRepo = catRepo;
        this.mapper = mapper;
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO obtenerPorId(Long id) {
        return repo.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    @Override
    public ProductoDTO guardarProducto(ProductoDTO dto) {
        Producto entity = mapper.toEntity(dto);
        if (dto.getCategoria() != null) {
            Categoria cat = catRepo.findByNombre(dto.getCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            entity.setCategoria(cat);
        }
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setDisponible(dto.isDisponible());
        return mapper.toDTO(repo.save(p));
    }

    @Override
    public void eliminarProducto(Long id) {
        repo.deleteById(id);
    }
}
