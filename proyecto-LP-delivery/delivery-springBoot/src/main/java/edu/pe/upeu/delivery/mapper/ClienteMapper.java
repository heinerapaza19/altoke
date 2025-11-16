package edu.pe.upeu.delivery.mapper;

import edu.pe.upeu.delivery.model.Cliente;
import edu.pe.upeu.delivery.DTOs.ClienteDTO;
import edu.pe.upeu.delivery.model.Direccion;
import org.springframework.stereotype.Component;


@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente entity) {
        if (entity == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(entity.getIdCliente());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setFoto(entity.getFoto());

        if (entity.getDireccion() != null) {
            dto.setCalle(entity.getDireccion().getCalle());
            dto.setNumero(entity.getDireccion().getNumero());
            dto.setReferencia(entity.getDireccion().getReferencia());
            dto.setDistrito(entity.getDireccion().getDistrito());
            dto.setCiudad(entity.getDireccion().getCiudad());
        }

        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        Cliente entity = new Cliente();
        entity.setIdCliente(dto.getIdCliente());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setFoto(dto.getFoto());

        Direccion direccion = new Direccion();
        direccion.setCalle(dto.getCalle());
        direccion.setNumero(dto.getNumero());
        direccion.setReferencia(dto.getReferencia());
        direccion.setDistrito(dto.getDistrito());
        direccion.setCiudad(dto.getCiudad());

        entity.setDireccion(direccion);

        return entity;
    }
}

