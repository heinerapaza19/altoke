package edu.pe.upeu.delivery.mapper;


import edu.pe.upeu.delivery.DTOs.RolDTO;
import edu.pe.upeu.delivery.model.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public RolDTO toDTO(Rol entity) {
        if (entity == null) return null;
        RolDTO dto = new RolDTO();
        dto.setIdRol(entity.getIdRol());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }

    public Rol toEntity(RolDTO dto) {
        if (dto == null) return null;
        Rol entity = new Rol();
        entity.setIdRol(dto.getIdRol());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }
}
