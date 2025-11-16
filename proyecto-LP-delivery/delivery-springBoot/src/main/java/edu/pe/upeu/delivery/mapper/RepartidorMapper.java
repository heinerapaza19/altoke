package edu.pe.upeu.delivery.mapper;


import edu.pe.upeu.delivery.DTOs.RepartidorDTO;
import edu.pe.upeu.delivery.model.Repartidor;
import org.springframework.stereotype.Component;

@Component
public class RepartidorMapper {

    public RepartidorDTO toDTO(Repartidor entity) {
        if (entity == null) return null;
        RepartidorDTO dto = new RepartidorDTO();
        dto.setIdRepartidor(entity.getIdRepartidor());
        dto.setNombre(entity.getNombre());
        dto.setTelefono(entity.getTelefono());
        dto.setPlacaMoto(entity.getPlacaMoto());
        dto.setLicencia(entity.getLicencia());
        dto.setDisponible(entity.isDisponible());
        dto.setEmpresa(entity.getEmpresa() != null ? entity.getEmpresa().getNombre() : null);
        return dto;
    }

    public Repartidor toEntity(RepartidorDTO dto) {
        if (dto == null) return null;
        Repartidor entity = new Repartidor();
        entity.setIdRepartidor(dto.getIdRepartidor());
        entity.setNombre(dto.getNombre());
        entity.setTelefono(dto.getTelefono());
        entity.setPlacaMoto(dto.getPlacaMoto());
        entity.setLicencia(dto.getLicencia());
        entity.setDisponible(dto.isDisponible());
        return entity;
    }
}
