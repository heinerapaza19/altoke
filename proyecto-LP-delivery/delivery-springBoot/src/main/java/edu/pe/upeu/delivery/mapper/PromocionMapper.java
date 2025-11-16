package edu.pe.upeu.delivery.mapper;


import edu.pe.upeu.delivery.DTOs.PromocionDTO;
import edu.pe.upeu.delivery.model.Promocion;
import edu.pe.upeu.delivery.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class PromocionMapper {

    public PromocionDTO toDTO(Promocion entity) {
        PromocionDTO dto = new PromocionDTO();
        dto.setIdPromocion(entity.getIdPromocion());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDescuento(entity.getDescuento());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        if (entity.getProducto() != null) {
            dto.setIdProducto(entity.getProducto().getIdProducto());
        }
        return dto;
    }

    public Promocion toEntity(PromocionDTO dto, Producto producto) {
        Promocion p = new Promocion();
        p.setIdPromocion(dto.getIdPromocion());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setDescuento(dto.getDescuento());
        p.setFechaInicio(dto.getFechaInicio());
        p.setFechaFin(dto.getFechaFin());
        p.setProducto(producto);
        return p;
    }
}
