package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.UsuarioDTO;
import edu.pe.upeu.delivery.mapper.UsuarioMapper;
import edu.pe.upeu.delivery.model.Usuario;
import edu.pe.upeu.delivery.repository.UsuarioRepository;
import edu.pe.upeu.delivery.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final UsuarioMapper mapper;

    public UsuarioServiceImpl(UsuarioRepository repo, UsuarioMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public UsuarioDTO obtenerPorId(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }

    @Override
    public UsuarioDTO guardarUsuario(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setUsername(dto.getUsername());
        u.setActivo(dto.isActivo());
        return mapper.toDTO(repo.save(u));
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setUsername(dto.getUsername());
        u.setActivo(dto.isActivo());
        return mapper.toDTO(repo.save(u));
    }

    @Override
    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }

    @Override
    public UsuarioDTO buscarPorUsername(String username) {
        return repo.findByUsername(username)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
    }
}
