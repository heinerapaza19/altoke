package edu.pe.upeu.delivery.mapper;


import edu.pe.upeu.delivery.DTOs.CategoriaDTO;
import edu.pe.upeu.delivery.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria entity) {
        if (entity == null) return null;
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(entity.getIdCategoria());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setImagen(entity.getImagen());
        return dto;
    }

    public Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) return null;
        Categoria entity = new Categoria();
        entity.setIdCategoria(dto.getIdCategoria());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setImagen(dto.getImagen());
        return entity;
    }
}
