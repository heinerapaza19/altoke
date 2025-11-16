package edu.pe.upeu.delivery.mapper;


import edu.pe.upeu.delivery.DTOs.UsuarioDTO;
import edu.pe.upeu.delivery.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setUsername(entity.getUsername());
        dto.setActivo(entity.isActivo());
        dto.setRol(entity.getRol() != null ? entity.getRol().getNombre() : null);
        dto.setEmpresa(entity.getEmpresa() != null ? entity.getEmpresa().getNombre() : null);
        return dto;
    }
}
