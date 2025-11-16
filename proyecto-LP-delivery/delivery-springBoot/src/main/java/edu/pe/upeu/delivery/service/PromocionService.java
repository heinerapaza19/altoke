package edu.pe.upeu.delivery.service;


import edu.pe.upeu.delivery.DTOs.PromocionDTO;

import java.util.List;

public interface PromocionService {
    List<PromocionDTO> listarPromociones();
    PromocionDTO obtenerPorId(Long id);
    PromocionDTO guardarPromocion(PromocionDTO dto);
    PromocionDTO actualizarPromocion(Long id, PromocionDTO dto);
    void eliminarPromocion(Long id);
}
