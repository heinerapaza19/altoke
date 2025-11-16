package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.RepartidorDTO;
import edu.pe.upeu.delivery.mapper.RepartidorMapper;
import edu.pe.upeu.delivery.model.Repartidor;
import edu.pe.upeu.delivery.repository.RepartidorRepository;
import edu.pe.upeu.delivery.service.RepartidorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepartidorServiceImpl implements RepartidorService {

    private final RepartidorRepository repo;
    private final RepartidorMapper mapper;

    public RepartidorServiceImpl(RepartidorRepository repo, RepartidorMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<RepartidorDTO> listarRepartidores() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public RepartidorDTO obtenerPorId(Long id) {
        return repo.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado: " + id));
    }

    @Override
    public RepartidorDTO guardarRepartidor(RepartidorDTO dto) {
        Repartidor entity = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public RepartidorDTO actualizarRepartidor(Long id, RepartidorDTO dto) {
        Repartidor r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado: " + id));
        r.setNombre(dto.getNombre());
        r.setTelefono(dto.getTelefono());
        r.setPlacaMoto(dto.getPlacaMoto());
        r.setLicencia(dto.getLicencia());
        r.setDisponible(dto.isDisponible());
        return mapper.toDTO(repo.save(r));
    }

    @Override
    public void eliminarRepartidor(Long id) {
        repo.deleteById(id);
    }
}
