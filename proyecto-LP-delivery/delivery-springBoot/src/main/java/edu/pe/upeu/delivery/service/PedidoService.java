package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.DTOs.PedidoDTO;

import java.util.List;

public interface PedidoService {
    List<PedidoDTO> listarPedidos();
    List<PedidoDTO> listarPedidosPorUsuario(String username);
    PedidoDTO obtenerPorId(Long id);
    PedidoDTO crearPedidoDesdeCarrito(Long idCliente);
    PedidoDTO crearPedidoDesdeCarritoUsuarioAutenticado(String username);
    PedidoDTO actualizarEstado(Long idPedido, String nuevoEstado);
    void eliminarPedido(Long idPedido);
}
