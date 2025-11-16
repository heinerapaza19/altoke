package edu.pe.upeu.delivery.service;


import edu.pe.upeu.delivery.DTOs.PagoDTO;

import java.util.List;

public interface PagoService {
    List<PagoDTO> listarPagos();
    PagoDTO obtenerPorId(Long id);
    PagoDTO registrarPago(PagoDTO dto);
    PagoDTO actualizarPago(Long id, PagoDTO dto);
    void eliminarPago(Long id);
}
