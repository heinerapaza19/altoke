package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.DTOs.ClienteDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> listarClientes();
    ClienteDTO obtenerPorId(Long id);
    ClienteDTO guardarCliente(ClienteDTO dto);
    ClienteDTO actualizarCliente(Long id, ClienteDTO dto);
    void eliminarCliente(Long id);
}
