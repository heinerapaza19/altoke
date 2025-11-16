package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.PromocionDTO;
import edu.pe.upeu.delivery.mapper.PromocionMapper;
import edu.pe.upeu.delivery.model.Producto;
import edu.pe.upeu.delivery.model.Promocion;
import edu.pe.upeu.delivery.repository.ProductoRepository;
import edu.pe.upeu.delivery.repository.PromocionRepository;
import edu.pe.upeu.delivery.service.PromocionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromocionServiceImpl implements PromocionService {

    private final PromocionRepository repo;
    private final ProductoRepository productoRepo;
    private final PromocionMapper mapper;

    public PromocionServiceImpl(PromocionRepository repo, ProductoRepository productoRepo, PromocionMapper mapper) {
        this.repo = repo;
        this.productoRepo = productoRepo;
        this.mapper = mapper;
    }

    @Override
    public List<PromocionDTO> listarPromociones() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public PromocionDTO obtenerPorId(Long id) {
        return repo.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
    }

    @Override
    public PromocionDTO guardarPromocion(PromocionDTO dto) {
        Producto producto = productoRepo.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Promocion entity = mapper.toEntity(dto, producto);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public PromocionDTO actualizarPromocion(Long id, PromocionDTO dto) {
        Promocion p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setDescuento(dto.getDescuento());
        p.setFechaInicio(dto.getFechaInicio());
        p.setFechaFin(dto.getFechaFin());
        return mapper.toDTO(repo.save(p));
    }

    @Override
    public void eliminarPromocion(Long id) {
        repo.deleteById(id);
    }
}
