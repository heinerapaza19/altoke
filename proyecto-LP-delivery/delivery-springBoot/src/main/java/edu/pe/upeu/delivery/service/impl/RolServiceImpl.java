package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.RolDTO;
import edu.pe.upeu.delivery.mapper.RolMapper;
import edu.pe.upeu.delivery.model.Rol;
import edu.pe.upeu.delivery.repository.RolRepository;
import edu.pe.upeu.delivery.service.RolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository repo;
    private final RolMapper mapper;

    public RolServiceImpl(RolRepository repo, RolMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<RolDTO> listarRoles() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public RolDTO guardarRol(RolDTO dto) {
        Rol entity = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public RolDTO actualizarRol(Long id, RolDTO dto) {
        Rol rol = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + id));
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        return mapper.toDTO(repo.save(rol));
    }

    @Override
    public void eliminarRol(Long id) {
        repo.deleteById(id);
    }
}
