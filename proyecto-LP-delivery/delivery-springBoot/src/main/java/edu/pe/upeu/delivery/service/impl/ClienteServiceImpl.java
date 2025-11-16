package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.ClienteDTO;
import edu.pe.upeu.delivery.mapper.ClienteMapper;
import edu.pe.upeu.delivery.model.Cliente;
import edu.pe.upeu.delivery.repository.ClienteRepository;
import edu.pe.upeu.delivery.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;
    private final ClienteMapper mapper;

    public ClienteServiceImpl(ClienteRepository repo, ClienteMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public ClienteDTO obtenerPorId(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }

    @Override
    public ClienteDTO guardarCliente(ClienteDTO dto) {
        Cliente entity = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO dto) {
        Cliente entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public void eliminarCliente(Long id) {
        repo.deleteById(id);
    }
}
