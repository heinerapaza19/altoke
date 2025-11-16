package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.DTOs.RepartidorDTO;

import java.util.List;

public interface RepartidorService {
    List<RepartidorDTO> listarRepartidores();
    RepartidorDTO obtenerPorId(Long id);
    RepartidorDTO guardarRepartidor(RepartidorDTO dto);
    RepartidorDTO actualizarRepartidor(Long id, RepartidorDTO dto);
    void eliminarRepartidor(Long id);
}
