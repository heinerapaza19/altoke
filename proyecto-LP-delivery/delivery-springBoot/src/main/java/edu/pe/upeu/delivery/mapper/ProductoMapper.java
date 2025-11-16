package edu.pe.upeu.delivery.mapper;


import edu.pe.upeu.delivery.DTOs.ProductoDTO;
import edu.pe.upeu.delivery.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto entity) {
        if (entity == null) return null;
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(entity.getIdProducto());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setImagen(entity.getImagen());
        dto.setDisponible(entity.isDisponible());
        dto.setCategoria(entity.getCategoria() != null ? entity.getCategoria().getNombre() : null);
        dto.setEmpresa(entity.getEmpresa() != null ? entity.getEmpresa().getNombre() : null);
        return dto;
    }

    public Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;
        Producto entity = new Producto();
        entity.setIdProducto(dto.getIdProducto());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setImagen(dto.getImagen());
        entity.setDisponible(dto.isDisponible());
        return entity;
    }
}
